package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.PictureDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import wuhui.wuhuidemo.R;


/**
 * Created by wuhui on 2017/1/9.
 */

public class CustomView extends View {
    private Paint mPaint;
    private Rect mBounds;
    private int firstTime = 0;
    Picture mPicture = null;
    Handler handler = null;
    //============================绘制图片===========================================
    //绘制总次数
    private int drawMaxCount = 13;
    //当前回执的个数
    private int drawCountCurrent = -1;
    //绘制时间
    private int drawDruingTime = 300;
    // 宽高
    private int mWidth, mHeight;

    //===================================绘制图片结束=================================================
    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPicture = new Picture();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (drawCountCurrent < drawMaxCount && drawCountCurrent >= 0) {
                    invalidate();
                    drawCountCurrent++;
                    this.sendEmptyMessageDelayed(0, drawDruingTime / drawMaxCount);
                } else {
                    drawCountCurrent = drawMaxCount - 1;
                    invalidate();
                }
            }
        };
//        recording(mPicture);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefalutSize(getSuggestedMinimumWidth(), widthMeasureSpec), getDefalutSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    private int getDefalutSize(int size, int measureSpec) {
        //  Log.e("尺寸:", size + "  " + measureSpec);
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        //   Log.e("此时的w,h", w + "===" + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //父控件  画出一个长方形
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBounds = new Rect();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawRect(0, 0, getWidth(), getRight(), mPaint);
//        drawScale(canvas);
//        drawRectF(canvas);
//        baseView2(canvas);
//        drawPie(canvas);
//        drawSun(canvas);
        drawPic2(canvas);
    }


    private void baseView(Canvas canvas) {

        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        String text = "TEST";
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 - textWidth / 2, getHeight() / 2 + textHeight / 2, mPaint);
    }

    private void baseView2(Canvas canvas) {
        //圆
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.YELLOW);
        float w = getWidth() / 2;
        float h = getHeight() / 2;
        int other = 30;
        mPaint.setStrokeWidth(3);
        canvas.drawCircle(w, h, 200, mPaint);
        // canvas.rotate(120, w, h);
        //扇形字体
        canvas.save();
        Path path = new Path();
        path.addArc(new RectF(0, 0, w + other, h + other), -180, 180);
        canvas.translate(w - (w + other) / 2, h -
                (h + other) / 2);
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(14);
        citePaint.setStrokeWidth(1);
        canvas.drawPath(path, mPaint);
        String msg = "欢迎来到中国";

        Log.e("长度：", msg.length() + "");
        float v = msg.length();
        canvas.drawTextOnPath(msg, path, v, -10, citePaint);
        canvas.restore();
        //画刻度
        int count = 60;
        int y = getHeight() / 2;
        Paint tmpPaint = new Paint(mPaint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(2);
        tmpPaint.setTextSize(20);
        //刻度的线x轴结束坐标
        int lineWidthX = getWidth() / 2 - 200;
        canvas.save();
        canvas.rotate(120, w, h);
        for (int i = 0; i < count; i++) {
            if (i % 5 == 0) {
                canvas.drawLine(lineWidthX / 2, y, lineWidthX, y, tmpPaint);
                canvas.drawText(String.valueOf(i / 5 + 1), lineWidthX / 2 - 20f, y + 5f, tmpPaint);
            } else {
                canvas.drawLine(lineWidthX / 2 + 20, y, lineWidthX, y, tmpPaint);
            }
            canvas.rotate(360 / count, w, h);
        }
        canvas.restore();
        //画中心的圆
        tmpPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        tmpPaint.setColor(Color.GRAY);
        canvas.drawCircle(w, h, 20, tmpPaint);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(w, h, 10, tmpPaint);
        //画指针
        canvas.drawLine(getWidth() / 2, getHeight() / 2 - 100, getWidth() / 2, getHeight() / 2, mPaint);

    }

    private void drawRectF(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        RectF rectF = new RectF(10, 10, 200, 100);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }

    private void drawPie(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawArc(new RectF(0, 0, getWidth(), getHeight()), 0, 90, true, mPaint);
        mPaint.setTextSize(50);
        String text = "TEST";
        mPaint.setColor(Color.BLACK);
        mPaint.getTextBounds(text, 0, text.length(), mBounds);
        float textWidth = mBounds.width();
        float textHeight = mBounds.height();
        canvas.drawText(text, getWidth() / 2 + 10, getHeight() / 2 + textHeight / 2, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawArc(new RectF(0, 0, getWidth(), getHeight()), 90, 90, true, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(new RectF(0, 0, getWidth(), getHeight()), 180, 100, true, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(new RectF(0, 0, getWidth(), getHeight()), 280, 80, true, mPaint);
    }

    private void drawScale(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        Rect rect = new Rect(0, 0, getWidth() / 2, getHeight() / 2);
        canvas.drawRect(rect, mPaint);
        canvas.scale(0.5f, 0.5f, 100, 100);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rect, mPaint);
    }

    private void drawSun(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, 200, mPaint);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(0, 0, 180, mPaint);
        for (int i = 0; i <= 360; i += 10) {
            canvas.drawLine(0, 180, 0, 200, mPaint);
            canvas.rotate(10);
        }
    }


    private void drawPic(Canvas canvas) {
        //  canvas.drawPicture(mPicture, new RectF(0, 0, mPicture.getWidth(),100));
//        PictureDrawable pd = new PictureDrawable(mPicture);
//        pd.setBounds(0, 0, 200, 100);
//        pd.draw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(0, 0, 240, mPaint);
        Rect dst = new Rect(-150, -150, 150, 150);
        mPaint.setColor(Color.WHITE);
        canvas.drawRect(dst, mPaint);
        //canvas.translate(mWidth / 2, mHeight / 2);
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.checkmark);
        //每次需要绘制的高度
        int drawWidth = bitmap.getHeight();
        Rect src = new Rect(drawWidth * drawCountCurrent, 0, drawWidth * (1 + drawCountCurrent), bitmap.getHeight());
//        Rect src = new Rect(drawHeight * drawCountCurrent, 0, drawHeight * (1 + drawCountCurrent), bitmap.getHeight());
//        int sideLength = bitmap.getHeight();
//        Rect src = new Rect(sideLength * drawCountCurrent, 0, sideLength * (drawCountCurrent + 1), sideLength);

        // Log.e("AAA", drawCountCurrent + "");
//        Rect dst = new Rect(0, 0, 400, 400);
        mPaint.setColor(Color.GREEN);
        canvas.drawBitmap(bitmap, src, dst, mPaint);
    }

    private void drawPic2(Canvas canvas) {
        Path path = new Path();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(2);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        int width = 100;
        path.lineTo(0, -width);
        path.lineTo(width, -width);
        path.lineTo(width, -width / 2);
        RectF rectF = new RectF(width / 2, -width / 2, width / 2 * 3, width / 2);
//        RectF rectF = new RectF(0, 0, 100, 100);
        canvas.drawRect(rectF, mPaint);
        canvas.drawArc(rectF, -90, 270, true,mPaint);
//        path.arcTo(rectF, -90, 270, false);
//        path.close();
        canvas.drawPath(path, mPaint);
//        canvas.translate(mWidth / 2, mHeight / 2);  // 移动坐标系到屏幕中心
//        canvas.scale(1, -1);                         // <-- 注意 翻转y坐标轴
//
////        path.lineTo(100,100);
//
//        RectF oval = new RectF(0, 0, 100, 100);
//        canvas.drawRect(oval, mPaint);
//        path.arcTo(oval, 0, 270);
//// path.arcTo(oval,0,270,false);             // <-- 和上面一句作用等价
//
//        canvas.drawPath(path, mPaint);
    }

    public void startDraw() {
        drawCountCurrent = 0;
        handler.sendEmptyMessageAtTime(1, drawDruingTime / drawMaxCount);

    }

    private void recording(Picture picture) {
        Canvas canvas = picture.beginRecording(200, 200);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        // 在Canvas中具体操作
        // 位移
        canvas.translate(100, 100);
        // 绘制一个圆
        canvas.drawCircle(0, 0, 50, paint);
        picture.endRecording();
    }


}


