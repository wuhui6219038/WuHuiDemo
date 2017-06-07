package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RadioGroup;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/4/17.
 */

public class HandlerActivity extends RxAppCompatActivity {
    private static final String TAG = "HandlerActivity";
    @BindView(R.id.type)
    RadioGroup type;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e(TAG, "普通handler");
        }
    };

    private Handler handler2 = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "handler.callback");
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        ButterKnife.bind(this);
    }

    @OnCheckedChanged(R.id.type)
    void onCheckedChanged() {
        int id = type.getCheckedRadioButtonId();

        if (id == type.getChildAt(0).getId()) {
            Message msg = Message.obtain();
            handler.sendMessage(msg);
        } else if (id == type.getChildAt(1).getId()) {
            Message msg = Message.obtain();
            handler2.sendMessage(msg);
        } else if (id == type.getChildAt(2).getId()) {
            Message msg = Message.obtain();
            handler2.sendMessage(msg);
        } else if (id == type.getChildAt(3).getId()) {
            Message msg = Message.obtain();
            handler2.sendMessage(msg);
        }
    }
}
