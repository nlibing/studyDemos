package net.accumulation.dev.android.selectimage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.accumulation.dev.android.selectimage.view.MediaItemLayout;
import net.accumulation.dev.android.worklib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 * user: Administrator
 * date: 2018/10/30
 * time; 9:50
 * name: net.zhiyuan51.dev.android.getphoneimage
 */
public class ImageApdater extends RecyclerView.Adapter<ImageApdater.ViewHolder> {
    Context mContext;
    List<ImageBean> allImageBeans;
    private OnCheckedListener mOnCheckedListener;
    private OnCheckListener mOnCheckListener;

    private List<ImageBean> mSelectedMedias;
    private OnItemClickListener mClickListener;
    private String type="0";
    public ImageApdater(Context context, List<ImageBean> imageBeans) {
        this.mContext = context;
        this.allImageBeans = imageBeans;
        this.mOnCheckListener = new OnCheckListener();
        this.mSelectedMedias = new ArrayList<>();
    }
    public ImageApdater(Context context, List<ImageBean> imageBeans,String type) {
        this.mContext = context;
        this.allImageBeans = imageBeans;
        this.type=type;
        this.mOnCheckListener = new OnCheckListener();
        this.mSelectedMedias = new ArrayList<>();
    }
    public void AddImage(List<ImageBean> imageBeans) {
        if(this.allImageBeans.size()>0){
            this.allImageBeans.clear();
        }
        this.allImageBeans.addAll(imageBeans);
        notifyDataSetChanged();
    }


    public List<ImageBean> getSelectedMedias() {
        return mSelectedMedias;
    }

    public void setSelectedMedias(List<ImageBean> selectedMedias) {
        if (selectedMedias == null) {
            return;
        }
        mSelectedMedias.clear();
        mSelectedMedias.addAll(selectedMedias);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycleview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view,mClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String path;
        if (TextUtils.isEmpty(allImageBeans.get(position).getImage_thumbnails())) {
            path = "file://" + allImageBeans.get(position).getImage_path();
        } else {
            path = "file://" + allImageBeans.get(position).getImage_path();
        }
        if(type.equals("0")){
            holder.mItemChecked.setVisibility(View.VISIBLE);
        }else {
            holder.mItemChecked.setVisibility(View.GONE);
        }
        holder.mItemLayout.setTag(allImageBeans.get(position));
        holder.mItemLayout.setTag(R.id.item_check, position);
        holder.mItemLayout.setCover(path);
        holder.mItemLayout.setChecked(allImageBeans.get(position).isSelect);
        holder.mItemChecked.setTag(R.id.media_layout, holder.mItemLayout);
        holder.mItemChecked.setTag(allImageBeans.get(position));
        holder.mItemChecked.setOnClickListener(mOnCheckListener);
    }

    @Override
    public int getItemCount() {
        return allImageBeans.size();
    }

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        mOnCheckedListener = onCheckedListener;
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        MediaItemLayout mItemLayout;
        ImageView mItemChecked;
        private OnItemClickListener mListener;
        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            mItemLayout = itemView.findViewById(R.id.media_layout);
            mItemChecked = itemView.findViewById(R.id.item_check);
            mItemLayout.setOnClickListener(this);
            mListener=listener;
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v,getAdapterPosition());
        }
    }

    private class OnCheckListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            MediaItemLayout itemLayout = (MediaItemLayout) v.getTag(R.id.media_layout);
            ImageBean media = (ImageBean) v.getTag();
            if (mOnCheckedListener != null) {
                mOnCheckedListener.onChecked(itemLayout, media);
            }
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int postion);
    }


    public interface OnCheckedListener {
        //选中按钮
        void onChecked(View v, ImageBean iMedia);
    }
}
