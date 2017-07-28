package wuhui.wuhuidemo.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import wuhui.wuhuidemo.ac.DaggerActivity;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForActivity;
import wuhui.wuhuidemo.injector.components.modules.SingleModule;
import wuhui.wuhuidemo.injector.components.modules.UitlModule;

/**
 * Created by wuhui on 2017/7/18.
 */
@TestScope
@Component(dependencies = ApplicationConoenpent.class, modules = {SingleModule.class, UitlModule.class})
public interface SingleConponet {
    void inject(DaggerActivity activity);
}
