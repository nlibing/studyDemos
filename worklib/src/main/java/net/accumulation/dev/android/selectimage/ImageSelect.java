package net.accumulation.dev.android.selectimage;

import android.app.Activity;
import android.support.annotation.NonNull;

import net.accumulation.dev.android.selectimage.activity.ChoiceImageActivity;

/**
 * Created by Administrator on 2018/11/1.
 * user: Administrator
 * date: 2018/11/1
 * time; 15:08
 * name: net.zhiyuan51.dev.android
 */
public class ImageSelect {
    public ImageSelect(){

    }
    public static void show(@NonNull Activity activity, int resquestCode, int maxCount) {
        ChoiceImageActivity.show(activity, resquestCode, maxCount);
    }
}
