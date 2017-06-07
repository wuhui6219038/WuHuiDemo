package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/1/5.
 */

public class BehaviorAc extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
    }
}
