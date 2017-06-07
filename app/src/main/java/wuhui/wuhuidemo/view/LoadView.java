package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.uitl.UiUtils;

/**
 * Created by wuhui on 2017/1/12.
 */

public class LoadView extends View {
    private Paint mPaint;
    //叶子
    Bitmap leafBtMap;
    //风扇
    Bitmap FengShanBtMap;
    //外框
    Bitmap Kuang_BtMap;
    // 用于控制绘制的进度条距离左／上／下的距离
    private static final int LEFT_MARGIN = 9;
    // 用于控制绘制的进度条距离右的距离
    private static final int RIGHT_MARGIN = 25;
    private int mLeftMargin, mRightMargin;
    // 淡白色(黄色) 默认的颜色
    private static final int WHITE_COLOR = 0xfffde399;
    // 橙色 填充的颜色
    private static final int ORANGE_COLOR = 0xffffa800;
    //当前加载的长度
    private int current_progress = -1;
    //总共需要加载的长度
    private int maxProgress = 100;
    private int mTotalWidth, mTotalHeight;
    private Handler handler;
    //每次绘制的宽度
    private float drawWidth = 2;
    //每次绘制的时间差
    private int delayTime = 100;

    public LoadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    public LoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint(context);
    }

    public LoadView(Context context) {
        super(context);
        initPaint(context);
    }

    private void initPaint(Context context) {
        initHandler();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mLeftMargin = UiUtils.dipToPx(context, LEFT_MARGIN);
        leafBtMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaf);
        Kuang_BtMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.leaf_kuang);
        FengShanBtMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fengshan);
        initValue();
    }

    private void initValue() {
        maxProgress = 100;
        drawWidth = (float) Kuang_BtMap.getWidth() / maxProgress;
        Log.e("lv", "每个单位的长度：" + drawWidth + "   总长度:" + Kuang_BtMap.getWidth());

    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (current_progress < maxProgress) {
                    invalidate();
                    current_progress++;
                    handler.sendEmptyMessageDelayed(1, delayTime);
                } else {
                    invalidate();
                }
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        Log.e("此时的宽度", w + "");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.translate(getWidth() / 2, getHeight() / 2);
        //画外框

        //drawArcProgress(canvas);
        mPaint.setColor(ORANGE_COLOR);
        canvas.save();
        canvas.translate(0, getHeight() / 2);
        canvas.drawRect(new Rect(0, 0, Kuang_BtMap.getWidth(), Kuang_BtMap.getHeight()), mPaint);
        canvas.restore();
        Log.e("lv", current_progress + "");

        canvas.drawRect(new RectF(0, 10, drawWidth * current_progress, Kuang_BtMap.getHeight() - 10), mPaint);
        canvas.drawBitmap(Kuang_BtMap, 0, 0, null);
        canvas.save();

        //圆弧
        mPaint.setColor(WHITE_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        canvas.translate(0, getHeight() / 4);
        canvas.drawArc(new RectF(10, 0, 100, 100), 90, 180, false, mPaint);
        //矩形
        canvas.drawLine(50, 0, 400, 0, mPaint);
        canvas.drawLine(50, 100, 400, 100, mPaint);

        //==========圆弧的填充=============
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(ORANGE_COLOR);
        if (current_progress >= 0 && drawWidth * current_progress <= 50) {
            drawArcProgress(canvas, drawWidth * current_progress);
        }
        if (current_progress >= 0 && drawWidth * current_progress > 50) {
            drawArcProgress(canvas, 50);
            canvas.drawRect(new RectF(50, 10, drawWidth * current_progress, 90), mPaint);
        }
//        drawArcProgress(canvas, 50);
//        canvas.drawRect(new RectF(50, 0,400,100), mPaint);
        //====================================
        //================右边两个圆========================
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(400, 50, 50, mPaint);
        mPaint.setColor(WHITE_COLOR);
        canvas.drawCircle(400, 50, 45, mPaint);

        canvas.saveLayerAlpha(350, 0, 450, 100, 0x50, Canvas.ALL_SAVE_FLAG);
        canvas.translate(350, 0);
        mPaint.setColor(Color.RED);
//        Rect src = new Rect(0, 0, FengShanBtMap.getWidth(), FengShanBtMap.getWidth());
//        RectF dst = new RectF(0, 0, 100, 100);
//        while (current_progress > 0) {
//            canvas.rotate(45, 50, 50);
//            canvas.drawRect(dst, mPaint);
//        }
//        canvas.drawBitmap(FengShanBtMap, src, dst, null);
        canvas.restore();
        canvas.restore();


    }


    //处理圆弧
    private void drawArcProgress(Canvas canvas, float acrRadio) {
        int angle = (int) Math.toDegrees(Math.acos((50 - acrRadio) / 100));
        int startAngle = 180 - angle;
        // 扫过的角度
        int sweepAngle = 2 * angle;
        canvas.drawArc(new RectF(20, 10, 90, 90), startAngle, sweepAngle, false, mPaint);
    }

    public void startLoad() {
        current_progress = 0;
        handler.sendEmptyMessageDelayed(1, delayTime);
    }
}
