package wuhui.wuhuidemo.test;

import android.util.Log;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by wuhui on 2016/12/26.
 */
public class RxJavaTestTest {

    @Test
    public void testRxRetryWhen() throws Exception {
        Observable<Integer> observable = Observable.just(1, 2, 3);
        observable.flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer o) {
                if (o == 1) {
                    return Observable.just("我是111");
                }
                return Observable.just("我是=" + o);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String s) {
              System.out.println(s);
            }
        });

    }

}