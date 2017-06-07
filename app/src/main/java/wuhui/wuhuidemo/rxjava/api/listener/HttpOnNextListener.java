package wuhui.wuhuidemo.rxjava.api.listener;

/**
 * Created by wuhui on 2016/12/27.
 * 封装接口回调时候的方法
 */

public abstract class HttpOnNextListener<String> {
    /**
     * 请求成功之后回调方法
     *
     * @param t 这个类是必须要实现的所以用抽象类
     */
    public abstract void onNext(String data);

    //完成
    public void onCompelet() {

    }

    //失败
    public void onError(Throwable e) {

    }

    //取消
    public void onCancel() {

    }
}
