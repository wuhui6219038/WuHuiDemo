package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by wuhui on 2017/2/10.
 */

public class RegionClickView extends View {
    private Paint mPaint;
    private Region mRegion;
    private Path mPath;
    private static final String TAG = "RegionClickView";

    public RegionClickView(Context context) {
        super(context);
    }

    public RegionClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mRegion = new Region();
        mPath = new Path();
//        mPath.addCircle(200, 200, 100, Path.Direction.CW);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
//        mPath.addCircle(w / 2, h / 2, 100, Path.Direction.CW);
        RectF bigRect = new RectF(0f, 0f, 100f, 100f);
        mPath.addArc(bigRect, -30f, -120f);
        RectF smallRect = new RectF(0, 20, 100, 120);
        mPath.arcTo(smallRect,  -150f ,120);

        Region globeRegion = new Region(0, 0, w, h);
        mRegion.setPath(mPath, globeRegion);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                Log.e(TAG, "LALALAL");
                if (mRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "圆被点击", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
