package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ViewFlipper;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/7/4.
 */

public class AllKindsAnimtion extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_kind_animation);
        ((ViewFlipper) findViewById(R.id.viewFilpper)).startFlipping();
    }
}
