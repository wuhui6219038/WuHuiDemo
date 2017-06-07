package wuhui.wuhuidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2016/12/23.
 */

public class SlideItemView extends LinearLayout {
    private static final String TAG = SlideItemView.class.getName();
    private static final int TAN = 2;
    private Scroller mScroller;
    //按钮的宽度
    private int mBtnWidth = 120;
    private float mLastX = 0;
    private float mLastY = 0;

    public SlideItemView(Context context) {
        super(context);
        initView(context);
    }

    public SlideItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.slideview, this);
        mScroller = new Scroller(context);
        mBtnWidth = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mBtnWidth, getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                //计算偏移量
                float deltaX = x - mLastX;
                float delatY = y - mLastY;
                mLastX = x;
                mLastY = y;
                if (Math.abs(deltaX) < Math.abs(delatY) * TAN) {
                    break;
                }
                if (deltaX != 0) {
                    Log.e(TAG, "getScrollX=" + getScrollX());
                    Log.e(TAG, "deltaX=" + deltaX);
                    float newScrollX = getScrollX() - deltaX;
                    if (newScrollX < 0) {
                        newScrollX = 0;
                    } else if (newScrollX > mBtnWidth) {
                        Log.e(TAG, "mBtnWidth=" + mBtnWidth);
                        newScrollX = mBtnWidth;
                    }
                    Log.e(TAG, "newScrollX=" + newScrollX);
                    this.scrollTo((int) newScrollX, 0);
                }
                break;
            case MotionEvent.ACTION_DOWN:

                reset();
                break;
        }
        return true;
    }

    private void reset() {
        int offset = getScrollX();
        if (offset == 0) {
            return;
        }
        smoothScrollTo(0, 0);

    }

    private void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }
}
