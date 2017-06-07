package wuhui.library.network.download;

/**
 * Created by wuhui on 2017/3/23.
 * 下载成功回调接口
 */

public interface DownloadListener {
    /**
     * @param read        读取的长度
     * @param totallength 总长度
     * @param done        是否完成
     */
    void download(long read, long totallength, boolean done
    );
}
