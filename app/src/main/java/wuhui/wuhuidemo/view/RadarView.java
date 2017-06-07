package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by wuhui on 2017/1/13.
 * dota2技术操作雷达图
 */

public class RadarView extends View {
    //定点个数
    private int count = 6;
    private float angle = (float) (Math.PI * 2 / count);
    private Paint mPaint;
    //边长
    private float width = 50;
    private String[] msg = {"A", "B", "C", "D", "E", "F"};
    private float mCurrentX = 0;
    Path path;

    public RadarView(Context context) {
        super(context);
        init();
    }

    public RadarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RadarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(getWidth() / 2, getHeight() / 2);

        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        path.addCircle(mCurrentX, getHeight() / 2, 100, Path.Direction.CW);
//        for (int index = 0; index < count; index++) {
//            path.reset();
//            float currentWidth = width * index;
//            for (int index2 = 0; index2 < count; index2++) {
//                if (index2 == 0) {
//                    path.moveTo(currentWidth, 0);
//                }
//                float x = (float) (currentWidth * Math.cos(angle * index2));
//                float y = (float) (currentWidth * Math.sin(angle * index2));
//                Log.e("坐标", "x=" + x + "  y=" + y);
//                path.lineTo(x, y);
//                if (index == 5) {
//                    drawFont(canvas, index2, x, y);
//                }
//            }
//            path.close();
        canvas.drawPath(path, mPaint);
//        }


    }

    private void drawFont(Canvas canvas, int poistion, float x, float y) {
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText(msg[poistion], 0, msg[poistion].length(), x + 5, y, mPaint);
    }


    private class MoveAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mCurrentX = getWidth() * interpolatedTime;
            path.reset();
            invalidate();
        }
    }

    public void startAnimation()

    {
        MoveAnimation move = new MoveAnimation();
        move.setDuration(2000);
        move.setInterpolator(new AccelerateDecelerateInterpolator());

        //move.setRepeatCount(Animation.INFINITE);
        //move.setRepeatMode(Animation.REVERSE);
        startAnimation(move);
    }
}
