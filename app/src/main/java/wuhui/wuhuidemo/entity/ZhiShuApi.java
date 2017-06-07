package wuhui.wuhuidemo.entity;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;
import wuhui.wuhuidemo.HttpService.HttpGetService;
import wuhui.wuhuidemo.rxjava.api.api.BaseApi;
import wuhui.wuhuidemo.rxjava.api.exception.HttpTimeException;
import wuhui.wuhuidemo.rxjava.api.listener.HttpOnNextListener;

/**
 * Created by wuhui on 2016/12/28.
 */

public class ZhiShuApi extends BaseApi {

    private Map<String, Object> map;

    public ZhiShuApi(RxAppCompatActivity mActivity, HttpOnNextListener mListener) {
        super(mActivity, mListener);
    }

    public void setParams(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        if (map == null) {
            throw new HttpTimeException("没用设置参数");
        } else {
            HttpGetService service = retrofit.create(HttpGetService.class);
            return service.getZhiShuData(map);
        }
    }

}
