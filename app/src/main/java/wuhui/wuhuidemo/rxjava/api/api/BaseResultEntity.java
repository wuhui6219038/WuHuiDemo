package wuhui.wuhuidemo.rxjava.api.api;

import java.io.Serializable;

/**
 * Created by wuhui on 2016/12/27.
 * 接口返回数据的基类
 */

public class BaseResultEntity {
    private int Success;
    private String Message;
    private String Info;

    public int getSuccess() {
        return Success;
    }

    public void setSuccess(int success) {
        Success = success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
