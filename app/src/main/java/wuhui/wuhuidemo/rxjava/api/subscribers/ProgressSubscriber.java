package wuhui.wuhuidemo.rxjava.api.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.rxjava.api.api.BaseApi;
import wuhui.wuhuidemo.rxjava.api.listener.HttpOnNextListener;

/**
 * Created by wuhui on 2016/12/28.
 * 数据交换
 */

public class ProgressSubscriber extends Subscriber<String> {
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;
    //    弱引用反正内存泄露
    private SoftReference<RxAppCompatActivity> mActivity;
    //    加载框可自己定义
    private ProgressDialog pd;
    /*请求数据*/
    private BaseApi api;

    public ProgressSubscriber(BaseApi baseApi) {
        this.api = baseApi;
        this.mSubscriberOnNextListener = new SoftReference<>(api.getmListener());
        this.mActivity = new SoftReference<>(api.getmActivity());
        if (baseApi.isShowProgress())
            initPorgressDialog(baseApi.isCancelProgress());
    }

    private void initPorgressDialog(boolean cancel) {
        Context context = mActivity.get();
        if (pd == null && context != null) {
            pd = new ProgressDialog(context);
            pd.setMessage("正在加载中。。。");
            pd.setCancelable(cancel);
            if (cancel) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        CancelProgress();
                    }
                });
            }
        }
    }

    private void showProgressDialog() {
        Context context = mActivity.get();
        if (!api.isShowProgress() || context == null || pd == null) {
            return;
        }
        if (!pd.isShowing()) {
            pd.show();
        }

        if (pd != null || pd.isShowing()) ;
    }

    private void dismisProgressDialog() {
        if (!api.isShowProgress()) return;
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    private void CancelProgress() {
        if (this.isUnsubscribed()) {
            unsubscribe();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        //TODO缓存
    }

    @Override
    public void onCompleted() {
        dismisProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        //报错的时候先判断是否设置了换了，尝试从缓存中获取数据,要是没用设置缓存怎么抛出异常
        dismisProgressDialog();
        if (api.isCache()) {
            //TODO缓存
        } else {
            doError(e);
        }
    }

    private void doError(Throwable e) {
        Context context = mActivity.get();
        if (context == null) {
            return;
        }
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, context.getResources().getString(R.string.network_error_1), Toast.LENGTH_SHORT).show();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, context.getResources().getString(R.string.network_error_2), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        e.printStackTrace();
    }

    @Override
    public void onNext(String data) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(data);
        }
    }
}
