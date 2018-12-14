package dev.android.studydemo.recyclerview.one;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import net.accumulation.dev.android.BaseActivity;
import net.accumulation.dev.android.baseRlvAdapter.BaseLoadMoreDataAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dev.android.studydemo.R;

/**
 * Created by Administrator on 2018/12/14.
 * user: Administrator
 * date: 2018/12/14
 * time; 18:12
 * name: dev.android.studydemo.recyclerview
 */
public class LoadMoreAndEmptyActivity extends BaseActivity{
    RecyclerView rlv_list;
    LoadMoreAdapter adapter;
    List<String> strings = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_load_more;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        findViewById(R.id.tv_add_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setData(strings);
            }
        });
        rlv_list=findViewById(R.id.rlv_list);
        rlv_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LoadMoreAdapter(this, R.layout.item_listview);
        rlv_list.setAdapter(adapter);
        for (int i = 0; i < 30; i++) {
            strings.add(i + "");
        }
        rlv_list.addOnScrollListener(new recyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                adapter.setLoadState(BaseLoadMoreDataAdapter.LOADING);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<String> s=new ArrayList<>();
                                s.add("q");
                                adapter.setLoadState(BaseLoadMoreDataAdapter.LOADING_COMPLETE);
                                adapter.addAll(s);
                            }
                        });
                    }
                }, 2000);
            }
        });
    }
}
