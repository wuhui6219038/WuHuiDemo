package wuhui.wuhuidemo.view;

import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wuhui on 2017/7/11.
 * 重叠recycleView
 */

public class CardLayoutManager extends RecyclerView.LayoutManager {
    private static final int DEFAULT_SHOW_ITEM = 3;
    private static final String TAG = "CardLayoutManager";

    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;

    public CardLayoutManager(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper) {
        this.mRecyclerView = recyclerView;
        this.mItemTouchHelper = itemTouchHelper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup
                .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        for (int position = DEFAULT_SHOW_ITEM; position >= 0; position--) {
            final View view = recycler.getViewForPosition(position);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getWidth() - getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view));
//            Log.e(TAG, "高度:" + view.getMeasuredHeight());
            if (position == DEFAULT_SHOW_ITEM) {
                // 按照一定的规则缩放，并且偏移Y轴。
                // CardConfig.DEFAULT_SCALE 默认为0.1f，CardConfig.DEFAULT_TRANSLATE_Y 默认为14
                view.setScaleX(1 - (position - 1) * 0.1f);
                view.setScaleY(1 - (position - 1) * 0.1f);
                view.setTranslationY((position - 1) * view.getMeasuredHeight() / 14);
            } else if (position > 0) {
                view.setScaleX(1 - position * 0.1f);
                view.setScaleY(1 - position * 0.1f);
                view.setTranslationY(position * view.getMeasuredHeight() / 14);
            } else {
                view.setOnTouchListener(mOnTouchListener);
            }
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                Log.e(TAG, "SS");
                mItemTouchHelper.startSwipe(childViewHolder);
//                mItemTouchHelper.startDrag(childViewHolder);
            }
            return false;
        }
    };
}
