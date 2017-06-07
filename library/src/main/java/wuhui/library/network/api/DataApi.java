package wuhui.library.network.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by wuhui on 2017/3/22.
 */

public abstract class DataApi extends BaseApi {

    //progressdialog  能否取消
    private boolean isPdCancel = false;
    /**
     * 是否显示加载框
     */
    private boolean shwoPd = true;


    public abstract Observable getObservable(Retrofit retrofit);

    public DataApi() {

    }

    public DataApi(String baseUrl, int connectTimeout, int readTimeout, int writeTimeout, TimeUnit timeUnit) {
        this.baseUrl = baseUrl;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.timeUnit = timeUnit;
    }

    public boolean isPdCancel() {
        return isPdCancel;
    }

    public void setPdCancel(boolean pdCancel) {
        isPdCancel = pdCancel;
    }


    public boolean isShwoPd() {
        return shwoPd;
    }

    public void setShwoPd(boolean shwoPd) {
        this.shwoPd = shwoPd;
    }


}
