package wuhui.wuhuidemo.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/7/4.
 */

public class SubmitButton extends View implements View.OnClickListener {
    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;
    private Paint mPaint;
    private Paint textPaint;
    private Paint okPaint;
    private float circleAngle = 0;
    RectF rectF = new RectF(-100, -50, 200, 100);
    private ValueAnimator animator_rect_to_angle;
    private ValueAnimator animator_angle_to_circle;
    private ValueAnimator animator_draw_ok;
    private boolean isOk = false;
    private int duration = 2000;
    /**
     * 默认宽度
     */
    private int default_width;
    private int two_circle_distance;
    private PathMeasure pathMeasure;
    /**
     * 动画集
     */
    private AnimatorSet animatorSet;
    private Path okPath;
    private float precent;
    private Bitmap bitmap = null;
    private DashPathEffect effect;

    public SubmitButton(Context context) {
        super(context);
        _init();

    }

    public SubmitButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        default_width = (w - h) / 2;
        initAnimation();
        _initOk();
        Log.e("btn", "尺寸变了");
    }

    private void _init() {
        mPaint = new Paint();
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
        okPaint = new Paint();
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setStrokeWidth(10);
        setOnClickListener(this);
        okPath = new Path();
        bitmap = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.ic_check_white_24dp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("宽度", mode + "  " + size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //300*150
        //RectF rectF = new RectF(-100, -50, 200, 100);
        rectF.left = two_circle_distance;
        rectF.right = width - two_circle_distance;
        rectF.top = 0;
        rectF.bottom = height;

        drawRoundRect(canvas);
        if (isOk) {
            canvas.drawPath(okPath, okPaint);
        }
    }

    private void drawRoundRect(Canvas canvas) {
        canvas.drawRoundRect(rectF, circleAngle, circleAngle, mPaint);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        String msg = "确认完成";
        int length = msg.length();
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseLine = (int) ((rectF.top + rectF.bottom - fontMetrics.top - fontMetrics.bottom) / 2);
        canvas.drawText(msg, rectF.centerX(), baseLine, textPaint);
    }

    private void _initOk() {
        okPath.moveTo(width / 2 - height / 4, height / 2);
        okPath.lineTo(width / 2, height / 2 + height / 6);
        okPath.lineTo(width / 2 + height / 4, height / 2 - height / 4);
        pathMeasure = new PathMeasure(okPath, true);
//        canvas.drawBitmap(bitmap, width / 2, height / 2, mPaint);
    }

    private void initAnimation() {
        set_rect_to_angle_animation();
        set_angle_to_circel();
        setOk();
    }


    private void set_rect_to_angle_animation() {
        animator_rect_to_angle = ValueAnimator.ofInt(0, getHeight() / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void set_angle_to_circel() {
        animator_angle_to_circle = ValueAnimator.ofInt(0, default_width);
        animator_angle_to_circle.setDuration(duration);
        animator_angle_to_circle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();
                int alpha = 255 - (two_circle_distance * 255) / default_width;
                textPaint.setAlpha(alpha);
                invalidate();
            }
        });
    }

    private void setOk() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                isOk = true;
                invalidate();
            }
        });
    }

    @Override
    public void onClick(View v) {
        animatorSet = new AnimatorSet();
        animatorSet.play(animator_draw_ok).after(animator_angle_to_circle).after(animator_rect_to_angle);
        animatorSet.start();
    }
}
