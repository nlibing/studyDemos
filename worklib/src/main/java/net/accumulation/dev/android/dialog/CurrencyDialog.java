package net.accumulation.dev.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import net.accumulation.dev.android.worklib.R;

/**
 * Created by Administrator on 2018/9/17.
 * user: Administrator
 * date: 2018/9/17
 * time; 11:39
 * name: 弹窗
 */
public class CurrencyDialog extends Dialog {
    CurrencyDialogListenerInterface currencyDialogListener;
    Context context;
    String title_text;
    String content_text;
    String right_text;
    String left_text;
    TextView tv_title;
    TextView tv_content;
    TextView tv_left;
    TextView tv_rigth;
    int layout = R.layout.currency_dialog;

    public CurrencyDialog(@NonNull Context context) {
        super(context);
        init();
    }

    //使用默认的弹窗
    public CurrencyDialog(@NonNull Context context, String title_text, String content_text, String right_text, String left_text) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.title_text = title_text;
        this.content_text = content_text;
        this.right_text = right_text;
        this.left_text = left_text;
        init();
    }

    public CurrencyDialog(@NonNull Context context, int layout, String title_text, String content_text, String right_text, String left_text) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.title_text = title_text;
        this.content_text = content_text;
        this.right_text = right_text;
        this.left_text = left_text;
        this.layout = layout;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layout, null);
        setContentView(view);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        tv_left = view.findViewById(R.id.tv_left);
        tv_rigth = view.findViewById(R.id.tv_rigth);

        tv_title.setText(title_text);
        tv_content.setText(content_text);
        tv_rigth.setText(right_text);
        tv_left.setText(left_text);

        tv_left.setOnClickListener(new clickListener());
        tv_rigth.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

    }

    public void setTitleColor(int titleColor) {
        tv_title.setTextColor(titleColor);
    }

    public void setContextColor(int contextColor) {
        tv_content.setTextColor(contextColor);
    }

    public void setLeftTextColor(int leftTextColor) {
        tv_left.setTextColor(leftTextColor);
    }

    public void setRightTextColor(int rightTextColor) {
        tv_rigth.setTextColor(rightTextColor);
    }

    public interface CurrencyDialogListenerInterface {
        void clickRigth();

        void clickLeft();
    }

    public void setClicklistener(CurrencyDialogListenerInterface currencyDialogListener) {
        this.currencyDialogListener = currencyDialogListener;
    }

    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tv_left) {
                currencyDialogListener.clickLeft();
            } else if (i == R.id.tv_rigth) {
                currencyDialogListener.clickRigth();
            }
        }
    }
}
