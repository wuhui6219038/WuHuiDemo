package wuhui.wuhuidemo.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by wuhui on 2017/2/16.
 * 水平方向
 */

public class AutoScrollHorizontalView extends ViewGroup {
    private static final String TAG = "AutoScrollHorizontal";

    /**
     * 显示区域的大小
     */
    private int mWidth, mHeight;
    private int mLastX, mLastY;
    //开始的位置
    private int mStartItem = 0;
    private Scroller mScroller;

    public AutoScrollHorizontalView(Context context) {
        super(context);
        init(context);
    }

    public AutoScrollHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();// 再次调用computeScroll。
        }
//        Log.e("ss", "computeScroll");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
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
        // scrollTo(0, mStartItem * mHeight);
        scrollTo(mStartItem * mWidth, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // Log.e(TAG, "onLayout");
        int right = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            child.layout(right, 0, right + child.getMeasuredWidth(), child.getMeasuredHeight());
            right += child.getMeasuredWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getRawX();
        int y = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNeedTouch(x - mLastX, y - mLastY)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        Log.e(TAG, "onInterceptTouchEvent");
        return intercept;
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
        Log.e("AutoScrollActivity", "父控件");
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                mLastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
//                int dy = y - mLastY;
//                mLastY = y;
                int dx = x - mLastX;
                scrollBy(-dx, 0);
                mLastX = x;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
//                //当期的位置
//                int sonIndex = (getScrollY() + getHeight() / 2) / getHeight();
//                if (sonIndex >= getChildCount()) {
//                    sonIndex = getChildCount() - 1;
//                }
//                if (sonIndex < 0) {
//                    sonIndex = 0;
//                }
//                int dy2 = sonIndex * getHeight() - getScrollY();
//                mScroller.startScroll(0, getScrollY(), 0, dy2, 1000);
                mScroller.startScroll(getScrollX(), 0, _initCalculateWidth(), 0, 1000);
                invalidate();
                break;
            }
        }
        return true;
    }

    private int _initCalculateWidth() {
        int srollWidth = 0;
        int currentIndex = (getScrollX() + getWidth() / 2) / getWidth();
        if (currentIndex >= getChildCount()) {
            currentIndex = getChildCount() - 1;
        }
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        srollWidth = currentIndex * getWidth() - getScrollX();
        return srollWidth;
    }

    private boolean isNeedTouch(int srcollX, int srcollY) {
        boolean cost = false;
        Log.e(TAG, "距离对比:" + srcollX + "===" + srcollY);
        //当水平方向移动的距离大于竖直方向是默认为水平移动
        if (Math.abs(srcollX) > 10) {
            cost = true;
        } else {
            cost = false;
        }

        return cost;
    }

    private void animtaion() {
        ObjectAnimator move = ObjectAnimator.ofFloat(this, "translationY", 0, 100f);
        move.setDuration(1000);
        move.start();
    }
}
