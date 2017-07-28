package wuhui.wuhuidemo.ac;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.injector.components.DaggerActivityComponent;
import wuhui.wuhuidemo.view.CustomView;
import wuhui.wuhuidemo.view.LoadView;
import wuhui.wuhuidemo.view.RadarView;

/**
 * Created by wuhui on 2017/1/9.
 * 自定义view
 */

public class CustomViewAc extends BaseActivity {
    @BindView(R.id.animation)
    Button animation;
    @BindView(R.id.cv)
    CustomView cv;
    @BindView(R.id.lv)
    LoadView lv;
    @BindView(R.id.rv)
    RadarView rv;
    @BindView(R.id.info)
    EditText info;
    private Animation myAnimation;
    private static final String TAG = "CustomViewAc";
    @Inject
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customview);
        ButterKnife.bind(this);
        myAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        getActivityComponent().inject(this);
        rxPermissions.request(Manifest.permission.INTERNET)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                });
        RxView.clicks(animation)
                .compose(rxPermissions.ensure(Manifest.permission.INTERNET))
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {

                    }
                });
        RxTextView.textChanges(info)
                // .debounce(200, TimeUnit.MILLISECONDS)

//                .switchMap(new Func1<CharSequence, Observable<String>>() {
//                    @Override
//                    public Observable<String> call(CharSequence charSequence) {
//                        return Observable.just(charSequence.toString());
//                    }
//                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        Log.e(TAG, "call: " + o);
                    }
                });

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
