package wuhui.library.network;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import wuhui.library.io.FileTools;
import wuhui.library.network.api.BaseApi;
import wuhui.library.network.api.DownApi;
import wuhui.library.network.bean.DownState;
import wuhui.library.network.download.DownloadIntercepetor;
import wuhui.library.network.exception.RetryWhenNetworkException;
import wuhui.library.network.service.HttpDownService;
import wuhui.library.network.subscribers.ProgressDownSubscriber;

/**
 * Created by wuhui on 2017/3/23.
 */

public class HttpDownManager {
    private static final String TAG = "HttpDownManager";
    private volatile static HttpDownManager INSTANCE;
    private ProgressDownSubscriber subscriber;
    private Map<String, ProgressDownSubscriber> subMap;
    /*记录下载数据*/
    private Set<DownApi> downInfos;

    private HttpDownManager() {
        subMap = new HashMap<>();
        downInfos = new HashSet<>();
    }

    public static HttpDownManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownManager();
                }
            }
        }
        return INSTANCE;
    }


    /**
     * 开始下载
     *
     * @param downInfo
     */
    private HttpDownService service;

    public void startDown(final DownApi downInfo) {
        if (downInfo == null) {
            Log.e(TAG, "downInfo为空");
            return;
        }
        if (subMap.get(downInfo.getDownUrl()) != null) {
            Log.e(TAG, "正在下载中");
            return;
        }
        subscriber = new ProgressDownSubscriber(downInfo);
        subMap.put(downInfo.getDownUrl(), subscriber);
        if (downInfos.contains(downInfo)) {
            service = downInfo.getHttpDownService();
        } else {
            Interceptor downloadIntercepetor = new DownloadIntercepetor(subscriber);
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(downInfo.getConnectTimeout(), downInfo.getTimeUnit())
                    .readTimeout(downInfo.getReadTimeout(), downInfo.getTimeUnit())
                    .writeTimeout(downInfo.getWriteTimeout(), downInfo.getTimeUnit())
                    .addInterceptor(httpLoggingInterceptor).addInterceptor(downloadIntercepetor);
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(downInfo.getBaseUrl())
                    .build();
            service = retrofit.create(HttpDownService.class);
            downInfo.setHttpDownService(service);
            downInfos.add(downInfo);
        }
        //开始下载
        service.downloadApk("bytes=" + downInfo.getReadLength() + "-", downInfo.getDownUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryWhenNetworkException())
                .map(new Func1<ResponseBody, DownApi>() {
                    @Override
                    public DownApi call(ResponseBody responseBody) {
                        try {
                            FileTools.write2Sd(responseBody, new File(downInfo.getSavaPath()), downInfo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return downInfo;
                    }
                })
                /*回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*数据回调*/
                .subscribe(subscriber);
    }

    public void stopDown(DownApi info) {
        if (info == null) {
            return;
        } else {
            info.setState(DownState.STOP);
            info.getAdapter().onPuase();
            if (subMap.containsKey(info.getDownUrl())) {
                ProgressDownSubscriber subscriber = subMap.get(info.getDownUrl());
                subscriber.unsubscribe();
                subMap.remove(info.getDownUrl());
            }
        }
    }

    public void pause(DownApi info) {

    }

    public void remove(DownApi downInfo) {
        if (subMap != null) {
            subMap.remove(downInfo.getDownUrl());
        }
    }


}
