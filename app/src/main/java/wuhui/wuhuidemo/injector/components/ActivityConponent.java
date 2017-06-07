package wuhui.wuhuidemo.injector.components;

import dagger.Component;
import dagger.Provides;
import wuhui.wuhuidemo.ac.DaggerActivity;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.modules.MainModules;
import wuhui.wuhuidemo.injector.components.modules.PoetryModules;

/**
 * Created by wuhui on 2017/3/7.
 */
@TestScope
@Component(dependencies = {ApplicationConoenpent.class}, modules = {PoetryModules.class})
public interface ActivityConponent {
    void inject(DaggerActivity activity);
}
