package wuhui.wuhuidemo.injector.components.modules;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.ForActivity;

/**
 * Created by wuhui on 2017/7/18.
 * 工具类
 */
@Module
public class UitlModule {
    @Provides
    Gson provideGson() {
        return new Gson();
    }
}
