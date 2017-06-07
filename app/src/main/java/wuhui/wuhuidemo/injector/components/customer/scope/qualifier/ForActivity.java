package wuhui.wuhuidemo.injector.components.customer.scope.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by wuhui on 2017/3/8.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ForActivity {
}
