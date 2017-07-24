package wuhui.wuhuidemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import wuhui.wuhuidemo.rxjava.api.api.BaseResultEntity;

/**
 * Created by wuhui on 2017/7/24.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface TestAnnotation {
    String target() default "test";

    Class dataType() default Object.class;
}
