package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wuhui on 2017/7/25.
 */

public class DashPathEffectView extends View {
    private Paint mPaint;
    private DashPathEffect dashPathEffect;
    private int phase = 0;
    private Path mPath;
    private int width, height;
    private static final String TAG = "DashPathEffect";

    public DashPathEffectView(Context context) {
        super(context);
        _init();
    }

    public DashPathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    private void _init() {
        mPath = new Path();
        mPaint = new Paint();


        mPaint.setAntiAlias(true);//设置抗锯齿
        mPaint.setColor(Color.RED);//画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);//画笔样式
        mPaint.setStrokeWidth(2);//画笔宽度
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        Log.e(TAG, "onSizeChanged");

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dashPathEffect = new DashPathEffect(new float[]{30, 50, 25, 15},
                phase);
        mPaint.setPathEffect(dashPathEffect);
        mPath.lineTo(400, 0);
//        mPath.lineTo(getRight() - 20, getBottom() - 20);
//        mPath.lineTo(0, getBottom() - 20);
        //mPath.close();
        canvas.translate(width / 4, height / 4);
        canvas.drawPath(mPath, mPaint);
        phase++;
        postInvalidateDelayed(50);
    }
}
