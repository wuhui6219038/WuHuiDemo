package wuhui.wuhuidemo.rxjava.api.exception;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import wuhui.wuhuidemo.config.Config;

/**
 * Created by wuhui on 2016/12/28.
 */

public class RetryWhenNetWorkException implements Func1<Observable<? extends Throwable>, Observable<?>> {
    private int repeatCount = Config.REPEATCOUNT;
    private int repeatTime = Config.CONNECTIONTIME * 5;

    public RetryWhenNetWorkException() {

    }


    public RetryWhenNetWorkException(int repeatCount, int repeatTime) {
        this.repeatCount = repeatCount;
        this.repeatTime = repeatTime;
    }

    @Override
    public Observable<?> call(final Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, repeatCount + 1), new Func2<Throwable, Integer, Wrapper>() {
            @Override
            public Wrapper call(Throwable throwable, Integer integer) {
                return new Wrapper(integer, throwable);
            }

        }).flatMap(new Func1<Wrapper, Observable<?>>() {
            @Override
            public Observable<?> call(Wrapper wrapper) {
                Log.e("RetryWhen", "RetryWhen");
                if ((wrapper.throwable instanceof ConnectException
                        || wrapper.throwable instanceof SocketTimeoutException
                        || wrapper.throwable instanceof TimeoutException)
                        & wrapper.index < repeatCount + 1
                        ) {
                    return Observable.interval(repeatTime, TimeUnit.MILLISECONDS);
                }
                return Observable.error(wrapper.throwable);
            }
        });
    }

    private class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(int index, Throwable throwable) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
