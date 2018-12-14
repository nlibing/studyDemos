/*
 * The author is bullet
 */

package net.accumulation.dev.android.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by bullet on 2017/12/16.
 */

public class ActivityCollector {
    private static List<Activity> activities;

    public ActivityCollector() {

    }

    /**
     * 静态内部类实现单例
     */
    private static class CollectorHold{
        private static final ActivityCollector instance = new ActivityCollector();
    }

    public static ActivityCollector getAcitivityCollector(){


        return CollectorHold.instance;
    }


    /**
     * 添加activity
     * @param activity
     */

    public void addActivty(Activity activity){
        if (activities == null) {
            activities = new ArrayList<>();
        }
        activities.add(activity);
    }

    public void removeActivty(Activity activity){
        activities.remove(activity);
    }

    public void finishAll(){
        for (Activity activity : activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
