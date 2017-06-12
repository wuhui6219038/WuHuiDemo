package wuhui.wuhuidemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by wuhui on 2017/6/9.
 */

public class TestTv extends TextView {
    private static final String TAG = "TestTv";

    public TestTv(Context context) {
        super(context);
    }

    public TestTv(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return false;
//    }
}
