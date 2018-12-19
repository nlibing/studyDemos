package net.accumulation.dev.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Dimension;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import net.accumulation.dev.android.utils.DisplayUtils;

import static android.support.annotation.Dimension.DP;

/**
 * Created by mc on 17/9/17.
 * 模板dialog
 */

public class BaseDialog extends Dialog {
    //示例用法
    /*         BaseDialog baseDialog = new BaseDialog.Build(mContext, R.style.ActionSheetDialogStyle)
              .contentViewId(R.layout.base_dialog)
                  .widthPercent(0.8f)
                  .isCancelable(true)
                  .create();*/
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        findViewById(viewId).setOnClickListener(onClickListener);
    }

    public static class Build {
        private boolean isCancelable;   //是否点击其他区域可取消
        private boolean isBottom;       //是否在手机底部
        private float widthPercent;     //宽度是手机宽度的百分之多少
        private float heightPercent;    //高度是手机高度的百分之多少
        private Context context;
        private int themeResId;         //dialog的风格ID
        private int contentViewId;      //contentView的ID
        private int bottomDistance;     //如果是在底部的，距离底部的dp
        private BaseDialog dialog;
        private Window window;
        private WindowManager.LayoutParams attributes;

        public Build(Context context, @StyleRes int themeResId) {
            this.context = context;
            this.themeResId = themeResId;
            dialog = new BaseDialog(context, themeResId);
            window = dialog.getWindow();
            attributes = window.getAttributes();
        }

        public Build isCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            dialog.setCancelable(isCancelable);
            return this;
        }

        public Build contentViewId(@LayoutRes int contentViewId) {
            this.contentViewId = contentViewId;
            window.setContentView(contentViewId);
            return this;
        }

        public Build isBottom(boolean isBottom) {
            this.isBottom = isBottom;
            window.setGravity(Gravity.BOTTOM);
            return this;
        }

        public Build bottomDistance(@Dimension(unit = DP) int bottomDistance) {
            this.bottomDistance = bottomDistance;
            attributes.y = DisplayUtils.dp2px( bottomDistance);
            return this;
        }

        public Build widthPercent(@FloatRange(from = 0.0, to = 1.0) float widthPercent) {
            this.widthPercent = widthPercent;
            attributes.width = (int) (DisplayUtils.getPhoneWidth(context) * widthPercent);
            return this;
        }

        public Build heightPercent(@FloatRange(from = 0.0, to = 1.0) float heightPercent) {
            this.heightPercent = heightPercent;
            attributes.height = (int) (DisplayUtils.getPhoneHeight(context) * heightPercent);
            return this;


        }

        public BaseDialog create() {
            window.setAttributes(attributes);
            return dialog;
        }
    }
}
