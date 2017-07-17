package wuhui.wuhuidemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import rx.Observable;

/**
 * Created by wuhui on 2017/7/5.
 */

public class TestNetWork extends View implements View.OnClickListener {
    private Paint mPaint;
    private Paint mPaint2;
    private Paint textPaint;
    private Paint textPaint2;
    private Path circel_path;
    private Path circel_path2;
    private int width;
    private int height;
    private ValueAnimator animator_circle_outside;
    private float degree = 0;
    RectF rectF = new RectF(0, 0, 200, 200);
    private int countHeight = 0;

    public TestNetWork(Context context) {
        super(context);
        _init();
    }

    public TestNetWork(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    private void _init() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(15);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        circel_path = new Path();
        set_animator_circle_outside();
        _initOutSideCircle();
        setOnClickListener(this);
        textPaint();
        textPaint2();
    }

    private void _initOutSideCircle() {
        circel_path2 = new Path();
        mPaint2 = new Paint();
        mPaint2.setStrokeWidth(15);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setAntiAlias(true);
        mPaint2.setColor(Color.BLUE);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);
    }

    private void textPaint() {
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    private void textPaint2() {
        textPaint2 = new Paint();
        textPaint2.setColor(Color.GRAY);
        textPaint2.setTextSize(20);
        textPaint2.setTextAlign(Paint.Align.CENTER);
        textPaint2.setAntiAlias(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        circel_path.addArc(rectF, 120, 300);
        canvas.drawPath(circel_path, mPaint);
        circel_path2.addArc(rectF, 120, degree);
        canvas.drawPath(circel_path2, mPaint2);
        drawCountText(canvas);
        drawText(canvas);
    }

    private void drawCountText(Canvas canvas) {
        String msg = "5000";
        textPaint.setColor(Color.GRAY);
        textPaint.setTextSize(40);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseLine = (int) ((rectF.top + rectF.bottom - fontMetrics.top - fontMetrics.bottom) / 2);
        countHeight = fontMetrics.bottom - fontMetrics.top;
        canvas.drawText(msg, rectF.centerX(), baseLine, textPaint);
    }

    private void drawText(Canvas canvas) {
        String msg = "步数";
        Paint.FontMetricsInt fontMetrics = textPaint2.getFontMetricsInt();
        int baseLine = (int) ((rectF.top + rectF.bottom - fontMetrics.top - fontMetrics.bottom) / 2);
        canvas.drawText(msg, rectF.centerX(), baseLine + countHeight, textPaint2);
    }

    private void set_animator_circle_outside() {
        animator_circle_outside = ValueAnimator.ofFloat(0, 1);
        animator_circle_outside.setDuration(2000);
        animator_circle_outside.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                degree = 300 * value;
                invalidate();

            }
        });
    }

    @Override
    public void onClick(View v) {
        degree = 0;
        invalidate();
        animator_circle_outside.start();
    }
}
