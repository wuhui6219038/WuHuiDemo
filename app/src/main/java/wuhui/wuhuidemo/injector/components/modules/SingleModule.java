package wuhui.wuhuidemo.injector.components.modules;

import android.util.Log;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import wuhui.wuhuidemo.injector.components.bean.Poetry;
import wuhui.wuhuidemo.injector.components.bean.SingleTestEntity;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.SingleBean;
import wuhui.wuhuidemo.injector.components.customer.scope.qualifier.PotryBean;

/**
 * Created by wuhui on 2017/7/18.
 */
@Module
public class SingleModule {
    @Provides
    SingleTestEntity provideSingleTestEntity(@SingleBean String msg) {
        return new SingleTestEntity(msg);
    }

    @Provides
    Poetry providePoetry(@PotryBean String msg) {
        return new Poetry(msg);
    }


    @Provides
    @SingleBean
    String getTestMsg() {
        return "我就是个单例";
    }

    @Provides
    @PotryBean
    String getTest2Msg() {
        return "我是一首美丽的诗";
    }

    @Inject
    void _initTest() {
        Log.e("ss", "我被调用了");

    }
}
