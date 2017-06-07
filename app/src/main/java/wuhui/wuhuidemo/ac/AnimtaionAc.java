package wuhui.wuhuidemo.ac;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/4/10.
 */

public class AnimtaionAc extends RxAppCompatActivity {
    @BindView(R.id.targetView)
    TextView targetView;
    @BindView(R.id.btn1)
    TextView btn1;
    @BindView(R.id.btn2)
    TextView btn2;
    @BindView(R.id.activity_main)
    NestedScrollView activityMain;
    private final static String TAG = "AnimtaionAc";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                animation();
                break;
            case R.id.btn2:
                break;
        }
    }

    private void animation() {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationX", 0, 100f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "scaleY", 1,0.8f);
        animator.setEvaluator(new FloatEvaluator());
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(targetView, "scaleX", 1, 0.8f, 1);
        AnimatorSet animSet = new AnimatorSet();
//        animSet.setInterpolator(new AccelerateDecelerateInterpolator());
//        animSet.playTogether(animator);
        animSet.play(animator);
        animSet.setDuration(2000);
        animSet.start();
    }
}
