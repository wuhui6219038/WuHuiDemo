package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wuhui on 2017/6/13.
 * 一个普通的球
 */

public class CommomBoom extends View {
    private Paint mPaint = null;

    public CommomBoom(Context context) {
        super(context);
        _init();
    }

    public CommomBoom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    public CommomBoom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _init();
    }

    private void _init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, mPaint);

    }
}
