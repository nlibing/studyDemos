package dev.android.studydemo.recyclerview.two;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import net.accumulation.dev.android.baseRlvAdapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import dev.android.studydemo.R;


/**
 * Created by Administrator on 2018/11/22.
 * user: Administrator
 * date: 2018/11/22
 * time; 14:36
 * name:
 */
public class DifferenceRecycleActivity extends AppCompatActivity {
    RecyclerView rlv_list;
    List<DifferenceBean> differenceBeans = new ArrayList<>();
    DifferenceDataAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difference);
        rlv_list = findViewById(R.id.rlv_list);
        rlv_list.setLayoutManager(new GridLayoutManager(this, 6, LinearLayoutManager.VERTICAL,false));
        for (int i = 0; i < 9; i++) {
            differenceBeans.add(new DifferenceBean(1, "1" + i));
            differenceBeans.add(new DifferenceBean(2, "2" + i));
            differenceBeans.add(new DifferenceBean(2, "2" + i));
            differenceBeans.add(new DifferenceBean(2, "2" + i));
            differenceBeans.add(new DifferenceBean(2, "2" + i));
            differenceBeans.add(new DifferenceBean(3, "3" + i));
            differenceBeans.add(new DifferenceBean(3, "3" + i));
            differenceBeans.add(new DifferenceBean(3, "3" + i));
        }
        adapter = new DifferenceDataAdapter(this, differenceBeans) {
            @Override
            public void convert(BaseViewHolder holder, DifferenceBean item, int position) {
                if (item.getType() == 1) {
                    holder.setText(R.id.tv_title_name, item.getName());
                } else if (item.getType() == 2) {
                    holder.setText(R.id.tv_two_title, item.getName());
                } else {
                    holder.setText(R.id.tv_three_title, item.getName());
                }
            }
        };
        rlv_list.setAdapter(adapter);
    }
}
