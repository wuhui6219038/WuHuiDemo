package wuhui.wuhuidemo.ac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import wuhui.wuhuidemo.R;
import wuhui.wuhuidemo.view.CommomBoom;
import wuhui.wuhuidemo.view.MyListView;

/**
 * Created by wuhui on 2017/6/12.
 * 滑动方向冲突
 */

public class Activity_Scroll_Unsame extends RxAppCompatActivity {
    @BindView(R.id.lv1)
    MyListView lv1;
    @BindView(R.id.lv2)
    MyListView lv2;
    @BindView(R.id.lv3)
    MyListView lv3;
    private static final String[] lv_data = {"第1个页面1", "第1个页面2", "第1个页面3", "第1个页面4", "第1个页面5", "第1个页面5", "第1个页面1", "第1个页面1", "第1个页面1", "第1个页面1", "第1个页面2", "第1个页面3", "第1个页面4", "第1个页面5", "第1个页面5", "第1个页面1", "第1个页面1", "第1个页面1", "第1个页面1"};
    private static final String[] lv_data2 = {"第2个页面1", "第2个页面2", "第2个页面3", "第2个页面4", "第1个页面5", "第1个页面5", "第1个页面1", "第1个页面1", "第1个页面1", "第1个页面1"};
    private static final String[] lv_data3 = {"第3个页面1", "第1个页面2", "第1个页面3", "第1个页面4", "第1个页面5", "第1个页面5", "第1个页面1", "第1个页面1", "第1个页面1", "第1个页面1"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollunsame);
        ButterKnife.bind(this);
        _initlv();
    }

    void _initlv() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, lv_data);
        ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, lv_data2);
        ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, lv_data3);
        lv1.setAdapter(adapter);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);
        final CommomBoom commomBoom = (CommomBoom) findViewById(R.id.commonbar);
        commomBoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commomBoom.doPost("接受了吗");
            }
        });
    }
}
