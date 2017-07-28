package wuhui.wuhuidemo.injector.components.bean;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by wuhui on 2017/7/26.
 */

public class TestBean {
    private static final String TAG = "TestBean";

    @Inject
    public TestBean() {

    }

    public void desc() {
        Log.e(TAG, "desc: 我只是来玩玩的");
    }
}
