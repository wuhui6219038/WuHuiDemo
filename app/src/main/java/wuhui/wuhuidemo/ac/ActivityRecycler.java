package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.adapter.RecyclerViewAdapter;
import wuhui.wuhuidemo.itemdecoration.SimpleDividerDecoration;

/**
 * Created by wuhui on 2017/7/17.
 */

public class ActivityRecycler extends RxAppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<String> datas;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);
        _init();
    }

    private void _init() {
        datas = new ArrayList<>();
        mAdapter = new RecyclerViewAdapter(this);

        _initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SimpleDividerDecoration(this, new SimpleDividerDecoration.DecorationCallback() {
            @Override
            public long getGroupId(int position) {
                return Character.toUpperCase(datas.get(position).charAt(0));
            }

            @Override
            public String getGroupFirstLineText(int position) {
                return datas.get(position).substring(0, 1).toUpperCase();
            }
        }));
        recyclerView.setAdapter(mAdapter);
        mAdapter.setDatas(datas);
    }

    private void _initData() {
        datas.add("a");
        datas.add("aa");
        datas.add("aaa");
        datas.add("aaaa");
        datas.add("b");
        datas.add("bb");
        datas.add("bbb");
        datas.add("bbbb");
        datas.add("c");
        datas.add("cc");
        datas.add("ccc");
        datas.add("cccc");
        datas.add("d");
        datas.add("dd");
        datas.add("ddd");
        datas.add("dddd");
        datas.add("e");
        datas.add("ee");
        datas.add("eee");
        datas.add("eeee");
        datas.add("f");
        datas.add("ff");
        datas.add("fff");
        datas.add("ffff");
        datas.add("g");
        datas.add("gg");
        datas.add("ggg");
        datas.add("gggg");
        datas.add("ggggg");
    }
}
