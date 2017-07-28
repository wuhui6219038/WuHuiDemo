package wuhui.wuhuidemo.injector.components.modules;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForActivity;

/**
 * Created by wuhui on 2017/7/26.
 */
@Module

public class ActivityModule {
    private RxAppCompatActivity activity;

    public ActivityModule(RxAppCompatActivity activity) {
        this.activity = activity;
    }

    @TestScope
    @Provides
    RxPermissions provideRermissions() {
        return new RxPermissions(activity);
    }
}
