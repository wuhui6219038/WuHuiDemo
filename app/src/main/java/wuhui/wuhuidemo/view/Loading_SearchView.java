package wuhui.wuhuidemo.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by wh on 2017/6/18.
 */

public class Loading_SearchView extends View {
    private static final String TAG = "loading";
    private Paint mPaint = null;
    private PathMeasure mPathMeasure;
    private Path circlePath;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener;
    private float mAnimatorValue = 0f;
    private ValueAnimator loading_animator;

    public Loading_SearchView(Context context) {
        super(context);
        _initAll(context);

    }

    public Loading_SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _initAll(context);
    }

    private void _initAll(Context context) {
        _initPaint(context);
        _initPath();
        _initListener();
        _initAnimator();
    }

    private void _initPaint(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.RED);
    }

    private void _initPath() {
        circlePath = new Path();
        RectF rectF = new RectF(-100, -100, 100, 100);
        circlePath.addArc(rectF, 45f, 359.9f);
        mPathMeasure = new PathMeasure();
    }

    private void _initListener() {
        mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
    }

    private void _initAnimator() {
        loading_animator = ObjectAnimator.ofFloat(0, 1).setDuration(2000);
        loading_animator.addUpdateListener(mUpdateListener);
        loading_animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "开始");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // invalidate();
                Log.e(TAG, "sss");
                loading_animator.removeAllListeners();
                _initAnimator();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        loading_animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        mPathMeasure.setPath(circlePath, false);
        Path tPath = new Path();
        Log.e("此时的值:", mAnimatorValue + "===" + mPathMeasure.getLength());
        float stop = mPathMeasure.getLength() * mAnimatorValue;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * 200f));
        mPathMeasure.getSegment(start, stop, tPath, true);
        //mPathMeasure.getSegment(mPathMeasure.getLength() * mAnimatorValue, mPathMeasure.getLength(), tPath, true);
        canvas.drawPath(tPath, mPaint);
    }
}
