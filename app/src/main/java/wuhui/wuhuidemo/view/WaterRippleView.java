package wuhui.wuhuidemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuhui on 2017/7/24.
 */

public class WaterRippleView extends View {
    /**
     * 最小半径
     */
    private int mMinRadio = 0;
    /**
     * 最大半径
     */
    private int mMaxRadio = 160;
    /**
     * 每个圆之间的间隔
     */
    private int mInterval = 20;
    /**
     * 加载圆的个数
     */
    private int circleCount = 0;
    /**
     * 当前加载的圆的个数
     */
    private int mCurrentCircleCount;
    private Paint mPaint;
    private ValueAnimator valueAnimator;

    public WaterRippleView(Context context) {
        super(context);
        _init();
    }

    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void _init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.parseColor("#90CAF9"));
        circleCount = (int) Math.round(mMaxRadio / mInterval + 0.5);
        Log.e("圆的总数", circleCount + "");
        _initAnimator();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        for (int radio = 1; radio < mCurrentCircleCount; radio++) {
            mPaint.setAlpha(255 - 255 * radio * mInterval / mMaxRadio);
            Log.e("透明度的值:", (radio * mInterval) + "");
            canvas.drawCircle(0, 0, radio * mInterval, mPaint);
        }
    }

    private void _initAnimator() {
        valueAnimator = ValueAnimator.ofInt(1, circleCount * 2);
        valueAnimator.setDuration(3000);
        // valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentCircleCount = (int) animation.getAnimatedValue();
                if (mCurrentCircleCount > 9) {
                    mCurrentCircleCount = circleCount * 2 - mCurrentCircleCount;
                }
                invalidate();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        valueAnimator.start();
        return super.onTouchEvent(event);
    }

    /**
     * 水波纹
     */
    class RippleView extends View {

        public RippleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

        }


    }
}
