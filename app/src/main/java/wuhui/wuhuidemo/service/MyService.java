package wuhui.wuhuidemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import wuhui.wuhuidemo.IMyAidlInterface;

/**
 * Created by wuhui on 2017/4/11.
 */

public class MyService extends Service {
    private static final String TAG = "MyService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "服务启动了");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        Log.e(TAG, "服务关闭了");
        super.onDestroy();
    }

    public final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {
        @Override
        public void hello(String msg) throws RemoteException {
            Log.e(TAG, msg);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
