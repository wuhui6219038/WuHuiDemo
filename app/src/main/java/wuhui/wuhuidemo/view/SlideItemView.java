package wuhui.wuhuidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2016/12/23.
 */

public class SlideItemView extends ViewGroup {
    private static final String TAG = SlideItemView.class.getName();
    private static final int TAN = 2;
    private Scroller mScroller;
    private float mLastX = 0;
    /**
     * 当前显示位置
     */
    private int currentIndex = 0;
    /**
     * 隐藏菜单的宽度
     */

    private int slideMenuWidth = 0;

    public SlideItemView(Context context) {
        super(context);
        initView(context);
    }

    public SlideItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int right = 0;
        for (int index = 0; index < getChildCount(); index++) {
            View child = getChildAt(index);
            child.layout(right, 0, right + child.getMeasuredWidth(), child.getMeasuredHeight());
            right += child.getMeasuredWidth();
        }
    }

    private void initView(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                reset();
                break;
            case MotionEvent.ACTION_MOVE:

                //计算偏移量
                float deltaX = x - mLastX;
                mLastX = x;
                if (deltaX < 0) {
                    deltaX = 0;
                } else if (deltaX > slideMenuWidth) {
                    deltaX = slideMenuWidth;
                }
                this.scrollBy((int) deltaX, 0);
                break;
        }
        return true;
    }

    private void reset() {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        smoothScrollTo();

    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();// 再次调用computeScroll。
        }
    }

    private void smoothScrollTo() {
        mScroller.startScroll(getScrollX(), 0, _initCalculateWidth(), 0, 1000);
        invalidate();
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
}
