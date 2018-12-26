package dev.android.studydemo.view.doodling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import net.accumulation.dev.android.selectimage.ImageBean;
import net.accumulation.dev.android.selectimage.ImageSelect;

import java.util.List;

import dev.android.studydemo.R;

public class DoodlingActivity extends AppCompatActivity implements View.OnClickListener {
    private int resquestCode = 200;
    List<ImageBean> select_list;
    TextView tv_add_iamge;
    TextView tv_doodling;
    TextView tv_text;
    TextView tv_save;
    DrawView dlv;
    LinearLayout ll_view;
    LinearLayout ll_input;
    EditText ed_input;
    TextView tv_input_sure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodling);
        tv_add_iamge = findViewById(R.id.tv_add_iamge);
        tv_doodling = findViewById(R.id.tv_doodling);
        tv_text = findViewById(R.id.tv_text);
        tv_save = findViewById(R.id.tv_save);
        dlv = findViewById(R.id.dlv);
        ll_view = findViewById(R.id.ll_view);
        ll_input = findViewById(R.id.ll_input);
        ed_input = findViewById(R.id.ed_input);
        tv_input_sure = findViewById(R.id.tv_input_sure);
        tv_add_iamge.setOnClickListener(this);
        tv_doodling.setOnClickListener(this);
        tv_text.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        tv_input_sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_iamge:
                ImageSelect.show(DoodlingActivity.this, resquestCode, 1);
                break;
            case R.id.tv_doodling:
                dlv.replacePattern(DrawView.PATTERN_DOODLING);
                ll_input.setVisibility(View.GONE);
                break;
            case R.id.tv_text:
                dlv.replacePattern(DrawView.PATTERN_INPUT);
                ll_input.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_save:
                //saveBitmap(dlv);
                break;
            case R.id.tv_input_sure:
                if (!TextUtils.isEmpty(ed_input.getText().toString())) {
                    dlv.setContent(ed_input.getText().toString());
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resquestCode && resultCode == resquestCode) {
            select_list = data.getParcelableArrayListExtra("select_image_list");
            if (select_list != null && select_list.size() > 0) {
                tv_add_iamge.setVisibility(View.GONE);
                ll_view.setVisibility(View.VISIBLE);
                dlv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        // TODO Auto-generated method stub
                        dlv.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                        Glide.with(DoodlingActivity.this)
                                .load(select_list.get(0).getImage_path())
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                        int imageHeight = resource.getHeight();
                                        int imageWidth=resource.getWidth();
                                        float scaleWidth = ((float) dlv.getWidth())/imageWidth;
                                        float scaleHeight = ((float) dlv.getHeight())/imageHeight;
                                        // 取得想要缩放的matrix參數
                                        Matrix matrix = new Matrix();
                                        matrix.postScale(scaleWidth, scaleHeight);
                                        // 得到新的圖片
                                        Bitmap newbm = Bitmap.createBitmap(resource, 0, 0, imageWidth, imageHeight, matrix,true);
                                        dlv.setBitmap(newbm);
                                    }
                                });
                        Log.e("测试：", dlv.getMeasuredHeight()+","+dlv.getMeasuredWidth());
                    }
                });

            }

        }
    }
}
