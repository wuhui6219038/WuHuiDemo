package wuhui.wuhuidemo.annotation;

import android.util.Log;

/**
 * Created by wuhui on 2017/7/24.
 */

public class AnnotationBean {
    @TestAnnotation(target = "targetrte")
    public static void post() {
        Log.e("AnnotationBean", "发射一个订阅");
    }

    @TestAnnotation(target = "targetrte")
    public void testAnnotationBean(String str) {
        Log.e("注解的方法", str);
    }

    @TestAnnotation(target = "targetrte")
    public void testAnnotationBean2(String str) {
        Log.e("注解的方法2", str);
    }
}
