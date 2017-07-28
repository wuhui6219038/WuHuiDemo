package wuhui.wuhuidemo.injector.components.modules;

import android.content.Context;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.MyApplication;

/**
 * Created by wuhui on 2017/7/18.
 */
@Module
public class AppModule {
    private final MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideAppliction() {
        return application;
    }

}
