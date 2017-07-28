package wuhui.wuhuidemo.injector.components;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import dagger.Component;
import dagger.Provides;
import wuhui.wuhuidemo.ac.CustomViewAc;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForActivity;
import wuhui.wuhuidemo.injector.components.modules.ActivityModule;

/**
 * Created by wuhui on 2017/7/26.
 */
@TestScope
@Component(dependencies = ApplicationConoenpent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    RxPermissions getRxPermissions();

    void inject(CustomViewAc activity);
}
