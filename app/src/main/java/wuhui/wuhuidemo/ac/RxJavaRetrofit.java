package wuhui.wuhuidemo.ac;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Observer;
import rx.subjects.AsyncSubject;
import wuhui.library.network.HttpDownManager;
import wuhui.library.network.HttpManager;
import wuhui.library.network.api.DataApi;
import wuhui.library.network.api.DownApi;
import wuhui.library.network.listener.HttpDownOnNextAdapter;
import wuhui.library.network.listener.OnNextBaseAdapter;
import wuhui.wuhuidemo.HttpService.HttpGetService;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.annotation.AnnotationBean;
import wuhui.wuhuidemo.annotation.TestAnnotation;

/**
 * Created by wuhui on 2017/6/6.
 */

public class RxJavaRetrofit extends BaseActivity {
    private static final String TAG = "RxJavaRetrofit";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(this);
    }

    private void _initAsyncSubject() {
        String[] value = {"one", "two", "three"};
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.onNext("one");
        Observable childObservable = subject.asObservable();
        Observer observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, s);
            }
        };
        subject.subscribe(observer);
    }

    private String target = null;

    private void TestCreator() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class targetClass = Class.forName("wuhui.wuhuidemo.annotation.AnnotationBean");
        Field[] fields = targetClass.getDeclaredFields();
        Method[] methods = targetClass.getMethods();
        for (Field field : fields) {
            if (field.isAnnotationPresent(TestAnnotation.class)) {
                for (Annotation anno : field.getDeclaredAnnotations()) {
                    Log.e(TAG, "Annotation in Method '"
                            + field + "' : " + anno);
                }
                TestAnnotation testAnnotation = field.getAnnotation(TestAnnotation.class);
                target = testAnnotation.target();
            }
        }
        for (Method method : methods) {
            if (method.isAnnotationPresent(TestAnnotation.class)) {
                for (Annotation anno : method.getDeclaredAnnotations()) {
                    Log.e(TAG, "Annotation in Method '"
                            + method + "' : " + anno);

                }
                TestAnnotation testAnnotation = method.getAnnotation(TestAnnotation.class);
                if (testAnnotation.target().equals(target)) {
                    method.invoke(targetClass.newInstance(), "测试方法的");
                } else {
                    Log.e(TAG, "没有发现相同的注解");
                }
            }
        }

    }


    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                try {
                    TestCreator();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
//                _initAsyncSubject();
                // getData();
                break;
            case R.id.btn2:
                //downLoad();
                break;
        }
    }

    private void getData() {
        OnNextBaseAdapter onNextBaseAdapter = new OnNextBaseAdapter() {
            @Override
            public void onNext(Object data) {
                Log.e(TAG, data.toString());
                //Gson gson = new Gson();

            }
        };
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GetShpiInfo");
        params.put("mod", "shpi");
        params.put("SignCS", "1234567890");
        HttpManager httpManager = new HttpManager(onNextBaseAdapter, this);
        httpManager.doHttpApiActivity(new DataApi() {
            @Override
            public Observable getObservable(Retrofit retrofit) {
                return retrofit.create(HttpGetService.class).getZhiShuData(params);
//                return retrofit.create(HttpGetService.class).getZhiShuData2(params)
            }
        });
    }

    private ProgressDialog progressDialog;

    private void downLoad() {
        String url = "http://www.steelhome.cn/application/steelhomeV2_test.apk";
        final DownApi downInfo = new DownApi(url);
        downInfo.setSavaPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) +
                "/test" + ".apk");
//        final ProgressBar pb = (ProgressBar) getView().findViewById(R.id.timeoutinfo);
        final HttpDownOnNextAdapter adapter2 = new HttpDownOnNextAdapter() {
            @Override
            public void onNext(Object o) {
                Log.e(TAG, o.toString());
            }

            @Override
            public void onStart() {
                Log.e(TAG, "开始下载");

            }

            @Override
            public void updateProgress(long readLength, long countLength) {
                float progress = (float) readLength / (float) countLength;
                DecimalFormat df = new DecimalFormat("0.00");//格式化小数
                String s = df.format(progress * 100);//返回的是String类型
                // Log.e(TAG, readLength + "===" + countLength);
                _initDownLoad((int) Float.parseFloat(s));
//                initNotification(downInfo, (int) Float.parseFloat(s));
            }

            @Override
            public void onCompleted() {
                //openFile(new File(downInfo.getSavaPath()));
                Log.e(TAG, "数据加载完成");
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        };
        downInfo.setAdapter(adapter2);
        HttpDownManager.getInstance().startDown(downInfo);
    }


    private void _initDownLoad(int progress) {
        //Log.e(TAG, "进度:" + progress);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(RxJavaRetrofit.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
            progressDialog.setTitle("正在下载");
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        progressDialog.setProgress(progress);
    }

    private NotificationManager manger;
    private NotificationCompat.Builder builder = null;

    private void initNotification(int progress) {

        if (manger == null) {
            manger = (NotificationManager)
                    this.getSystemService(Activity.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.drawable.checkmark);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.checkmark));
            //禁止用户点击删除按钮删除
            builder.setAutoCancel(false);
            //禁止滑动删除
            builder.setOngoing(false);
            //取消右上角的时间显示
            builder.setShowWhen(false);
            builder.setOngoing(true);
            builder.setShowWhen(false);

        }
        builder.setProgress(100, progress, false);
        if (progress == 100) {
            builder.setContentTitle("已完成");
        } else
            builder.setContentTitle("下载中..." + progress + "%");
        Notification notification = builder.build();
        manger.notify(0x01, notification);
    }
}
