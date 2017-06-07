package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by wuhui on 2017/2/4.
 */

public class CustomET extends EditText {
    private Paint mPaint;

    public CustomET(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomET(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = new RectF(100, 100, 600, 600);
        mPaint.setColor(Color.YELLOW);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(rectF, 20, 20, mPaint);
    }
}
