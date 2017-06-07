package wuhui.wuhuidemo.ac;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.IMyAidlInterface;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.service.MyService;

/**
 * Created by wuhui on 2017/4/11.
 */

public class ActivityService extends RxAppCompatActivity {
    private static final String TAG="ActivityService";
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    private Intent intent;
    private IMyAidlInterface mIMyAidlInterface;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.start, R.id.stop})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.start:
                Log.e("S", "SS");
              //  intent = new Intent(this, MyService.class);
                bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.stop:
                unbindService(mServiceConnection);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG,"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG,"onRestoreInstanceState");
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                mIMyAidlInterface.hello("World!!!");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("ActivityService", "onServiceDisconnected: " + name);
        }
    };
}
