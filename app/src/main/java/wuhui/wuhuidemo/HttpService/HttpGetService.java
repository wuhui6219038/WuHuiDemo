package wuhui.wuhuidemo.HttpService;


import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;
import wuhui.wuhuidemo.rxjava.api.api.BaseResultEntity;

/**
 * Created by wuhui on 2016/12/28.
 */

public interface HttpGetService {
    @GET("index.php")
    Observable<String> getZhiShuData(@QueryMap Map<String, Object> map);
}
