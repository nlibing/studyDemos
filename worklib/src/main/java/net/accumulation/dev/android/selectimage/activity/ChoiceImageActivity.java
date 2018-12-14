package net.accumulation.dev.android.selectimage.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import net.accumulation.dev.android.selectimage.ImageApdater;
import net.accumulation.dev.android.selectimage.ImageBean;
import net.accumulation.dev.android.selectimage.helper.ImageHelper;
import net.accumulation.dev.android.selectimage.view.MProgressDialog;
import net.accumulation.dev.android.selectimage.view.MediaItemLayout;
import net.accumulation.dev.android.worklib.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by Administrator on 2018/10/30.
 * user: Administrator
 * date: 2018/10/30
 * time; 13:37
 * name: 图片选择
 */
public class ChoiceImageActivity extends AppCompatActivity {
    RecyclerView rlv_image;
    TextView tv_select_num;
    TextView tv_select_preview;//预览按钮
    protected MProgressDialog progressDialog;
    ImageHelper imageHelper;
    List<ImageBean> imageBeans = new ArrayList<>();
    ImageApdater imageApdater;
    static int mMaxCount = 6;//图片选择数量限制
    public final static int GET_PREVIEW = 2;//预览图片
    private static final String EXTRA_KEY_MAX = "max";
    public static final int JURISDICTION = 1;//权限
    private static int resultMainCode;

    public static void show(Activity activity, int requestCode, int maxCount) {
        Intent i = new Intent(activity, ChoiceImageActivity.class);
        i.putExtra(EXTRA_KEY_MAX, maxCount);
        resultMainCode = requestCode;
        activity.startActivityForResult(i, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_image);
        rlv_image = findViewById(R.id.rlv_image);
        tv_select_num = findViewById(R.id.tv_select_num);
        tv_select_preview = findViewById(R.id.tv_select_preview);
        rlv_image.setHasFixedSize(true);
        rlv_image.setLayoutManager(new GridLayoutManager(this, 4));
        rlv_image.setNestedScrollingEnabled(false);
        imageApdater = new ImageApdater(this, imageBeans);
        rlv_image.setAdapter(imageApdater);
        mMaxCount = getIntent().getIntExtra(EXTRA_KEY_MAX, 6);
        PermissionGen.with(ChoiceImageActivity.this)
                .addRequestCode(JURISDICTION)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.LOCATION_HARDWARE).request();
        imageHelper = ImageHelper.getGetImageHelperInstance();
        showDialog();
        new LoadImage().execute();
        imageApdater.setOnCheckedListener(new OnMediaCheckedListener());
        imageApdater.setOnItemClickListener(new ImageApdater.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                //item点击事件
                Intent intent = new Intent();
                intent.setClass(ChoiceImageActivity.this, PreviewSelectImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("select_lits", (ArrayList<? extends Parcelable>) imageApdater.getSelectedMedias());
                bundle.putInt("number", postion);
                intent.putExtras(bundle);
                startActivityForResult(intent, GET_PREVIEW);
            }
        });
        tv_select_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageApdater.getSelectedMedias().size() > 0) {
                    //查看选中预览
                    Intent intent = new Intent();
                    intent.setClass(ChoiceImageActivity.this, PreviewSelectImageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("select_lits", (ArrayList<? extends Parcelable>) imageApdater.getSelectedMedias());
                    bundle.putInt("number", 0);
                    bundle.putInt("type", 0);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, GET_PREVIEW);
                }
            }
        });
        tv_select_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageApdater.getSelectedMedias().size() > 0) {
                    Intent data = new Intent();
                    data.putParcelableArrayListExtra("select_image_list", (ArrayList<? extends Parcelable>) imageApdater.getSelectedMedias());
                    setResult(resultMainCode, data);
                    finish();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = JURISDICTION)
    public void requestPhotoSuccess() {
        //成功之后的处理
    }

    @PermissionFail(requestCode = JURISDICTION)
    public void requestPhotoFail() {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_PREVIEW && resultCode == GET_PREVIEW) {
            if (data.getIntExtra("type", 0) == 1) {
                List<ImageBean> select_list = data.getParcelableArrayListExtra("back_select_list");
                if (select_list.size() > 0) {
                    tv_select_num.setText("完成(" + select_list.size() + "/" + mMaxCount + ")");
                } else {
                    tv_select_num.setText("完成");
                }
                checkSelectedMedia(imageBeans, select_list);
                imageApdater.setSelectedMedias(select_list);
            } else {
                List<ImageBean> select_list = data.getParcelableArrayListExtra("select_image_list");
                Intent back_data = new Intent();
                back_data.putParcelableArrayListExtra("select_image_list", (ArrayList<? extends Parcelable>) select_list);
                setResult(resultMainCode, back_data);
                finish();
            }

        }
    }

    public void checkSelectedMedia(List<ImageBean> allMedias, List<ImageBean> selectedMedias) {
        if (allMedias == null || allMedias.size() == 0) {
            return;
        }
        Map<String, ImageBean> map = new HashMap<>(allMedias.size());
        for (ImageBean allMedia : allMedias) {
            if (!(allMedia instanceof ImageBean)) {
                return;
            }
            ImageBean media = (ImageBean) allMedia;
            media.setSelect(false);
            map.put(media.getImage_path(), media);
        }
        if (selectedMedias == null || selectedMedias.size() < 0) {
            return;
        }
        for (ImageBean media : selectedMedias) {
            if (map.containsKey(media.getImage_path())) {
                map.get(media.getImage_path()).setSelect(true);
            }
        }
    }

    //从手机中查询图片
    class LoadImage extends AsyncTask<Void, Integer, Boolean> {
        List<ImageBean> imageBeanList;

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissDialog();
            if (aBoolean) {
                imageApdater.AddImage(imageBeanList);
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            imageHelper.getThumbnail(ChoiceImageActivity.this);
            imageBeanList = imageHelper.getRealAddress();
            return true;

        }
    }

    /**
     * 弹出提示框
     */
    public MProgressDialog showDialog() {
        if (progressDialog == null) {
            progressDialog = new MProgressDialog(ChoiceImageActivity.this);
            progressDialog = progressDialog.createLoadingDialog("请稍等");
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
        return progressDialog;
    }

    /**
     * 关闭提示框
     */
    public void dismissDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        }

    }

    //item选择
    public class OnMediaCheckedListener implements ImageApdater.OnCheckedListener {


        @Override
        public void onChecked(View view, ImageBean iMedia) {
            ImageBean imageBean = iMedia;
            boolean isSelected = !imageBean.isSelect();
            List<ImageBean> selectedMedias = imageApdater.getSelectedMedias();
            MediaItemLayout layout = (MediaItemLayout) view;
            if (isSelected) {
                if (selectedMedias.size() >= mMaxCount) {
                    String warning = getString(R.string.boxing_too_many_picture_fmt, mMaxCount);
                    Toast.makeText(ChoiceImageActivity.this, warning, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!selectedMedias.contains(imageBean)) {
                    selectedMedias.add(imageBean);
                }
            } else {
                if (selectedMedias.size() >= 1 && selectedMedias.contains(imageBean)) {
                    selectedMedias.remove(imageBean);
                }
            }
            if (selectedMedias.size() > 0) {
                tv_select_num.setText("完成(" + selectedMedias.size() + "/" + mMaxCount + ")");
            } else {
                tv_select_num.setText("完成");
            }
            imageBean.setSelect(isSelected);
            layout.setChecked(isSelected);
        }
    }

}
