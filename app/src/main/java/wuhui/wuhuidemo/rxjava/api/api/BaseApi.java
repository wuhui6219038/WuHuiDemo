package wuhui.wuhuidemo.rxjava.api.api;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;
import wuhui.wuhuidemo.config.Config;
import wuhui.wuhuidemo.rxjava.api.exception.HttpTimeException;
import wuhui.wuhuidemo.rxjava.api.listener.HttpOnNextListener;

/**
 * Created by wuhui on 2016/12/28.
 * 请求数据统一封装类
 */

public abstract class  BaseApi<T> implements Func1<T, String> {
    //rx生命周期管理
    private SoftReference<RxAppCompatActivity> mActivity;
    //回调接口
    private SoftReference<HttpOnNextListener> mListener;
    //点击返回按钮能否取消pb
    private boolean cancelProgress = false;
    //是否显示pb
    private boolean showProgress = true;
    //是否需要缓存
    private boolean cache = false;
    //超时时间
    private int connectTime = Config.CONNECTIONTIME;
    //有网状态下缓存的时间
    private int cookieNetWorkTime = Config.COOKIENETWORKTIME;
    //没有网状态下缓存的时间
    private int cookieNoNetWorkTime = Config.COOKIENONETWORKTIME;
    //请求url
    private String baseUrl = Config.BASEURL;

    public BaseApi(RxAppCompatActivity mActivity, HttpOnNextListener mListener) {
        setmActivity(mActivity);
        setmListener(mListener);
    }

    @Override
    public String call(T tBaseResultEntity) {
//        BaseResultEntity baseResulte = JSONObject.parseObject(tBaseResultEntity.toString(), BaseResultEntity.class);
//        if (baseResulte.getSuccess() == Config.DATA_FAIL) {
//            throw new HttpTimeException(baseResulte.getMessage());
//        }
//        return baseResulte.getInfo();
        return  null;
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
    public abstract Observable getObservable(Retrofit retrofit);

    public RxAppCompatActivity getmActivity() {
        return mActivity.get();
    }

    public void setmActivity(RxAppCompatActivity mActivity) {
        this.mActivity = new SoftReference<RxAppCompatActivity>(mActivity);
    }

    public HttpOnNextListener getmListener() {
        return mListener.get();
    }

    public void setmListener(HttpOnNextListener mListener) {
        this.mListener = new SoftReference<HttpOnNextListener>(mListener);
    }

    public boolean isCancelProgress() {
        return cancelProgress;
    }

    public void setCancelProgress(boolean cancelProgress) {
        this.cancelProgress = cancelProgress;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public int getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(int connectTime) {
        this.connectTime = connectTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;


    }
}
