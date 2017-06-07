package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.CustomView;
import wuhui.wuhuidemo.view.LoadView;
import wuhui.wuhuidemo.view.RadarView;

/**
 * Created by wuhui on 2017/1/9.
 * 自定义view
 */

public class CustomViewAc extends RxAppCompatActivity {
    @BindView(R.id.animation)
    Button animation;
    @BindView(R.id.cv)
    CustomView cv;
    @BindView(R.id.lv)
    LoadView lv;
    @BindView(R.id.rv)
    RadarView rv;
    private Animation myAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        ButterKnife.bind(this);
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
    }

    @OnClick(R.id.animation)
    public void onClick() {
        rv.startAnimation();
//        cv.startAnimation(myAnimation);
//        cv.startDraw();
//        cv2.unCheck();
//        lv.startLoad();
    }


}
