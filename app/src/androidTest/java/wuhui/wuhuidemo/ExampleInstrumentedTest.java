package wuhui.wuhuidemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

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
}
