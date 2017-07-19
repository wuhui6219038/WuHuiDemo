package wuhui.wuhuidemo.ac;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.MyApplication;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.injector.components.DaggerSingleConponet;
import wuhui.wuhuidemo.injector.components.bean.Poetry;
import wuhui.wuhuidemo.injector.components.bean.SingleTestEntity;

/**
 * Created by wuhui on 2017/3/7.
 */

public class DaggerActivity extends RxAppCompatActivity {
    @BindView(R.id.dagger)
    TextView dagger;
    @Inject()
    SingleTestEntity singleTestEntity;
    @Inject()
    Poetry mPotry;
//    @Inject
//    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);
        DaggerSingleConponet.builder().build().inject(this);
        initView();


    }

    private void initView() {
        Log.e("此时的SingleTestEntity", singleTestEntity.getDesc() + " " + mPotry.getPoems());
    }
}
