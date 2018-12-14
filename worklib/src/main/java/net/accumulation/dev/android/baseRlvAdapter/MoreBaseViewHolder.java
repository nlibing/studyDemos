package net.accumulation.dev.android.baseRlvAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2018/11/26.
 * user: Administrator
 * date: 2018/11/26
 * time; 9:32
 * name: net.zhiyuan51.dev.android.baseRlvAdapter
 */
public abstract class MoreBaseViewHolder<T> extends RecyclerView.ViewHolder {
    public MoreBaseViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T model,int position);
}
