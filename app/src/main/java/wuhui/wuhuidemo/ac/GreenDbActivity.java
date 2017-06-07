package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import wuhui.wuhuidemo.HttpService.HttpGetService;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.config.Config;
import wuhui.wuhuidemo.entity.ZhiShuApi;
import wuhui.wuhuidemo.entity.ZhiShuEntity;
import wuhui.wuhuidemo.greendao.entity.UserTest;
import wuhui.wuhuidemo.greendao.gen.DaoMaster;
import wuhui.wuhuidemo.greendao.gen.DaoSession;
import wuhui.wuhuidemo.greendao.gen.UserTestDao;
import wuhui.wuhuidemo.rxjava.api.api.BaseResultEntity;
import wuhui.wuhuidemo.rxjava.api.http.HttpManager;
import wuhui.wuhuidemo.rxjava.api.listener.HttpOnNextListener;

/**
 * Created by wuhui on 2016/12/29.
 */

public class GreenDbActivity extends RxAppCompatActivity {
    private static final String TAG = "GreenDbActivity";

    @BindView(R.id.btn2_1)
    Button btn21;
    @BindView(R.id.btn2_2)
    Button btn22;
    @BindView(R.id.btn2_3)
    Button btn23;
    @BindView(R.id.btn2_4)
    Button btn24;
    @BindView(R.id.btn2_5)
    Button btn25;
    private UserTestDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        ButterKnife.bind(this);
        initDao();
    }

    @OnClick({R.id.btn2_1, R.id.btn2_2, R.id.btn2_3, R.id.btn2_4, R.id.btn2_5, R.id.btn2_6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn2_1:
                saveUser();
                break;
            case R.id.btn2_2:
                updataUser();
                break;
            case R.id.btn2_3:
                deleteUser();
                break;
            case R.id.btn2_4:
                testEX();
                //   test2();
                break;
            case R.id.btn2_6:
                break;
            case R.id.btn2_5:
                break;
        }
    }

    private void saveUser() {
        UserTest user = new UserTest("wuhui2", "wuhui2");
        userDao.insert(user);
    }

    private void updataUser() {
        UserTest findUser = userDao.queryBuilder().where(UserTestDao.Properties.UserName.eq("wuhui")).build().unique();
        if (findUser != null) {
            findUser.setUserName("wuhuitest");
            userDao.update(findUser);
        } else {
            Toast.makeText(this, "不存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUser() {

    }

    private void queryUser() {

    }

    private void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "wuhuitest.db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserTestDao();

    }

    private void test() {
        HttpOnNextListener httpOnNextListener = new HttpOnNextListener<String>() {
            @Override
            public void onNext(String zhiShuEntities) {
                Log.e("ss", zhiShuEntities);
            }
        };
        ZhiShuApi zhiShuApi = new ZhiShuApi(this, httpOnNextListener);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GetShpiInfo");
        params.put("mod", "shpi");
        params.put("SignCS", "1234567890");
        zhiShuApi.setParams(params);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(zhiShuApi);

    }

    private void test2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASEURL)
                .addConverterFactory(ScalarsConverterFactory.create())//添加 json 转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加 RxJava 适配器
                .build();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GetShpiInfo");
        params.put("mod", "shpi");
        params.put("SignCS", "1234567890");
        HttpGetService service = retrofit.create(HttpGetService.class);
        service.getZhiShuData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("ss", s);
                    }
                });
    }

    private void testEX() {
        HttpOnNextListener httpOnNextListener = new HttpOnNextListener<String>() {
            @Override
            public void onNext(String zhiShuEntities) {
                Log.e("ss", zhiShuEntities);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }
        };
        ZhiShuApi zhiShuApi = new ZhiShuApi(this, httpOnNextListener);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("action", "GetShpiInfo");
        params.put("mod", null);
        params.put("SignCS", "1234567890");
        zhiShuApi.setParams(params);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(zhiShuApi);
    }
}
