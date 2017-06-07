package wuhui.library.network.subscribers;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import wuhui.library.network.api.DownApi;
import wuhui.library.network.bean.DownState;
import wuhui.library.network.download.DownloadListener;
import wuhui.library.network.listener.HttpDownOnNextAdapter;

/**
 * Created by wuhui on 2017/3/23.
 */

public class ProgressDownSubscriber<T> extends Subscriber<T> implements DownloadListener {
    private HttpDownOnNextAdapter adapter;
    private DownApi downInfo;

    public ProgressDownSubscriber(DownApi downInfo) {
        this.adapter = downInfo.getAdapter();
        this.downInfo = downInfo;
    }

    @Override
    public void onStart() {
        if (adapter != null)
            adapter.onStart();
        downInfo.setState(DownState.START);
    }

    @Override
    public void onCompleted() {
        if (adapter != null) {
            adapter.onCompleted();
        }
        if (isUnsubscribed()) {
            unsubscribe();
        }
        downInfo.setState(DownState.FINISH);
    }

    @Override
    public void onError(Throwable e) {

        if (!isUnsubscribed()) {
            unsubscribe();
        }
        if (adapter != null) {
            adapter.onError(e);
        }
        downInfo.setState(DownState.ERROR);
        // HttpDownManager.getInstance().remove(downInfo);
    }

    @Override
    public void onNext(T t) {
        if (adapter != null) {
            adapter.onNext(t);
        }
    }

    @Override
    public void download(long read, final long totallength, boolean done) {
        if (adapter != null) {
            if (downInfo.getCountLength() > totallength) {
                read = downInfo.getCountLength() - totallength + read;
            } else {
                downInfo.setCountLength(totallength);
            }
            downInfo.setReadLength(read);
            Observable.just(read)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (downInfo.getState() != DownState.PAUSE
                                    || downInfo.getState() != DownState.STOP
                                    || downInfo.getState() != DownState.ERROR || downInfo.getState() != DownState.FINISH) {
                                downInfo.setState(DownState.DOWN);
                                adapter.updateProgress(aLong, totallength);
                            }
                        }
                    });
        }
    }
}
