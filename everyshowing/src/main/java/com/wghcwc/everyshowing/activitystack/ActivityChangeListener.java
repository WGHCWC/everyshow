package com.wghcwc.everyshowing.activitystack;

import android.app.Activity;

/**
 * @author wghcwc
 * @date 19-12-5
 */
public interface ActivityChangeListener {


    /**
     * 当前Activity Destroy
     *
     * @param activity 当前Activity
     */
    void onActivityDestroy(Activity activity);

    /**
     * 当前Activity 创建
     *
     * @param activity
     */
    void onActivityCreate(Activity activity);

    void onActivitySateChange(Activity activity, ActivityState state);

}
