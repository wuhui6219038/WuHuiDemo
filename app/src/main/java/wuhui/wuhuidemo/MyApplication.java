package wuhui.wuhuidemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import wuhui.wuhuidemo.injector.components.ApplicationConoenpent;
import wuhui.wuhuidemo.injector.components.DaggerApplicationConoenpent;
import wuhui.wuhuidemo.injector.components.modules.AppModule;

/**
 * Created by wuhui on 2017/3/8.
 */

public class MyApplication extends Application {
    private ApplicationConoenpent mApplicationComponent;
    private static MyApplication sApplication;

    public static MyApplication getInstance() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationConoenpent.builder().appModule(new AppModule(this)).build();
        }
    }

    public ApplicationConoenpent getApplicationComponent() {
        return mApplicationComponent;
    }

}
