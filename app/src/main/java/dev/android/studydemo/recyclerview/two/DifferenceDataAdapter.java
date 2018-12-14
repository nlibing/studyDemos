package dev.android.studydemo.recyclerview.two;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import net.accumulation.dev.android.baseRlvAdapter.BaseViewHolder;

import java.util.List;

import dev.android.studydemo.R;

/**
 * Created by Administrator on 2018/11/22.
 * user: Administrator
 * date: 2018/11/22
 * time; 14:03
 * name: 不同item布局显示
 */
public abstract class DifferenceDataAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private final int MORE_ENTRY_ONE = 3;//第一种条目
    private final int MORE_ENTRY_TWO = 4;//第一种条目
    private final int MORE_ENTRY_THREE = 5;//第一种条目
    private Context context;//上下文
    public List<DifferenceBean> list;//数据源
    private LayoutInflater inflater;//布局器
    private OnItemClickListener listener;//点击事件监听器
    private RecyclerView recyclerView;

    public DifferenceDataAdapter(Context context, List<DifferenceBean> differenceBeans) {
        this.context = context;
        this.list = differenceBeans;
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
                    if ((getItemViewType(position) == MORE_ENTRY_ONE)) {
                        return 6;
                    } else if((getItemViewType(position) == MORE_ENTRY_TWO)){
                        return 3;
                    }else {
                        return 2;
                    }
                    //这里其实有一个不算问题的问题
                    //例如现在用的第二种布局，而恰巧你的第二个数据不是整数，就是说，假如你现在第二集合数据只有4条数据
                    //这种情况只能显示完整一行，第二行只能显示3分之一，恰巧你的第三种布局数据的item只占2分之1
                    //现在的情况就是，第一行显示完整(第二集合数据)，第二行（第二集合数据和第三集合数据）混合了起来
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
    public void setData(List<DifferenceBean> data) {
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
    public void addAll(List<DifferenceBean> data) {
        this.list.addAll(data);
        notifyDataSetChanged();
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
            case MORE_ENTRY_ONE:
                view = inflater.inflate(R.layout.item_type_one, parent, false);
                baseViewHolder = new BaseViewHolder(view, context);
                break;
            case MORE_ENTRY_TWO:
                view = inflater.inflate(R.layout.item_type_two, parent, false);
                baseViewHolder = new BaseViewHolder(view, context);
                break;
            case MORE_ENTRY_THREE:
                view = inflater.inflate(R.layout.item_type_three, parent, false);
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
        if(list.get(position).getType()==1){
            return MORE_ENTRY_ONE;
        }else if(list.get(position).getType()==2){
            return MORE_ENTRY_TWO;
        }else {
            return MORE_ENTRY_THREE;
        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 1;
        } else {
            return list.size() ;
        }
    }



    public abstract void convert(BaseViewHolder holder, DifferenceBean item, int position);

}
