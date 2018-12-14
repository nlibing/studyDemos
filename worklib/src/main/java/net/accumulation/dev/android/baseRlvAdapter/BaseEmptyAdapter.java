package net.accumulation.dev.android.baseRlvAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import net.accumulation.dev.android.worklib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/22.
 * user: Administrator
 * date: 2018/11/22
 * time; 14:03
 * name: 带有空状态的BaseAdapter
 */
public abstract class BaseEmptyAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final int EMPTYSTATE = 0;//空状态布局
    private final int DEFAULTSTATE = 1;//默认布局
    private Context context;//上下文
    public List<T> list;//数据源
    private LayoutInflater inflater;//布局器
    private int itemLayoutId;//布局id
    private int layout_empty = R.layout.empty_state;
    private OnItemClickListener listener;//点击事件监听器
    private RecyclerView recyclerView;

    public BaseEmptyAdapter(Context context, int itemLayoutId) {
        this.context = context;
        this.list = new ArrayList<>();
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    /**
     * 添加数据
     *
     * @param data 初始数据
     */
    public void setData(List<T> data) {
        if (this.list.size() > 0) {
            this.list.clear();
        }
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加更多数据
     *
     * @param data
     */
    public void addAll(List<T> data) {
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 插入一条数据
     *
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一条数据
     *
     * @param position
     */
    public void delete(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置空状态布局
     * @param empty_layout
     */
    public void setEmptyLayout(int empty_layout) {
        layout_empty = empty_layout;
    }

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(RecyclerView parent, View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        BaseViewHolder baseViewHolder = null;
        switch (viewType) {
            case EMPTYSTATE:
                view = inflater.inflate(layout_empty, parent, false);
                baseViewHolder = new BaseViewHolder(view, context);
                break;
            case DEFAULTSTATE:
                view = inflater.inflate(itemLayoutId, parent, false);
                baseViewHolder = new BaseViewHolder(view, context);
                break;
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (list == null || list.size() == 0) {

        } else {
            if (listener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null && view != null && recyclerView != null) {
                            int position = recyclerView.getChildAdapterPosition(view);
                            listener.onItemClick(recyclerView, view, position);
                        }
                    }
                });
            }
            try {
                convert(holder, list.get(position), position);
            } catch (Exception e) {

            }

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list == null || list.size() == 0) {
            return EMPTYSTATE;
        } else {
            return DEFAULTSTATE;
        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 1;
        } else {
            return list.size();
        }
    }

    public abstract void convert(BaseViewHolder holder, T item, int position);
}
