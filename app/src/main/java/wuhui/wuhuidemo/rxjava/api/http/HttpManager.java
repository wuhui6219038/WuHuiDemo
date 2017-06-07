package wuhui.wuhuidemo.rxjava.api.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wuhui.wuhuidemo.rxjava.api.api.BaseApi;
import wuhui.wuhuidemo.rxjava.api.exception.RetryWhenNetWorkException;
import wuhui.wuhuidemo.rxjava.api.subscribers.ProgressSubscriber;

/**
 * Created by wuhui on 2016/12/28.
 */

public class HttpManager {
    private volatile static HttpManager INSTANCE;

    private HttpManager() {

    }

    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public void doHttpDeal(BaseApi baseApi) {
        //初始化okhhtp
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(baseApi.getConnectTime(), TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor);


        //TODO 缓存的功能没有
        //初始化retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseApi.getBaseUrl())

                .build();
//初始化观察者
        ProgressSubscriber subscriber = new ProgressSubscriber(baseApi);
        //初始化被观察者
        Observable observable =
                baseApi.getObservable(retrofit)
                        .retryWhen(new RetryWhenNetWorkException())
                        .onErrorResumeNext(new Func1<Throwable, Observable>() {
                            @Override
                            public Observable call(Throwable throwable) {
                                Log.e("错误", "错误");
                                return Observable.error(throwable);
                            }
                        })
                        .compose(baseApi.getmActivity().bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(baseApi);
//订阅
        observable.subscribe(subscriber);
    }
}
