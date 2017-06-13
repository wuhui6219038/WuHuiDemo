package wuhui.wuhuidemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.tencent.android.tpush.XGIOperateCallback;
//import com.tencent.android.tpush.XGPushConfig;
//import com.tencent.android.tpush.XGPushManager;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Retrofit;
import rx.Observable;
import wuhui.library.network.HttpManager;
import wuhui.library.network.api.DataApi;
import wuhui.library.network.listener.OnNextBaseAdapter;
import wuhui.wuhuidemo.HttpService.HttpGetService;
import wuhui.wuhuidemo.ac.ActivityPlayer;
import wuhui.wuhuidemo.ac.ActivityService;
import wuhui.wuhuidemo.ac.Activity_Scroll_Unsame;
import wuhui.wuhuidemo.ac.AnimtaionAc;
import wuhui.wuhuidemo.ac.AutoScrollActivity;
import wuhui.wuhuidemo.ac.CustomViewAc;
import wuhui.wuhuidemo.ac.CustomViewAndAnimationAc;
import wuhui.wuhuidemo.ac.DaggerActivity;
import wuhui.wuhuidemo.ac.FirstAc;
import wuhui.wuhuidemo.ac.GreenDbActivity;
import wuhui.wuhuidemo.ac.QuanXianAc;
import wuhui.wuhuidemo.ac.RxJavaRetrofit;
import wuhui.wuhuidemo.ac.ZheDieAc;
import wuhui.wuhuidemo.ac.ZuoBiaoXiAc;
import wuhui.wuhuidemo.entity.ZhiShuEntity;

public class MainActivity extends RxAppCompatActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.tb_toolbar)
    Toolbar tbToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tbToolbar = (Toolbar) findViewById(R.id.tb_toolbar);
        setSupportActionBar(tbToolbar);
        ButterKnife.bind(this);
        _initXinGe();
        AndPermission.with(this).requestCode(100)
                .permission(Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_SMS)
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                        AndPermission.rationaleDialog(MainActivity.this, rationale).show();
                    }
                })
                .callback(new PermissionListener() {
                    @Override
                    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                        Log.e(TAG, "onSucceed");
                    }

                    @Override
                    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                        Log.e(TAG, "onFailed");
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                            AndPermission.defaultSettingDialog(MainActivity.this, 100).show();
                        }
                    }
                }).start();
    }

    private void _initXinGe() {
//        XGPushConfig.enableDebug(this, true);
//        XGPushManager.registerPush(getApplicationContext(), new XGIOperateCallback() {
//            @Override
//            public void onSuccess(Object data, int flag) {
//                Log.d("TPush", "注册成功，设备token为：" + data);
//            }
//
//            @Override
//            public void onFail(Object data, int errCode, String msg) {
//                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "onRestoreInstanceState");
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15,R.id.btn16})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.btn1:
                intent.setClass(this, FirstAc.class);
                break;
            case R.id.btn2:
                intent.setClass(this, GreenDbActivity.class);
                break;
            case R.id.btn4:
                intent.setClass(this, CustomViewAc.class);
                break;
            case R.id.btn5:
                intent.setClass(this, QuanXianAc.class);
                break;
            case R.id.btn6:
                intent.setClass(this, ZheDieAc.class);
                break;
            case R.id.btn7:
                intent.setClass(this, ZuoBiaoXiAc.class);
                break;
            case R.id.btn8:
                intent.setClass(this, CustomViewAndAnimationAc.class);
                break;
            case R.id.btn9:
                intent.setClass(this, AutoScrollActivity.class);
                break;
            case R.id.btn10:
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                ComponentName componentName = new ComponentName("cn.steelhome.handinfo.test", "cn.steelhome.handinfo.Activity.EntryActivity");
                intent.setComponent(componentName);
//                Intent resolveIntent = getPackageManager().getLaunchIntentForPackage("cn.steelhome.handinfo.test");
//                startActivity(resolveIntent);
                break;
            case R.id.btn11:
                intent.setClass(this, DaggerActivity.class);
                break;
            case R.id.btn12:
                intent.setClass(this, AnimtaionAc.class);
                break;
            case R.id.btn13:
                intent.setClass(this, ActivityService.class);
                break;
            case R.id.btn14:
                intent.setClass(this, ActivityPlayer.class);
                break;
            case R.id.btn15:
                intent.setClass(this, RxJavaRetrofit.class);
                break;
            case R.id.btn16:
                intent.setClass(this, Activity_Scroll_Unsame.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100: {
                Toast.makeText(this, "hehe", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }

//    private void test() {
//        OnNextBaseAdapter<ZhiShuEntity> onNextBaseAdapter = new OnNextBaseAdapter<ZhiShuEntity>() {
//            @Override
//            public void onNext(ZhiShuEntity data) {
//                Log.e(TAG, data.toString());
//            }
//        };
//        final Map<String, Object> params = new HashMap<String, Object>();
//        params.put("action", "GetShpiInfo");
//        params.put("mod", "shpi");
//        params.put("SignCS", "1234567890");
//        HttpManager httpManager = new HttpManager(onNextBaseAdapter, this);
//        httpManager.doHttpApiActivity(new DataApi() {
//            @Override
//            public Observable getObservable(Retrofit retrofit) {
//                return retrofit.create(HttpGetService.class).getZhiShuData(params);
//            }
//        });
//    }
}
