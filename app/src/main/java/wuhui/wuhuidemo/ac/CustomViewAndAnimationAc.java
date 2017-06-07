package wuhui.wuhuidemo.ac;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.CustomViewAndAnimation;

/**
 * Created by wuhui on 2017/2/15.
 */

public class CustomViewAndAnimationAc extends RxAppCompatActivity {
    @BindView(R.id.customViewAndAnimation)
    CustomViewAndAnimation customViewAndAnimation;
    @BindView(R.id.move)
    Button move;
    @BindView(R.id.move_miss)
    Button moveMiss;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.move, R.id.move_miss, R.id.move_miss_scale, R.id.move_path, R.id.demo1})
    public void onClick(View view) {
        ObjectAnimator move = ObjectAnimator.ofFloat(customViewAndAnimation, "translationX", 0, 100f);
        ObjectAnimator move2 = ObjectAnimator.ofFloat(customViewAndAnimation, "translationX", 100, 0);
        ObjectAnimator dismis = ObjectAnimator.ofFloat(customViewAndAnimation, "alpha", 1f, 0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(customViewAndAnimation, "scaleX", 1f, 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(customViewAndAnimation, "scaleY", 1f, 3f, 1f);
        Path mPath = new Path();
        mPath.addCircle(300, 300, 100, Path.Direction.CW);
        ObjectAnimator pathAnimator = ObjectAnimator.ofFloat(customViewAndAnimation, "translationX", "translationY", mPath);
        AnimatorSet animSet = new AnimatorSet();
        switch (view.getId()) {
            case R.id.move:
                animSet.setDuration(2000);
//                move.setRepeatCount(ValueAnimator.INFINITE);
//                move.setRepeatMode(ValueAnimator.INFINITE);
//                move2.setRepeatCount(ValueAnimator.INFINITE);
//                move2.setRepeatMode(ValueAnimator.INFINITE);
                animSet.play(move2).after(move);
                animSet.start();
                break;
            case R.id.move_miss:

                animSet.play(move).with(dismis);
                animSet.setDuration(5000);
                animSet.start();
                break;
            case R.id.move_miss_scale:
                animSet.play(scaleX);
                animSet.setDuration(3000);
                animSet.start();
                break;
            case R.id.move_path:
                pathAnimator.setDuration(2000);
                pathAnimator.start();
                break;
            case R.id.demo1:
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                        R.animator.property_animator);
                set.setTarget(customViewAndAnimation);
                set.start();
                break;
        }
    }
}
