package wuhui.wuhuidemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by wuhui on 2017/1/19.
 * 水波纹
 */

public class WaterWaveView extends View {
    private Path path;
    private Paint mPaint;
    private Path mSecondPath;
    private Paint mSecondPaint;
    private int centerX, centerY;
    /**
     * 屏幕的长宽
     */
    private int screenWidth, screenHeigh;
    //移动的总距离
    private int mLeftWaveMoveLength = 0;
    /**
     * 峰值
     */
    private int mWaveHeight = 30;
    /**
     * 波长
     */
    private int mWaveWidth = 800;
    /**
     * 一个屏幕内几个周期
     */
    private int mWaveCount;
    private ValueAnimator valueAnimator;

    public WaterWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    public WaterWaveView(Context context) {
        super(context);
        init();
    }

    public WaterWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        path = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ccFF4081"));
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setAntiAlias(true);
        mSecondPaint = new Paint();
        mSecondPath = new Path();
        mSecondPaint.setAntiAlias(true);
        mSecondPaint.setColor(Color.parseColor("#ccFF4081"));
        mSecondPaint.setStrokeWidth(2);
        mSecondPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        _initValueAnimator();
        setVisibility(GONE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("ss", "onSizeChanged");
        screenWidth = w;
        screenHeigh = h;
        mWaveCount = (int) Math.round(screenWidth / mWaveWidth + 1.5);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, 20);
        drawFirst(canvas);
        //  drawSecond(canvas);

    }

    private int offset = mWaveWidth / 2;

    private void drawFirst(Canvas canvas) {
        path.reset();
        mSecondPath.reset();
        path.moveTo(-mWaveWidth + mLeftWaveMoveLength, mWaveHeight);
        mSecondPath.moveTo(-mWaveWidth + mLeftWaveMoveLength + offset, mWaveHeight);
        for (int i = 0; i < mWaveCount; i++) {
            //第一个曲线
            //第一段
            path.quadTo(-mWaveWidth * 3 / 4 + mLeftWaveMoveLength + i * mWaveWidth, -mWaveHeight, -mWaveWidth / 2 + mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);
            //第二段
            path.quadTo(-mWaveWidth / 4 + mLeftWaveMoveLength + i * mWaveWidth, 3 * mWaveHeight, mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);
            //第二个曲线
            //第一段
            mSecondPath.quadTo(-mWaveWidth * 3 / 4 + mLeftWaveMoveLength + i * mWaveWidth + offset, -mWaveHeight, -mWaveWidth / 2 + mLeftWaveMoveLength + i * mWaveWidth + offset, mWaveHeight);
            //第二段
            mSecondPath.quadTo(-mWaveWidth / 4 + mLeftWaveMoveLength + i * mWaveWidth + offset, 3 * mWaveHeight, mLeftWaveMoveLength + i * mWaveWidth + offset, mWaveHeight);

        }
//        path.lineTo(getWidth(), getHeight());
//        path.lineTo(0,  getHeight());
//        path.close();
//
//        canvas.drawPath(path, mPaint);
        mSecondPath.lineTo(getWidth(), getHeight());
        mSecondPath.lineTo(0, getHeight());
        mSecondPath.close();
        canvas.drawPath(mSecondPath, mSecondPaint);
    }

    private void drawSecond(Canvas canvas) {
        path.reset();
        path.moveTo(-mWaveWidth / 2 + mLeftWaveMoveLength, mWaveHeight);
        for (int i = 0; i < mWaveCount; i++) {
            //第一个曲线
            //第一段
            path.quadTo(-mWaveWidth * 1 / 4 + mLeftWaveMoveLength + i * mWaveWidth, -mWaveHeight, +mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);
            //第二段
            path.quadTo(mWaveWidth / 4 + mLeftWaveMoveLength + i * mWaveWidth, 3 * mWaveHeight, mWaveWidth / 4 + mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);

        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
//        canvas.translate(mWaveWidth, 0);
        //     Log.e("点的坐标", mPoints.toString());
        canvas.drawPath(path, mPaint);
    }

    private void _initValueAnimator() {
        valueAnimator = ValueAnimator.ofInt(0, mWaveWidth);
        valueAnimator.setDuration(2000);
        valueAnimator.setStartDelay(300);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLeftWaveMoveLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        valueAnimator.start();
        return super.onTouchEvent(event);
    }
}
