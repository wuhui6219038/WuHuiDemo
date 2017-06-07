package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.ZheDieView;

/**
 * Created by wuhui on 2017/2/7.
 */

public class ZheDieAc extends RxAppCompatActivity {
    @BindView(R.id.id_fold_layout)
    ZheDieView idFoldLayout;
    GestureDetector mScrollGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhedie);
        ButterKnife.bind(this);

        mScrollGestureDetector = new GestureDetector(this,
                new ScrollGestureDetector());
        idFoldLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mScrollGestureDetector.onTouchEvent(event);
            }
        });
    }

    class ScrollGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            idFoldLayout.setmFactor(distanceX);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
