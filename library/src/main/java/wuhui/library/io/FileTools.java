package wuhui.library.io;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import wuhui.library.network.api.DownApi;

/**
 * Created by wuhui on 2017/3/24.
 * 文件管理工具类
 */

public class FileTools {
    private static final String TAG = "FileTools";

    /**
     * 将下载的软件写入到sd中
     */
    public static void write2Sd(ResponseBody responseBody, File file, DownApi info) throws IOException {
        if (file == null) {
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "test" + ".apk");

        }
        Log.e(TAG, "文件名" + file.toString());
        if (!file.getParentFile().exists()) {
            if (file.getParentFile().mkdirs()) {
                Log.e(TAG, "文件创建失败");
            }
        }
        long allLength;
        if (info.getCountLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getCountLength();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(responseBody.byteStream());
        byte[] buffer = new byte[1024];
        int len;
        int total = 0;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
            total += len;
            //获取当前下载量
//            pd.setProgress(total);
        }


//        FileChannel channel = null;
//        RandomAccessFile aFile = new RandomAccessFile(file, "rwd");
//        channel = aFile.getChannel();
//        MappedByteBuffer mappedBuffer = channel.map(FileChannel.MapMode.READ_WRITE,
//                info.getReadLength(), allLength - info.getReadLength());
//        mappedBuffer.flip();
//        //每次读取8kb
//        byte[] buffer = new byte[1024];
//        int len;
//        int record = 0;
//        while ((len = responseBody.byteStream().read(buffer)) != -1) {
//            Log.e(TAG, "此时的buffer" + buffer.length);
//            try {
//                mappedBuffer.put(buffer, 0, len);
////                record += len;
//            } catch (Exception E) {
//                Log.e(TAG, "此时的len" + len);
//            }
//
//        }
        responseBody.byteStream().close();
//        if (channel != null) {
//            channel.close();
//        }
//        if (channel != null) {
//            channel.close();
//        }
    }

}
