package wuhui.wuhuidemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhui on 2017/1/19.
 * 水波纹
 */

public class BezierView2 extends View {
    Path path = new Path();
    private Paint mPaint;
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
    private int mWaveHeight = 15;
    /**
     * 波长
     */
    private int mWaveWidth = 800;
    /**
     * 一个屏幕内几个周期
     */
    private int mWaveCount;
    private ValueAnimator valueAnimator;

    public BezierView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BezierView2(Context context) {
        super(context);
        init();
    }

    public BezierView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        _initValueAnimator();
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

    private void drawFirst(Canvas canvas) {
        path.reset();
        path.moveTo(-mWaveWidth + mLeftWaveMoveLength, mWaveHeight);
        for (int i = 0; i < mWaveCount; i++) {
            //第一个曲线
            //第一段
            path.quadTo(-mWaveWidth * 3 / 4 + mLeftWaveMoveLength + i * mWaveWidth, -mWaveHeight, -mWaveWidth / 2 + mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);
            //第二段
            path.quadTo(-mWaveWidth / 4 + mLeftWaveMoveLength + i * mWaveWidth, 3 * mWaveHeight, mLeftWaveMoveLength + i * mWaveWidth, mWaveHeight);

        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
//        canvas.translate(mWaveWidth, 0);
        //     Log.e("点的坐标", mPoints.toString());
        canvas.drawPath(path, mPaint);
//        canvas.drawPath(path2, mPaint);
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
