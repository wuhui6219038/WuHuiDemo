package wuhui.wuhuidemo.injector.components.customer.scope.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by wuhui on 2017/7/19.
 */
@Qualifier
@Documented
@Retention(RUNTIME)
public @interface SingleBean {
}
