package wuhui.wuhuidemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observable;
import rx.Single;
import rx.subjects.AsyncSubject;
import wuhui.wuhuidemo.uitl.RsaUtils;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("wuhui.wuhuidemo", appContext.getPackageName());
    }

    @Test
    public void testKeyPair() {
        Log.e("RSA:", RsaUtils.generateRSAKeyPair(1024).getPrivate().toString());
    }

    @Test
    public void testSingle() {
        String[] value = {"1", "2", "3"};
//        Single<String>.fr
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("one");
        asyncSubject.onNext("two");
        asyncSubject.onNext("three");
        asyncSubject.subscribe(asyncSubject);

    }

}
