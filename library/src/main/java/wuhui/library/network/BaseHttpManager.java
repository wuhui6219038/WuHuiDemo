package wuhui.library.network;

import android.util.Log;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import wuhui.library.network.api.BaseApi;
import wuhui.library.network.api.DataApi;
import wuhui.library.network.download.DownloadIntercepetor;

/**
 * Created by wuhui on 2017/3/23.
 * httpManager管理
 */

public class BaseHttpManager {
    protected OkHttpClient.Builder builder;

    protected BaseHttpManager() {
        builder = new OkHttpClient.Builder();
    }

    /**
     * @return初始化okhttp
     */
    protected void _initOkHttp(BaseApi baseApi) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.connectTimeout(baseApi.getConnectTimeout(), baseApi.getTimeUnit())
                .readTimeout(baseApi.getReadTimeout(), baseApi.getTimeUnit())
                .writeTimeout(baseApi.getWriteTimeout(), baseApi.getTimeUnit())
                .addInterceptor(httpLoggingInterceptor);
    }

    /**
     * 初始化Retrofit
     *
     * @return
     */

    protected Retrofit _initRetrofit(BaseApi baseApi) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(baseApi.getBaseUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

}
