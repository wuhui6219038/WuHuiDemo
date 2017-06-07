package wuhui.library.network.subscribers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.ref.SoftReference;

import rx.Subscriber;
import wuhui.library.R;
import wuhui.library.network.api.DataApi;
import wuhui.library.network.listener.OnNextBaseAdapter;

/**
 * Created by wuhui on 2017/3/22.
 * 观察者
 */

public class ProgressSubscriber extends Subscriber {
    private static final String TAG = " ProgressSubscriber";
    private OnNextBaseAdapter mAdapter;
    private SoftReference<Context> mActivity;
    private DataApi baseApi;
    private ProgressDialog pd;

    public ProgressSubscriber(DataApi baseApi, OnNextBaseAdapter mAdapter, Context mActivity) {
        this.baseApi = baseApi;
        this.mAdapter = mAdapter;
        this.mActivity = new SoftReference<Context>(mActivity);
        if (baseApi.isShwoPd())
            _initProgressDialog();
    }


    private void _initProgressDialog() {
        Context cxt = mActivity.get();
        if (pd == null && cxt != null) {
            pd = new ProgressDialog(cxt);
            pd.setCancelable(false);
            pd.setTitle(cxt.getResources().getString(R.string.info_loding));
            if (!baseApi.isPdCancel()) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        cancel_progressDialog();
                    }
                });
            }
        } else {
            Log.e(TAG, "pd或者cxt中有空值");
        }
    }

    private void showProgressDialog() {
        if (baseApi.isShwoPd()) {
            if (mActivity.get() == null || pd == null) {
                Log.e(TAG, "pd有空值或者cxt对应的ac已经关闭");
            } else {
                if (!pd.isShowing()) {
                    pd.show();
                }
            }
        }
    }

    private void dismisProgressDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }


    //取消http连接
    private void cancel_progressDialog() {
        if (!this.isUnsubscribed()) {
            unsubscribe();
            mAdapter.onCancel();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismisProgressDialog();
        mAdapter.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        dismisProgressDialog();
        mAdapter.onError(e);
        e.printStackTrace();
    }

    @Override
    public void onNext(Object data) {
//        BaseResults base = (BaseResults) gson.fromJson(data, baseApi.getTargetClass());
        mAdapter.onNext(data);
    }

}
