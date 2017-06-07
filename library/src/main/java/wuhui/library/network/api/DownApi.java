package wuhui.library.network.api;


import wuhui.library.network.bean.DownState;
import wuhui.library.network.listener.HttpDownOnNextAdapter;
import wuhui.library.network.service.HttpDownService;

/**
 * Created by wuhui on 2017/3/23.
 */

public class DownApi extends BaseApi {
    //保存的路径
    private String savaPath;
    //下载地址
    private String downUrl;
    private int stateInte;
    private HttpDownOnNextAdapter adapter;
    /*下载长度*/
    private long readLength;
    /*文件总长度*/
    private long countLength;
    /**
     * 显示下载的notification
     */
    private boolean notificationShow;

    private HttpDownService httpDownService;

    public HttpDownService getHttpDownService() {
        return httpDownService;
    }

    public void setHttpDownService(HttpDownService httpDownService) {
        this.httpDownService = httpDownService;
    }

    public boolean isNotificationShow() {
        return notificationShow;
    }

    public void setNotificationShow(boolean notificationShow) {
        this.notificationShow = notificationShow;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public DownApi(String url) {
        this.downUrl = url;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public String getSavaPath() {
        return savaPath;
    }

    public void setSavaPath(String savaPath) {
        this.savaPath = savaPath;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public HttpDownOnNextAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HttpDownOnNextAdapter adapter) {
        this.adapter = adapter;
    }


    public DownState getState() {
        switch (getStateInte()) {
            case 0:
                return DownState.START;
            case 1:
                return DownState.DOWN;
            case 2:
                return DownState.PAUSE;
            case 3:
                return DownState.STOP;
            case 4:
                return DownState.ERROR;
            case 5:
            default:
                return DownState.FINISH;
        }
    }

    public void setState(DownState state) {
        setStateInte(state.getState());
    }


    public int getStateInte() {
        return stateInte;
    }

    public void setStateInte(int stateInte) {
        this.stateInte = stateInte;
    }

}
