package wuhui.wuhuidemo.injector.components.modules;

import com.google.gson.Gson;

import javax.inject.Named;

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

    @Provides
    Poetry providePoetry() {
        return new Poetry("你好");
    }

    @Provides
    Gson provideGson() {
        return new Gson();
    }
}
