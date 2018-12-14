package net.accumulation.dev.android.baseRlvAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/11/22.
 * user: Administrator
 * date: 2018/11/22
 * time; 13:44
 * name: viewholder基类
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {


    private SparseArray<View> views;
    private Context mContext;

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        views = new SparseArray<>();
    }

    public SparseArray<View> getViews() {
        return views;
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
        return this;
    }


}
