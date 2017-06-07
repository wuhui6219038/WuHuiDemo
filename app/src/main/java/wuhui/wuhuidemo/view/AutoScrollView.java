package wuhui.wuhuidemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wuhui on 2017/2/16.
 */

public class AutoScrollView extends ViewGroup {
    private static final String TAG = "AutoScrollView";

    /**
     * 显示区域的大小
     */
    private int mWidth, mHeight;
    private int mLastX, mLastY;
    //开始的位置
    private int mStartItem = 0;
    private Scroller mScroller;

    public AutoScrollView(Context context) {
        super(context);
        init(context);
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG, mScroller.getCurrX() + "===" + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();// 再次调用computeScroll。
        }
//        Log.e("ss", "computeScroll");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        Log.e("mWidth,mHeight", mWidth + "  " + mHeight);
        scrollTo(0, mStartItem * mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout");
        int top = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            child.layout(0, top, child.getMeasuredWidth(), top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (!mScroller.isFinished()) {
//                    mScroller.setFinalY(mScroller.getCurrY());
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                mLastY = y;
                return true;

            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;
                mLastX = x;
                mLastY = y;
                scrollBy(0, dy);
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                Log.e(TAG, "滑动的距离：" + getScrollY());
                int sonIndex = (getScrollY() + getHeight() / 2) / getHeight();
                Log.e(TAG, "当前位置：" + sonIndex);

                if (sonIndex >= getChildCount())
                    sonIndex = getChildCount() - 1;
                if (sonIndex < 0) {
                    sonIndex = 0;
                }
                int dy2 = sonIndex * getHeight() - getScrollY();
                mScroller.startScroll(0, getScrollY(), 0, dy2, 1000);
                invalidate();
                return true;
            }
        }
        return true;
    }

    private void animtaion() {
        ObjectAnimator move = ObjectAnimator.ofFloat(this, "translationY", 0, 100f);
        move.setDuration(1000);
        move.start();
    }
}
