package wuhui.library.network.service;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by wuhui on 2017/3/23.
 * 下载接口
 */

public interface HttpDownService {
    /**
     * @param start 开始位置
     * @param url
     * @return
     */
    @GET
    Observable<ResponseBody> downloadApk(@Header("RANGE") String start, @Url String url);
}
