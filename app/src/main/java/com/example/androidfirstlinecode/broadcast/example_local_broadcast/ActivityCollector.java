package com.example.androidfirstlinecode.broadcast.example_local_broadcast;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    static void addActivity(Activity activity){
        activities.add(activity);
    }

    static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
