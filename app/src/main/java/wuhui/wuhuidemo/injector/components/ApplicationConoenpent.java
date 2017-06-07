package wuhui.wuhuidemo.injector.components;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import wuhui.wuhuidemo.injector.components.modules.MainModules;

/**
 * Created by wuhui on 2017/3/8.
 */
@Singleton
@Component(modules = {MainModules.class})
public interface ApplicationConoenpent {
    Gson getGson();
}
