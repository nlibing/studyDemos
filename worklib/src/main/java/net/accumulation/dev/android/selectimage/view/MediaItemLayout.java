package net.accumulation.dev.android.selectimage.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import net.accumulation.dev.android.selectimage.helper.WindowManagerHelper;
import net.accumulation.dev.android.worklib.R;

/**
 * Created by Administrator on 2018/10/25.
 * user: Administrator
 * date: 2018/10/25
 * time; 15:23
 * name: net.zhiyuan51.dev.android.getphoneimage.view
 */
public class MediaItemLayout extends LinearLayout {
    private ImageView mCheckImg;
    private ImageView mCoverImg;
    private View mFontLayout;

    public MediaItemLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public MediaItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MediaItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_media_item, this, true);
        mCoverImg = view.findViewById(R.id.item);
        mCheckImg = view.findViewById(R.id.item_check);
        mFontLayout = view.findViewById(R.id.gary_layout);
        setImageRect(context);
    }

    private void setImageRect(Context context) {
        int screenHeight = WindowManagerHelper.getScreenHeight(context);
        int screenWidth = WindowManagerHelper.getScreenWidth(context);
        int width = 100;
        if (screenHeight != 0 && screenWidth != 0) {
            width = (screenWidth - getResources().getDimensionPixelOffset(R.dimen.boxing_media_margin) * 4) / 4;
        }
        mCoverImg.getLayoutParams().width = width;
        mCoverImg.getLayoutParams().height = width;
        mFontLayout.getLayoutParams().width = width;
        mFontLayout.getLayoutParams().height = width;
    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            mFontLayout.setVisibility(View.VISIBLE);
            mCheckImg.setBackgroundResource(R.mipmap.select_xz);
        } else {
            mFontLayout.setVisibility(View.GONE);
            mCheckImg.setBackgroundResource(R.mipmap.no_xz);
        }
    }

    public void setCover(String path) {
        Glide.with(mCoverImg.getContext()).load(path).
                placeholder(R.drawable.ic_boxing_default_image)
                .crossFade().centerCrop().override(150, 150).
                into(mCoverImg);
    }
}
