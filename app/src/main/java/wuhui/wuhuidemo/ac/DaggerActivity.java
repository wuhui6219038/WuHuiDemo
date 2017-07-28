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
import wuhui.wuhuidemo.injector.components.bean.TestBean;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.modules.SingleModule;

/**
 * Created by wuhui on 2017/3/7.
 */

public class DaggerActivity extends BaseActivity {
    private static final String TAG = "DaggerActivity";
    @BindView(R.id.dagger)
    TextView dagger;
    @Inject
    @TestScope
    SingleTestEntity singleTestEntity;

    @Inject
    Poetry mPotry;
    @Inject
    TestBean testBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);
        DaggerSingleConponet.builder().applicationConoenpent(MyApplication.getInstance().getApplicationComponent()).singleModule(new SingleModule()).build().inject(this);
        initView();


    }

    private void initView() {
        testBean.desc();
    }
}
