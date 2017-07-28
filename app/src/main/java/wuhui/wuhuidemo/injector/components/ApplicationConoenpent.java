package wuhui.wuhuidemo.injector.components;

import android.content.Context;

import com.tbruyelle.rxpermissions.RxPermissions;

import javax.inject.Singleton;

import dagger.Component;
import wuhui.wuhuidemo.injector.components.modules.AppModule;

/**
 * Created by wuhui on 2017/3/8.
 */
@Singleton
@Component(modules = AppModule.class)
public interface ApplicationConoenpent {
    Context context();


}
