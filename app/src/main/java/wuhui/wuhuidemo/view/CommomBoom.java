package wuhui.wuhuidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wuhui on 2017/6/13.
 * 一个普通的球
 */

public class CommomBoom extends View {
    private Paint mPaint = null;
    private static final int DEFALUT_WITDTH = 200;
    private static final int DEFALUT_HEIGHT = 200;
    private Message message;

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
        if (getHandler() != null)
            message = getHandler().obtainMessage();
        else {
            Log.e("error", "getHandler为空了");
            message = new Message();
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算高度
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (modeHeight == MeasureSpec.AT_MOST && modeWidth == MeasureSpec.AT_MOST) {
            setMeasuredDimension(DEFALUT_WITDTH, DEFALUT_HEIGHT);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    public void doPost(String msg) {
        Log.e("收到信息", msg);
    }
}
