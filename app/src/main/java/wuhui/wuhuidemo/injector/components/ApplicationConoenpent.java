package wuhui.wuhuidemo.injector.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import wuhui.wuhuidemo.injector.components.modules.AppModule;

/**
 * Created by wuhui on 2017/3/8.
 */
@Component(modules = AppModule.class)
public interface ApplicationConoenpent {
//    Context context();
}
