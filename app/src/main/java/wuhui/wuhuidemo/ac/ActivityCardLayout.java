package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.adapter.CardAdapter;
import wuhui.wuhuidemo.callback.CardItemTouchHelperCallback;
import wuhui.wuhuidemo.view.CardLayoutManager;

/**
 * Created by wuhui on 2017/7/11.
 */

public class ActivityCardLayout extends RxAppCompatActivity {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private CardAdapter mAdapter;
    private CardLayoutManager cardLayoutManager;
    private CardItemTouchHelperCallback cardItemTouchHelperCallback;
    private ItemTouchHelper touchHelper;
    private List<Integer> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardlayout);
        ButterKnife.bind(this);
        initData();
        _initView();
    }

    private void _initView() {


        mAdapter = new CardAdapter(this);
        mAdapter.setData(list);
        recycler.setAdapter(mAdapter);

        cardItemTouchHelperCallback = new CardItemTouchHelperCallback(list, recycler.getAdapter());
        touchHelper = new ItemTouchHelper(cardItemTouchHelperCallback);
        touchHelper.attachToRecyclerView(recycler);
        cardLayoutManager = new CardLayoutManager(recycler, touchHelper);
        recycler.setLayoutManager(cardLayoutManager);

    }

    private void initData() {
        list = new ArrayList<>();
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);
        list.add(R.mipmap.test);

    }
}
