package wuhui.wuhuidemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wuhui on 2017/6/9.
 */

public class TestLl extends LinearLayout {
    String TAG = "TestLl";
    private Scroller mScroller;

    public TestLl(Context context) {
        super(context);
        _init(context);

    }

    public TestLl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init(context);
    }

    void _init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                intercept = true;
                break;
            default:
                intercept = false;
                break;

        }
        return intercept;

    }

    //上一次滑动的坐标值
    private int mLastX, mLastY;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int y = (int) ev.getRawY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setScrollY(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = 0;
                dy = y - mLastY;
                View chlidView = getChildAt(0);
                if (getScrollY() <= chlidView.getHeight())
                    scrollBy(0, -dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                int srollHeight = getWidth() - getScrollY();
                if (srollHeight > getChildAt(0).getHeight()) {
                    srollHeight = getChildAt(0).getHeight();
                }
                //  mScroller.startScroll(0, getScrollY(), 0, srollHeight, 1000);
                // invalidate();
                break;
            default:
                break;

        }
        return true;
    }
}
