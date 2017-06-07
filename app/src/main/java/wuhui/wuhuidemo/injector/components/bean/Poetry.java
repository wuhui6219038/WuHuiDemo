package wuhui.wuhuidemo.injector.components.bean;

import javax.inject.Inject;

/**
 * Created by wuhui on 2017/3/7.
 */

public class Poetry {
    private String mPemo;
    String test;

    // 用Inject标记构造函数,表示用它来注入到目标对象中去
//    @Inject
    public Poetry() {
        mPemo = "生活就像海洋";
    }

    public Poetry(String pomes) {
        this.mPemo = pomes;
    }

    public String getPoems() {
        return mPemo;
    }
}
