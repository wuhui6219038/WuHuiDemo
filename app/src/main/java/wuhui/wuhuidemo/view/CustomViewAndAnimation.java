package wuhui.wuhuidemo.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wuhui on 2017/2/15.
 */

public class CustomViewAndAnimation extends View {
    private static final String TAG = "CustomViewAndAnimation";
    private Paint mPaint;
    private Path mPath;
    private int radius = 100;
    private boolean isStarting = false;
    private float X = radius, Y = radius;
    private int type = 0;


    private Point currentPoint;

    public CustomViewAndAnimation(Context context) {
        super(context);
        init();
    }

    public CustomViewAndAnimation(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(radius, radius);
            drawCircle(canvas);
            //startAnimation();
        } else {
            drawCircle(canvas);
        }
        super.onDraw(canvas);
//        Log.e(TAG, "当前坐标2：" + getX() + "  " + getY());
//        Log.e(TAG, "挡墙矩阵:" + getMatrix().toShortString());
//        mPath.reset();
//        mPath.addCircle(X, Y, radius, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);
    }

    private void startAnimation() {
        Point startPoint = new Point(radius, radius);
        Point endPoint = new Point(getWidth() - radius, getHeight() - radius);
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setDuration(5000);
        animator.start();
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.x;
        float y = currentPoint.y;
        canvas.drawCircle(x, y, radius, mPaint);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged");

    }

    //
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure");
    }

    //
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                X = event.getX();
                Y = event.getY();
                isStarting = true;
                Log.e(TAG, "当前坐标：" + X + "  " + Y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                X = event.getX();
                Y = event.getY();
                isStarting = true;
                Log.e(TAG, "当前坐标：" + X + "  " + Y);
                invalidate();
                break;
        }
        return true;
    }


}

class PointEvaluator implements TypeEvaluator {


    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
        int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
        return new Point(x, y);
    }
}
