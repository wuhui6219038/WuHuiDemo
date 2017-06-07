package wuhui.wuhuidemo.injector.components.modules;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.injector.components.bean.Poetry;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForActivity;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForApplication;

/**
 * Created by wuhui on 2017/3/7.
 */
@Module
public class PoetryModules {
    @TestScope
    @Provides
    Poetry providePoetry(String poems) {
        return new Poetry(poems);
    }

    @Provides
    String providePoems() {
        return "只有意志坚强的人，才能到达彼岸";
    }

    @TestScope
    @ForActivity
    @Provides
    Poetry providePoetryForActivity() {
        return new Poetry("我是为activity专用的");
    }

    @TestScope
    @ForApplication
    @Provides
    Poetry providePoetryForApplication() {
        return new Poetry("我是为Application专用的");
    }


}
