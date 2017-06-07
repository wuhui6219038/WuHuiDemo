package wuhui.wuhuidemo.ac;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/1/18.
 * 权限问题
 */

public class QuanXianAc extends RxAppCompatActivity {
    private int MYCODE = 0x10;
    private Intent intent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanxian);
    }

    private void initPermission(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                    print("拒绝授权之后再次授权了");
                } else
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MYCODE);
            } else {
                print("已经授权了");
                startActivity(intent);
            }
        } else {
            print("低版本的");
            startActivity(intent);
        }
    }

    private void print(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    print("重新授权了");
                    startActivity(intent);
                } else {
                    print("被拒绝授权了");
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void doCall(View view) {
        intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "10086");
        intent.setData(data);
        initPermission(intent);

    }

    private void call() {

    }
}
