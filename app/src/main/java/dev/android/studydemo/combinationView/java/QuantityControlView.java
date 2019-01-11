package dev.android.studydemo.combinationView.java;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import dev.android.studydemo.R;


/**
 * Created by Administrator on 2019/1/7.
 * user: Administrator
 * date: 2019/1/7
 * time; 17:12
 * name: 数量控制器
 */
public class QuantityControlView extends LinearLayout {
    ImageView remove;
    ImageView add;
    TextView tv_number;
    Context mContext;
    QuantityOnClickListener quantityOnClickListener;
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    int number = 0;

    public void setQuantityOnClickListener(QuantityOnClickListener quantityOnClickListener) {
        this.quantityOnClickListener = quantityOnClickListener;
    }



    public QuantityControlView(Context context) {
        super(context);
    }

    public QuantityControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        addView(View.inflate(context, R.layout.quantity_control_view, null));
        remove = findViewById(R.id.iv_remove);
        add = findViewById(R.id.iv_add);
        tv_number = findViewById(R.id.tv_number);
        remove.setOnClickListener(new onQuantityClick());
        add.setOnClickListener(new onQuantityClick());
        tv_number.setText(getNumber() + "");
    }

    public void addIncrease() {
        number++;
        tv_number.setText(number + "");
    }

    public void reduce() {
        if (number == 0) {
            Toast.makeText(mContext, "最小值为1", Toast.LENGTH_SHORT).show();
        } else {
            number--;
            tv_number.setText(number + "");
        }
    }

    public interface QuantityOnClickListener {
        void addNumber();

        void removeNumber();
    }

    public class onQuantityClick implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_remove:
                    quantityOnClickListener.removeNumber();
                    break;
                case R.id.iv_add:
                    quantityOnClickListener.addNumber();
                    break;
            }
        }
    }
}
