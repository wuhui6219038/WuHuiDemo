package wuhui.library.network.listener;

/**
 * Created by wuhui on 2017/3/23.
 * 下载管理适配器
 */

public abstract class HttpDownOnNextAdapter<T> implements HttpOnNextListener {
    /*现在成功*/
    public void onNext(T t) {

    }

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 下载进度
     *
     * @param readLength  读取的长度
     * @param countLength 总长度
     */
    public abstract void updateProgress(long readLength, long countLength);


    /**
     * 暂停下载
     */
    public void onPuase() {

    }

    /**
     * 停止下载销毁
     */
    public void onStop() {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
