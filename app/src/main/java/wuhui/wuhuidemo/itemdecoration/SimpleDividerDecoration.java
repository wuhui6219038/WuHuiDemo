package wuhui.wuhuidemo.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/7/17.
 */

public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
    private Paint dividerPaint;
    private Paint flagPaint;
    private Paint textPaint;
    private int dividerHeight = 5;
    private int dividerWidth = 15;
    private int headerHeight = 80;
    private DecorationCallback callback;

    public SimpleDividerDecoration(Context context, DecorationCallback callback) {
        this.callback = callback;
        dividerPaint = new Paint();
        flagPaint = new Paint();
        textPaint = new Paint();

        dividerPaint.setColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            int pos = parent.getChildAdapterPosition(parent.getChildAt(index));
            addHeaderItemOffsets(outRect, pos);
            addLineItemOffsets(outRect, pos, parent);
        }
    }

    /**
     * 设置头部
     */
    private void addHeaderItemOffsets(Rect outRect, int position) {
        if (isFirstLine(position)) {
            outRect.top = headerHeight;
        } else {
            outRect.top = 0;
        }
    }

    private void addLineItemOffsets(Rect outRect, int position, RecyclerView parent) {
        if (isLastLine(position, parent)) {
            outRect.bottom = 0;
        } else {
            outRect.bottom = dividerHeight;
        }
    }

    /**
     * @param position
     * @return
     */
    private boolean isFirstLine(int position) {
        if (position == 0) {
            return true;
        } else {
            return callback.getGroupId(position) != callback.getGroupId(position - 1);
        }
    }

    private boolean isLastLine(int position, RecyclerView parent) {
        if (position == parent.getAdapter().getItemCount() - 1) {
            return true;
        } else
            return callback.getGroupId(position) != callback.getGroupId(position + 1);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getLeft();
        int right = parent.getRight();
        int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = parent.getChildAt(index);
            int pos = parent.getChildAdapterPosition(view);
            if (isFirstLine(pos)) {
                addHeader(c, view.getTop(), pos, left, right);
            }
            if (!isLastLine(pos, parent)) {
                addLine(c, view, left, right);
            }
        }

    }

    private void addHeader(Canvas c, int top, int position, int left, int right) {
        String textLine = callback.getGroupFirstLineText(position).toUpperCase();
        Log.e("值", top + "");
        textPaint.setColor(Color.RED);
        Rect rect = new Rect(left, top, right, top - headerHeight);
        c.drawRect(rect, textPaint);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        int baseLineY = (int) (rect.centerY() - fontMetrics.top / 2 - fontMetrics.bottom / 2);//基线中间点的y轴计算公式
        c.drawText(textLine, left, baseLineY, textPaint);
    }

    //分割线
    private void addLine(Canvas c, View view, int left, int right) {
        float top = view.getBottom();
        float bottom = view.getBottom() + dividerHeight;
        c.drawRect(left, top, right, bottom, dividerPaint);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
     //   addHeader(c, parent, state);
        // addFlag(c, parent, state);

    }

    private void addHeader(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            View view = parent.getChildAt(index);
            int position = parent.getChildAdapterPosition(view);
            if (!isFirstLine(position)) {
                addHeader(c, view.getTop(), position, parent.getLeft(), parent.getRight());
            }
//
        }
    }

    private void addFlag(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int index = 0; index < count; index++) {
            View childView = parent.getChildAt(index);
            int pos = parent.getChildAdapterPosition(childView);
            boolean isLeft = pos % 2 == 0;
            float left = 0, right = 0;
            if (isLeft) {
                left = childView.getLeft();
                right = left + dividerWidth;
                flagPaint.setColor(Color.BLUE);
            } else {
                right = childView.getRight();

                left = right - dividerWidth;
                flagPaint.setColor(Color.LTGRAY);
            }
            float top = childView.getTop();
            float bottom = childView.getBottom();
            c.drawRect(left, top, right, bottom, flagPaint);
        }
    }

    public interface DecorationCallback {
        /**
         * 获取当前position组号
         *
         * @param position
         * @return
         */
        long getGroupId(int position);

        /**
         * 获取当前组号名
         *
         * @param position
         * @return
         */
        String getGroupFirstLineText(int position);
    }
}
