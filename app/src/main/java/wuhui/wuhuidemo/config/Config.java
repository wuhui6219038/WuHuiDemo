package wuhui.wuhuidemo.config;

/**
 * Created by wuhui on 2016/12/28.
 */

public class Config {
    //设置请求时间
    public static final int CONNECTIONTIME = 10;
    //有网状态下缓存的时间
    public static final int COOKIENETWORKTIME = 60;
    //没有网状态下缓存的时间
    public static final int COOKIENONETWORKTIME = 24 * 60 * 60 * 30;
    //数据请求失败
    public static final int DATA_FAIL = 0;
    //数据请求成功
    public static final int DATA_SUCCESS = 1;

    //请求失败 请求次数
    public static final int REPEATCOUNT = 5;
    //请求失败 设置请求间隔
    public static final int REPEATDELAY = 1000;

    /**
     * 接口
     */
    public static String BASEURL = "http://imobileapp.steelhome.cn/v1.4/";

}
