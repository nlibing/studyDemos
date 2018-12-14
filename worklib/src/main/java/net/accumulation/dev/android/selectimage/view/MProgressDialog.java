package net.accumulation.dev.android.selectimage.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.accumulation.dev.android.worklib.R;


/**
 * Created by lb on 2018/3/17.
 */

public class MProgressDialog extends Dialog {
    private Context context=null;
    private  MProgressDialog progressDialog = null;
    public MProgressDialog(Context context) {
        super(context);
        this.context=context;
    }

    public MProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public  MProgressDialog createLoadingDialog( String msg) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progressdialog, null);
        LinearLayout layout =  v.findViewById(R.id.dialog_view);
        ImageView spaceshipImage =  v.findViewById(R.id.img);
        TextView tipTextView =  v.findViewById(R.id.tipTextView);
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.progressdialog_anim);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);

        progressDialog = new MProgressDialog(context, R.style.myprogressdialog);// 创建自定义样式dialog

        progressDialog.setCanceledOnTouchOutside(false);
        //progressDialog.setCancelable(false);// 不可以用“返回键”取消
        progressDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        return progressDialog;

    }
}
