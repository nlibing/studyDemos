package net.accumulation.dev.android.selectimage.helper;

/**
 * Created by Administrator on 2018/11/14.
 * user: Administrator
 * date: 2018/11/14
 * time; 11:52
 * name: net.zhiyuan51.dev.android.selectimage.helper
 */
public interface ImageLoadCallBack {
    /**
     * Successfully handle a task;
     */
    void onSuccess();

    /**
     * Error happened when running a task;
     */
    void onFail(Throwable t);
}
