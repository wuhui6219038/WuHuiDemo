package wuhui.wuhuidemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;

/**
 * Created by wuhui on 2017/7/11.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private Context context;
    private List<Integer> data;

    public CardAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardHolder holder = new CardHolder(LayoutInflater.from(
                context).inflate(R.layout.item_card_layout, parent,
                false));
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
//        holder.pic.set(context.getResources().getDrawable(data.get(position), null));
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List data) {
        this.data = data;
    }


    class CardHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pic)
        ImageView pic;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.age)
        TextView age;
        @BindView(R.id.xingzuo)
        TextView xingzuo;
        @BindView(R.id.desc)
        TextView desc;


        public CardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }
}
