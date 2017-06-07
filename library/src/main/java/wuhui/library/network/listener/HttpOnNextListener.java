package wuhui.library.network.listener;

/**
 * Created by wuhui on 2017/3/22.
 * 异步网络请求网络回调接口
 */

public interface HttpOnNextListener {
     void onError(Throwable e);

     void onCancel();

     void onCompleted();
}
