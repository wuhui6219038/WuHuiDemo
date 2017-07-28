package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import wuhui.wuhuidemo.MyApplication;
import wuhui.wuhuidemo.injector.components.ActivityComponent;
import wuhui.wuhuidemo.injector.components.DaggerActivityComponent;
import wuhui.wuhuidemo.injector.components.bean.SingleTestEntity;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.modules.ActivityModule;

/**
 * Created by wuhui on 2017/7/24.
 */

public class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder().applicationConoenpent(MyApplication.getInstance().getApplicationComponent())
                .activityModule(new ActivityModule(this)).build();
    }
}
