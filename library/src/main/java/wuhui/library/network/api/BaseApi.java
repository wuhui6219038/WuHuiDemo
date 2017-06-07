package wuhui.library.network.api;

import java.util.concurrent.TimeUnit;

/**
 * Created by wuhui on 2017/6/6.
 */

public class BaseApi {


    //默认的超时，读写为10s
    private static final int DEFALUT_SOCKETTIME = 1;
    private static final TimeUnit DEFALUT_TIMEUNIT = TimeUnit.SECONDS;
    /**
     * 联网地址
     */
    protected String baseUrl = "http://imobileapp.steelhome.cn/v1.4/";
    //连接时间
    protected int connectTimeout = DEFALUT_SOCKETTIME;
    //读取时间
    protected int readTimeout = DEFALUT_SOCKETTIME;
    //写入时间
    protected int writeTimeout = DEFALUT_SOCKETTIME;
    /**
     * 时间单位
     */
    protected TimeUnit timeUnit = DEFALUT_TIMEUNIT;

    public BaseApi() {

    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
