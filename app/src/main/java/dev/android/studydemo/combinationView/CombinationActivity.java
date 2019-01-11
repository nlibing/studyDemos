package dev.android.studydemo.combinationView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.android.studydemo.R;
import dev.android.studydemo.combinationView.java.QuantityControlView;
import dev.android.studydemo.combinationView.kt.KtQuantityControlView;

/**
 * 组合控件，数量加减控制
 */
public class CombinationActivity extends AppCompatActivity {
    QuantityControlView quantityControlView;
    KtQuantityControlView ktQuantityControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);
        quantityControlView = findViewById(R.id.qcv);
        ktQuantityControlView = findViewById(R.id.kt_qcv);
        quantityControlView.setQuantityOnClickListener(new QuantityControlView.QuantityOnClickListener() {
            @Override
            public void addNumber() {
                quantityControlView.addIncrease();
            }

            @Override
            public void removeNumber() {
                quantityControlView.reduce();
            }
        });
        ktQuantityControlView.setQuantityOnClickListener(new KtQuantityControlView.QuantityOnClickListener() {
            @Override
            public void addNumber() {
                ktQuantityControlView.addIncrease();
            }

            @Override
            public void removeNumber() {
                ktQuantityControlView.reduce();
            }
        });
    }
}
