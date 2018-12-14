package net.accumulation.dev.android.baseRlvAdapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
 * name: 加载更多
 */
public abstract class BaseLoadMoreDataAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final String TAG = "BaseLoadMoreDataAdapter";
    private final int EMPTYSTATE = 0;//空状态布局
    private final int DEFAULTSTATE = 1;//默认布局
    private final int FOOTERSTATE = 2;//底布局(加载更多)
    private Context context;//上下文
    public List<T> list;//数据源
    private LayoutInflater inflater;//布局器
    private int itemLayoutId;//默认item布局id
    private int layout_empty = R.layout.empty_state;//默认空状态布局id
    private int layout_load_more = R.layout.load_more;//默认加载更多布局
    private OnItemClickListener listener;//点击事件监听器
    private RecyclerView recyclerView;
    // 当前加载状态，默认为加载完成
    private int loadState = 2;
    // 正在加载
    public static final int LOADING = 1;
    // 加载完成
    public static final int LOADING_COMPLETE = 2;

    public BaseLoadMoreDataAdapter(Context context, int itemLayoutId) {
        this.context = context;
        this.list = new ArrayList<>();
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    if ((getItemViewType(position) == FOOTERSTATE) || (getItemViewType(position) == EMPTYSTATE)) {
                        return gridManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }

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
     *
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
            case FOOTERSTATE:
                view = inflater.inflate(layout_load_more, parent, false);
                baseViewHolder = new BaseViewHolder(view, context);
                break;

        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (list == null || list.size() == 0) {
        } else {
            if (position == list.size()) {
                loadData(holder, loadState);
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
    }

    @Override
    public int getItemViewType(int position) {
        if (list == null || list.size() == 0) {
            return EMPTYSTATE;
        } else if (list != null && list.size() > 0 && position == list.size()) {
            return FOOTERSTATE;
        } else {
            return DEFAULTSTATE;
        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 1;
        } else {
            //+1是为了加载更多布局
            return list.size() + 1;
        }
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 0.正在加载 1.加载完成 2.加载到底
     */
    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    public abstract void convert(BaseViewHolder holder, T item, int position);

    public abstract void loadData(BaseViewHolder holder, int loadState);
}
