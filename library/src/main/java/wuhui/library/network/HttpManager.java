package wuhui.library.network;

import android.util.Log;

import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.ref.SoftReference;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wuhui.library.network.api.BaseApi;
import wuhui.library.network.api.DataApi;
import wuhui.library.network.exception.RetryWhenNetworkException;
import wuhui.library.network.listener.OnNextBaseAdapter;
import wuhui.library.network.subscribers.ProgressSubscriber;

/**
 * Created by wuhui on 2017/3/22.
 * http交互程序
 *
 * @see HttpManager#doHttpApiActivity(DataApi)
 * @see HttpManager#doHttpApiFragment(DataApi)
 */

public class HttpManager {
    //软引用便于垃圾回收器回收
    private static final String TAG = "HTTPManager";
    private OnNextBaseAdapter adapter;
    private SoftReference<RxAppCompatActivity> appCompatActivity;
    private SoftReference<RxFragment> fragmentSoftReference;
    private Subscriber singleSubscriber = null;

    public HttpManager(OnNextBaseAdapter adapter, RxAppCompatActivity appCompatActivity) {
        this.adapter = adapter;
        this.appCompatActivity = new SoftReference<RxAppCompatActivity>(appCompatActivity);
    }

    public HttpManager(OnNextBaseAdapter adapter, RxFragment rxFragment) {
        this.adapter = adapter;
        this.fragmentSoftReference = new SoftReference<RxFragment>(rxFragment);
    }


    private void _initSubscriber(DataApi baseApi) {
        if (appCompatActivity != null) {
            singleSubscriber = new ProgressSubscriber(baseApi, adapter, appCompatActivity.get());
        } else {
            singleSubscriber = new ProgressSubscriber(baseApi, adapter, fragmentSoftReference.get().getActivity());
        }
    }


    /**
     * 初始化Retrofit
     *
     * @return
     */

    private Retrofit _initRetrofit(BaseApi baseApi) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseApi.getConnectTimeout(), baseApi.getTimeUnit())
                .readTimeout(baseApi.getReadTimeout(), baseApi.getTimeUnit())
                .writeTimeout(baseApi.getWriteTimeout(), baseApi.getTimeUnit())
                .addInterceptor(httpLoggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseApi.getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    /**
     * activity联网交互
     *
     * @param baseApi
     */
    public void doHttpApiActivity(DataApi baseApi) {

        if (appCompatActivity == null) {
            Log.e(TAG, "只能再ac中使用");
        } else {
            _initSubscriber(baseApi);
            Observable observable = baseApi.getObservable(_initRetrofit(baseApi))
                    .retryWhen(new RetryWhenNetworkException())
                    .onErrorResumeNext(new Func1<Throwable, Observable>() {
                        @Override
                        public Observable call(Throwable throwable) {
                            Log.e(TAG, "ss");
                            return Observable.error(throwable);
                        }
                    })
                    .compose(appCompatActivity.get().bindUntilEvent(ActivityEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(singleSubscriber);
        }
    }

    /*用于fragment中联网交互*/
    public void doHttpApiFragment(DataApi baseApi) {
        if (fragmentSoftReference == null) {
            Log.e(TAG, "只能再fragment中使用");
        } else {
            _initSubscriber(baseApi);
            Observable observable = baseApi.getObservable(_initRetrofit(baseApi))
                    .retryWhen(new RetryWhenNetworkException())
                    .compose(fragmentSoftReference.get().bindUntilEvent(FragmentEvent.DESTROY))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
            observable.subscribe(singleSubscriber);
        }
    }


}
