package wuhui.wuhuidemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by wuhui on 2017/6/12.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercept = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                intercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //不需要翻页的
//                if (getFirstVisiblePosition() == 0 && getLastVisiblePosition() == getCount() - 1) {
//                    intercept = false;
//                } else {
//                    //还没有滑到底部
//                    if (getFirstVisiblePosition() >= 0 && getLastVisiblePosition() != getCount() - 1) {
//                        intercept = true;
//                    } else {
//
//                    }
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                intercept = false;
//                break;
//            default:
//                intercept = false;
//                break;
//        }
//        return intercept;
//    }
}
