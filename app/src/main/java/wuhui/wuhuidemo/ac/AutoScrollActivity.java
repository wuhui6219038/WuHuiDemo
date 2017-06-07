package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/2/16.
 */

public class AutoScrollActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scroll);
    }

}
