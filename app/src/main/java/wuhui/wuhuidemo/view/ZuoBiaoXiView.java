package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wuhui on 2017/2/10.
 */

public class ZuoBiaoXiView extends View {
    private Paint mPaint;
    private Path mPath;

    private float down_x, down_y;

    public ZuoBiaoXiView(Context context) {
        super(context);
        init();
    }

    public ZuoBiaoXiView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] pst = {down_x, down_y};
        canvas.translate(getWidth() / 2, getHeight() / 2);
        Matrix matrix = new Matrix();
        Log.e("最初的矩阵", canvas.getMatrix().toShortString());
        canvas.getMatrix().invert(matrix);
        Log.e("初次变换的矩阵", matrix.toShortString());
        Matrix afterMatrix = new Matrix();
        matrix.invert(afterMatrix);
        Log.e("在此变换的矩阵", afterMatrix.toShortString());
//        matrix.mapPoints(pst);
//        afterMatrix.mapPoints(pst);
        mPath.addCircle(pst[0], pst[1], 10, Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                down_x = event.getX();
                down_y = event.getY();
//                down_x = event.getRawX();
//                down_y = event.getRawY();
                break;
        }
        invalidate();
        return true;
    }
}
