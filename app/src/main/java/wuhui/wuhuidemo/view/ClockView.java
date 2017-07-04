package wuhui.wuhuidemo.view;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import static android.R.attr.radius;

/**
 * Created by wuhui on 2017/7/4.
 */

public class ClockView extends View implements View.OnClickListener {
    private Paint mPaint;
    private Path mPath;
    private ValueAnimator animator_center_to_left;
    private ValueAnimator animator_left_to_right;
    private ValueAnimator animator_right_to_center;
    private float radius = 50;
    private float lineLength = 300;
    private float lineX = 0, lineY = lineLength;
    private float ballX = 0, ballY = lineLength + radius;
    private AnimatorSet animationSet;

    public ClockView(Context context) {
        super(context);
        _init();
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    private void _init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(3);
        mPath = new Path();
        setOnClickListener(this);
        animationSet = new AnimatorSet();
        _initAnimation();
    }

    private void _initAnimation() {
        set_animator_center_to_left();
        set_animator_left_to_right();
        set_animator_right_to_left();
        animationSet.play(animator_right_to_center).after(animator_center_to_left).after(animator_left_to_right);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPath.reset();
        mPath.lineTo(lineX, lineY);
        mPath.addCircle(ballX, ballY, radius, Path.Direction.CW);
        mPaint.setColor(Color.RED);
        canvas.drawPath(mPath, mPaint);
    }

    private void set_animator_center_to_left() {
        animator_center_to_left = ValueAnimator.ofInt(0, 45);
        animator_center_to_left.setDuration(2000);
        animator_center_to_left.setRepeatCount(Animation.INFINITE);
        animator_center_to_left.setRepeatMode(Animation.REVERSE);
        animator_center_to_left.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                lineX = (float) (lineLength * Math.sin(Math.toRadians(angle)));//计算x坐标
                lineY = (float) ((lineLength * Math.cos(Math.toRadians(angle))));//计算y坐标
                ballX = (float) ((lineLength + radius) * Math.sin(Math.toRadians(angle)));//计算x坐标
                ballY = (float) (((lineLength + radius) * Math.cos(Math.toRadians(angle))));//计算y坐标
                invalidate();
            }
        });
    }

    private void set_animator_left_to_right() {
        animator_left_to_right = ValueAnimator.ofInt(45, -45);
        animator_left_to_right.setDuration(2000);
        animator_left_to_right.setRepeatCount(Animation.INFINITE);
        animator_left_to_right.setRepeatMode(Animation.REVERSE);
        animator_left_to_right.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                lineX = (float) (lineLength * Math.sin(Math.toRadians(angle)));//计算x坐标
                lineY = (float) ((lineLength * Math.cos(Math.toRadians(angle))));//计算y坐标
                ballX = (float) ((lineLength + radius) * Math.sin(Math.toRadians(angle)));//计算x坐标
                ballY = (float) (((lineLength + radius) * Math.cos(Math.toRadians(angle))));//计算y坐标
                invalidate();
            }
        });
    }

    private void set_animator_right_to_left() {
        animator_right_to_center = ValueAnimator.ofInt(-45, 0);
        animator_right_to_center.setDuration(2000);
        animator_right_to_center.setRepeatCount(Animation.INFINITE);
        animator_right_to_center.setRepeatMode(Animation.REVERSE);
        animator_right_to_center.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int angle = (int) animation.getAnimatedValue();
                lineX = (float) (lineLength * Math.sin(Math.toRadians(angle)));//计算x坐标
                lineY = (float) ((lineLength * Math.cos(Math.toRadians(angle))));//计算y坐标
                ballX = (float) ((lineLength + radius) * Math.sin(Math.toRadians(angle)));//计算x坐标
                ballY = (float) (((lineLength + radius) * Math.cos(Math.toRadians(angle))));//计算y坐标
                invalidate();
            }
        });
    }


    @Override
    public void onClick(View v) {
        animationSet.start();
    }
}
