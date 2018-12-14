package dev.android.studydemo.recyclerview.one;

import android.content.Context;
import android.view.View;

import net.accumulation.dev.android.baseRlvAdapter.BaseLoadMoreDataAdapter;
import net.accumulation.dev.android.baseRlvAdapter.BaseViewHolder;

import dev.android.studydemo.R;

/**
 * Created by Administrator on 2018/12/14.
 * user: Administrator
 * date: 2018/12/14
 * time; 18:22
 * name: dev.android.studydemo.recyclerview
 */
public class LoadMoreAdapter extends BaseLoadMoreDataAdapter<String> {
    public LoadMoreAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, String item, int position) {
        holder.setText(R.id.tv_title,item);
    }

    @Override
    public void loadData(BaseViewHolder holder, int loadState) {
        if(loadState==2){
            holder.itemView.setVisibility(View.GONE);
        }else {
            holder.itemView.setVisibility(View.VISIBLE);
        }
    }
}
