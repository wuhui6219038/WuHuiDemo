package wuhui.wuhuidemo.view;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by wuhui on 2017/1/19.
 */

public class BezierView2 extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    Path path = new Path();
    private Paint mPaint;
    private int centerX, centerY;
    private int width = 100;
    private int heigh = 100;
    private Handler handler;
    private Timer timer;
    private MyTimerTask mTask;
    //每一次移动的距离
    private int speedWidth = 20;
    //移动的总距离
    private int mLeftWaveMoveLength = 0;
    //峰值
    private int mWaveHeight = 50;
    //波长
    private int mWaveWidth = 200;
    private List<Point> mPoints;

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
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mLeftWaveMoveLength += speedWidth;//波向右移动距离增加mSpeed;
                if (mLeftWaveMoveLength >= mWaveWidth) {//当增加到一个波长时回复到0
                    mLeftWaveMoveLength = 0;
                }
                invalidate();
            }
        };
        mPoints = new ArrayList<Point>();
        timer = new Timer();
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        centerX = w / 2;
//        centerY = h / 2;
        Log.e("ss", "onSizeChanged");
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("ss", "onMeasure");
        if (mPoints.isEmpty()) {
            width = getMeasuredWidth();
            heigh = getMeasuredHeight();
            centerY = heigh / 2;
            mWaveHeight = heigh / 20;
            for (int i = 0; i < 9; i++) {
                int tmpy = 0;
                switch (i % 4) {
                    case 0:
                        tmpy = centerY;
                        break;
                    case 1:
                        tmpy = centerY + mWaveHeight;
                        break;
                    case 2:
                        tmpy = centerY;
                        break;
                    case 3:
                        tmpy = centerY - mWaveHeight;
                        break;
                }
                Point point = new Point(mWaveWidth / 4 * i, tmpy);
                mPoints.add(point);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(4);
        Log.e("ss", "ss");
        // 绘制贝塞尔曲线
        mPaint.setColor(Color.RED);

        path.reset();
        path.moveTo(mPoints.get(0).x + mLeftWaveMoveLength, centerY);
//        path.quadTo(width / 2, centerY - heigh, width, centerY);

//        for (int i = 0; i < 5; i++) {
//            heigh = 100;
//            if (i % 2 != 0) {
//                heigh = centerY + heigh;
//            } else {
//                heigh = centerY - heigh;
//            }
//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setStrokeWidth(20);
//            mPaint.setColor(Color.BLACK);
//            canvas.drawPoint(width / 2 * (2 * i + 1), heigh, mPaint);
//            mPaint.setColor(Color.BLUE);
//            canvas.drawPoint(width * (i + 1), centerY, mPaint);
//            mPaint.setColor(Color.RED);
//            mPaint.setStrokeWidth(6);
//            path.quadTo(width / 2 * (2 * i + 1), heigh, width * (i + 1), centerY);
//
//        }
        int i = 0;
        mPaint.setStyle(Paint.Style.FILL);
        for (; i < mPoints.size() - 2; i += 2) {
            path.quadTo(mPoints.get(i + 1).x + mLeftWaveMoveLength, mPoints.get(i + 1).y, mPoints.get(i + 2).x + mLeftWaveMoveLength, mPoints.get(i + 2).y);
        }
        path.lineTo(mPoints.get(i).x + mLeftWaveMoveLength,heigh);
        path.lineTo(mPoints.get(0).x + mLeftWaveMoveLength,heigh);
        path.close();
        //     Log.e("点的坐标", mPoints.toString());
        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.BLUE);
        RectF rectF = new RectF(0, 0, mPoints.get(4).x, heigh);
        RectF rectF2 = new RectF(mPoints.get(8).x, 0, width, heigh);
        canvas.drawRect(rectF, mPaint);
        canvas.drawRect(rectF2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(handler);
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
