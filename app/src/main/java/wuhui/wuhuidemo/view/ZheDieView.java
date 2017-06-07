package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/2/7.
 */

public class ZheDieView extends ViewGroup {
    private Bitmap mBitmap;

    //渐变
    private Paint mShadowPaint;
    private Paint mSolidPaint;
    private Matrix mShadowGradientMatrix;
    private LinearGradient mShadowGradientShader;
    //分割的个数
    private int clipCount = 8;
    //分割后每一个小块的长度
    private float clipWidth = 0;
    //折叠之后每一块的长度
    private int mTraslateWidth = 0;
    //存放每个小块的矩阵
    private Matrix[] matrices;
    //折抵之后的总长度
    private int mTraslateWidths = 0;
    //缩放比例
    private float mFactor = 0.5f;
    Matrix mMatrix;
    private int mTranslation = getWidth();

    public ZheDieView(Context context) {
        super(context);
    }

    public ZheDieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ZheDieView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    private void init() {
        matrices = new Matrix[clipCount];
        //初始化分割参数
        clipWidth = mBitmap.getWidth() / clipCount;
        mTraslateWidths = (int) (mBitmap.getWidth() * mFactor);
        mTraslateWidth = mTraslateWidths / clipCount;

        //渐变效果
        mSolidPaint = new Paint();
        int alpha = (int) (255 * mFactor * 0.8f);
        mSolidPaint
                .setColor(Color.argb((int) (alpha * 0.5F), 0, 0, 0));

        mShadowPaint = new Paint();
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowGradientShader = new LinearGradient(0, 0, 0.5f, 0, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowGradientMatrix = new Matrix();
        mShadowGradientMatrix.setScale(mBitmap.getWidth(), 1);
        mShadowGradientShader.setLocalMatrix(mShadowGradientMatrix);
        mShadowPaint.setAlpha(alpha);


        //变形操作
        //纵轴减小的那个高度，用勾股定理计算下
        int depth = (int) Math.sqrt(clipWidth * clipWidth
                - mTraslateWidth * mTraslateWidth) / 2;
        for (int i = 0; i < clipCount; i++) {
            mMatrix = new Matrix();
            mMatrix.reset();
            float[] src = {i * clipWidth, 0,//
                    (i + 1) * clipWidth, 0,//
                    (i + 1) * clipWidth, mBitmap.getHeight(),//
                    i * clipWidth, mBitmap.getHeight()};
            boolean isEven = i % 2 == 0;
            float[] dst = {i * mTraslateWidth, isEven ? 0 : depth,//
                    (i + 1) * mTraslateWidth, isEven ? depth : 0,//
                    (i + 1) * mTraslateWidth, isEven ? mBitmap.getHeight() - depth : mBitmap
                    .getHeight(),//
                    i * mTraslateWidth, isEven ? mBitmap.getHeight() : mBitmap.getHeight()
                    - depth};
//            Log.e("原始矩形矩形:", Arrays.toString(src));
//            Log.e("目标矩形:", Arrays.toString(dst));
            mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
            matrices[i] = mMatrix;
        }
//        mMatrix = new Matrix();
//        float[] src = {0, 0,//
//                mBitmap.getWidth()/8, 0,//
//                mBitmap.getWidth()/8, mBitmap.getHeight(),//
//                0, mBitmap.getHeight()};
//        float[] dst = {0, 0,//
//                mBitmap.getWidth()/8, 50,//
//                mBitmap.getWidth()/8, mBitmap.getHeight() - 50,//
//                0, mBitmap.getHeight()};
//        mMatrix.setPolyToPoly(src, 0, dst, 0, src.length >> 1);
    }

    private boolean isReady;
    private Canvas mCanvas = new Canvas();

    @Override
    protected void dispatchDraw(Canvas canvas) {

//        canvas.clipRect(0, 0, 50, mBitmap.getHeight());
//        canvas.translate(0, 100);
        if (mFactor == 1) {
            super.dispatchDraw(canvas);
            return;
        } else if (mFactor == 0) {
            return;
        }

        for (int i = 0; i < clipCount; i++) {
            canvas.save();
            canvas.concat(matrices[i]);
            canvas.clipRect(clipWidth * i, 0, clipWidth * i + clipWidth,
                    mBitmap.getHeight());
            if (isReady) {
                canvas.drawBitmap(mBitmap, 0, 0, null);
            } else {
                super.dispatchDraw(mCanvas);
                canvas.drawBitmap(mBitmap, 0, 0, null);
                isReady = true;
            }
            canvas.translate(clipWidth * i, 0);
            if (i % 2 == 0) {
                canvas.drawRect(0, 0, clipWidth, getHeight(), mSolidPaint);
            } else {
                canvas.drawRect(0, 0, clipWidth, getHeight(), mShadowPaint);
            }
            canvas.restore();
        }
//        canvas.drawBitmap(mBitmap,matrices[7], null);
//        Log.e("此时的坐标值:", matrices[0].toShortString());
        //绘制阴影

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("ZheDie", "onMeasure");
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(),
                child.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("ZheDie", "onLayout");
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), child.getMeasuredHeight());
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        init();

    }

    public void setmFactor(float distanceX) {
        mTranslation -= distanceX;
        if (mTranslation < 0) {
            mTranslation = 0;
        }
        if (mTranslation > getWidth()) {
            mTranslation = getWidth();
        }
        float factor = Math.abs(((float) mTranslation)
                / ((float) getWidth()));
        this.mFactor = factor;
        init();
        invalidate();
    }
}
