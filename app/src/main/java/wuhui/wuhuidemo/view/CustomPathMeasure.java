package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhui on 2017/2/7.
 */

public class CustomPathMeasure extends View {
    Paint mPaint;
    private PathMeasure mPathMeasure;
    private int maxCount = 100;
    private int currentCount = 0;
    private Handler mHandler;
    private Timer timer;
    MyTimerTask mTask;
    private boolean isSearch;

    public CustomPathMeasure(Context context) {
        super(context);
        init(context);
    }

    public CustomPathMeasure(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomPathMeasure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(-50, -50, 50, 50);
        Path path = new Path();
        path.addArc(rectF, 45, 360f);
        mPathMeasure = new PathMeasure(path, false);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (currentCount >= maxCount) {
                    if (!isSearch) {
                        currentCount = 0;
                        isSearch = true;
                        invalidate();
                    } else {

                    }

                } else {
                    currentCount += 10;
                    invalidate();
                }
//                    currentCount += 10;
            }
        };
        timer = new Timer();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        RectF rectF = new RectF(-50, -50, 50, 50);
        canvas.drawRect(rectF, mPaint);

        Matrix matrix = new Matrix();

//        if (!isSearch) {
//            Path dst = new Path();
//            float start = mPathMeasure.getLength() * currentCount / maxCount;
//            float stop = mPathMeasure.getLength() * (currentCount + 10) / maxCount;
//            mPathMeasure.getSegment(start, stop, dst, true);
//            canvas.drawPath(dst, mPaint);
//        } else {
//            Path dst2 = new Path();
//            float start = 0;
//            float stop = mPathMeasure.getLength() * currentCount / maxCount;
//            mPathMeasure.getSegment(start, stop, dst2, true);
//            canvas.drawPath(dst2, mPaint);
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTask = new MyTimerTask(mHandler);
        timer.schedule(mTask, 0, 300);
        return false;
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }
}
