package net.accumulation.dev.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import net.accumulation.dev.android.worklib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/12.
 * user: Administrator
 * date: 2018/11/12
 * time; 10:42
 * name: 底部列表弹窗菜单
 */
public class BottomMenuView extends Dialog {
    Context mContext;
    ListView lv_menus;
    TextView tv_cancel;
    LinearLayout ll_layout;
    List<String> list = new ArrayList<>();
    MeunsAdapter adapter;


    DialogListenerInterface dialogListenerInterface;

    public BottomMenuView(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BottomMenuView(@NonNull Context context, List<String> list) {
        super(context, R.style.ActionSheetDialogStyle);
        this.mContext = context;
        this.list=list;
        init();
    }

    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.menu_dialog, null);
        lv_menus = view.findViewById(R.id.lv_menus);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        ll_layout=view.findViewById(R.id.ll_layout);
        setContentView(view);
        adapter = new MeunsAdapter(mContext, list);
        lv_menus.setAdapter(adapter);
        lv_menus.setOnItemClickListener(new ItemButton());
        tv_cancel.setOnClickListener(new ButtonClick());
        ll_layout.setOnClickListener(new ButtonClick());
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.height = (int) (d.heightPixels * 0.6);
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
        dialogWindow.setGravity(Gravity.BOTTOM);
    }

    public interface DialogListenerInterface {
        void itemButton(int position);

    }

    public void setDialogListenerInterface(DialogListenerInterface dialogListenerInterface) {
        this.dialogListenerInterface = dialogListenerInterface;
    }

    class ButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }

    class ItemButton implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            dialogListenerInterface.itemButton(position);
        }
    }

    class MeunsAdapter extends BaseAdapter {
        List<String> meuns;
        Context mContext;
        private LayoutInflater mInflater;

        public MeunsAdapter(Context mContext, List<String> meuns) {
            this.mContext = mContext;
            this.meuns = meuns;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return meuns.size();
        }

        @Override
        public Object getItem(int position) {
            return meuns.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_listview, null);
            TextView textView = view.findViewById(R.id.tv_title);
            if (position == 0) {
                textView.setBackgroundResource(R.drawable.top_round);
            } else if (position == meuns.size() - 1) {
                textView.setBackgroundResource(R.drawable.bottom_round);
            } else {
                textView.setBackgroundResource(R.drawable.center_round);
            }
            textView.setText(meuns.get(position));
            return view;
        }

    }


}
