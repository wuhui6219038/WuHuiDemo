package wuhui.wuhuidemo.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


import java.io.File;

/**
 * Created by codeest on 16/10/10.
 */

public class UpdateService extends Service {
    private BroadcastReceiver receiver;
    private static final String TAG = "UpdateService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "开始监听了");

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiver(receiver);
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/geeknews.apk")),
                        "application/vnd.android.package-archive");
                startActivity(intent);
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        startDownload();
        return Service.START_STICKY;
    }

    private void startDownload() {
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

        DownloadManager.Request request = new DownloadManager.Request(
                Uri.parse("http://www.steelhome.cn/application/steelhome.apk"));
        request.setTitle("GeekNews");
        request.setDescription("新版本下载中");
        request.setMimeType("application/vnd.android.package-archive");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "geeknews.apk");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        long reference = dm.enqueue(request);
        _initQuery(dm, reference);
        Toast.makeText(this, "后台下载中", Toast.LENGTH_SHORT).show();
    }

    private void _initQuery(DownloadManager dm, long reference) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(reference);
        Cursor myDownload = dm.query(query);

        if (myDownload.moveToFirst()) {
            Log.e(TAG, "reference=" + reference);
            int fileNameSizeId = myDownload.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int fileNameStautsId = myDownload.getColumnIndex(DownloadManager.COLUMN_STATUS);
            String fileNameSize = myDownload.getString(fileNameSizeId);
            String fileNameStauts = myDownload.getString(fileNameStautsId);
            Log.e(TAG, fileNameSize + " : " + fileNameStauts);
        }
        myDownload.close();
    }

}
