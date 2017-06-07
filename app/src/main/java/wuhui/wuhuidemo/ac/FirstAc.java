package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.SlideItemView;

/**
 * Created by wuhui on 2016/12/22.
 */

public class FirstAc extends AppCompatActivity {
    @BindView(R.id.slideview)
    SlideItemView slideview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstac_layout);
        ButterKnife.bind(this);
//        ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
////                switch (event.getAction()) {
////                    case MotionEvent.ACTION_MOVE:
////                        Log.e("ss", "父试图在移动");
////                        break;
////                }
//                slideview.onTouchEvent(event);
//                return true;
//            }
//        });
    }
}
