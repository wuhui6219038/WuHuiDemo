package wuhui.wuhuidemo.injector.components.modules;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.injector.components.customer.scope.TestScope;

/**
 * Created by wuhui on 2017/3/7.
 */
@Module
public class MainModules {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
