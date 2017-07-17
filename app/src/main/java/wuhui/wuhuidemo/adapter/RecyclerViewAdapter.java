package wuhui.wuhuidemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/7/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.TestViewHolder> {
    private List<String> datas;
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_info_layout, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        holder.tv_info.setText(datas.get(position) + "desc");
        holder.tv_title.setText(datas.get(position));
    }


    @Override
    public int getItemCount() {
        if (datas == null)
            return 0;
        else
            return datas.size();
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    final class TestViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_info;

        public TestViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.title);
            tv_info = (TextView) itemView.findViewById(R.id.info);
        }
    }
}
