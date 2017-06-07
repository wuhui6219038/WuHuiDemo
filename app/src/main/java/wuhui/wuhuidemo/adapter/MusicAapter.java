package wuhui.wuhuidemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.entity.MusicBean;

/**
 * Created by wuhui on 2017/4/14.
 */

public class MusicAapter extends RecyclerView.Adapter<MusicAapter.MusicHolder> {
    private Context context;
    private List<MusicBean> musics;
    private ItemClickListenser listenser;

    public MusicAapter(Context context, List<MusicBean> musics) {
        this.context = context;
        this.musics = musics;
    }

    public MusicAapter(Context context) {
        this.context = context;
    }

    @Override
    public MusicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicHolder holder = new MusicHolder(LayoutInflater.from(
                context).inflate(R.layout.item_music, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicHolder holder,final int position) {
        holder.tv_author.setText(musics.get(position).getMusicAuthor());
        holder.tv_musicName.setText(musics.get(position).getMusicTitle());
        holder.palyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenser.onclick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (musics == null) {
            return 0;
        } else
            return musics.size();
    }

    public void setData(List<MusicBean> musics) {
        this.musics = musics;
        notifyDataSetChanged();
    }

    public void setOnItemClickListenser(ItemClickListenser listenser) {
        this.listenser = listenser;
    }

    class MusicHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.author)
        TextView tv_author;
        @BindView(R.id.musicName)
        TextView tv_musicName;
        @BindView(R.id.palyView)
        LinearLayout palyView;

        public MusicHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


    }

    public interface ItemClickListenser {
        void onclick(int position);
    }


}

