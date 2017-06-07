package wuhui.library.network.listener;


/**
 * Created by wuhui on 2017/3/22.\
 * 适配器
 */

public abstract class OnNextBaseAdapter implements HttpOnNextListener {
    public abstract void onNext(Object data);

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onCompleted() {

    }
}
