package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.MyApplication;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.injector.components.DaggerActivityConponent;
import wuhui.wuhuidemo.injector.components.bean.Poetry;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;

/**
 * Created by wuhui on 2017/3/7.
 */

public class DaggerActivity extends RxAppCompatActivity {
    @BindView(R.id.dagger)
    TextView dagger;
    @TestScope
    @Inject
    Poetry mPoetry;
    @TestScope
    @Inject
    Poetry mPoetry2;
    @TestScope
    @Inject
    Poetry mPoetry3;
    @Inject
    Gson mGson;
    @Inject
    Gson mGson2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);
        ButterKnife.bind(this);
        DaggerActivityConponent.builder().applicationConoenpent(MyApplication.getInstance().getApplicationComponent()).build().inject(this);
        initView();
    }

    private void initView() {
        dagger.setText(mGson.toJson(mPoetry) + "=====" + mGson2.toJson(mPoetry2) + "===========" + mGson2.toJson(mPoetry3));
    }
}
