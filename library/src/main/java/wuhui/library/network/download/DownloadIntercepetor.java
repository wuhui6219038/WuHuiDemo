package wuhui.library.network.download;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by wuhui on 2017/3/23.
 */

public class DownloadIntercepetor implements Interceptor {
    private DownloadListener listener;

    public DownloadIntercepetor(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        Response response = originResponse.newBuilder().body(new DownloadResponseBody(originResponse.body(), listener)).build();
        return response;
    }
}
