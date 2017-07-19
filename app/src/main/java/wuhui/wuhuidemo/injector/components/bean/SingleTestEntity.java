package wuhui.wuhuidemo.injector.components.bean;

import java.util.concurrent.RecursiveTask;

import javax.inject.Inject;

/**
 * Created by wuhui on 2017/7/18.
 */

public class SingleTestEntity {
    private String desc;
    private String msg;

    public SingleTestEntity() {
        this.desc = "没有构造方法";
    }

    public SingleTestEntity(String msg) {
        this.desc = msg;
    }

    public String getDesc() {
        return desc;
    }
}
