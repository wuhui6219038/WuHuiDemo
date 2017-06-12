package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.TestTv;

/**
 * Created by wuhui on 2017/2/16.
 */

public class AutoScrollActivity extends RxAppCompatActivity {
    TestTv tv1;
    TextView tv2;
    TextView tv3;
    private static final String TAG = "AutoScrollActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
        tv1 = (TestTv) findViewById(R.id.tv1);
//        tv1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.e("AutoScrollActivity", "子控件");
//                return false;
//            }
//        });
//
//
//        tv1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.e("点击的是", ((TextView) view).getText().toString());
//            }
//        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e(TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent");
        return super.onTouchEvent(event);
    }
    //    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tv1:
//                break;
//            case R.id.tv2:
//                break;
//            case R.id.tv3:
//                break;
//        }
//        Log.e("点击的是", ((TextView) view).getText().toString());
//    }
}
