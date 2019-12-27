package com.wghcwc.everyshow.activitystack;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;

/**
 * @author wghcwc
 * @date 19-12-5
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {
    public static ActivityLifecycle instance;

    static {
        instance = new ActivityLifecycle();
    }

    private ActivityLifecycle() {
        changeListeners = new LinkedList<>();
    }

    public static ActivityLifecycle getInstance() {
        return instance;
    }

    private final LinkedList<ActivityChangeListener> changeListeners;

    public void add(ActivityChangeListener listener) {
        changeListeners.add(listener);
    }

    public void remove(ActivityChangeListener listener) {
        changeListeners.remove(listener);
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        ActivityStack.pushActivity(activity);
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivityCreate(activity);
            changeListener.onActivitySateChange(activity, ActivityState.CREATED);
        }

    }

    @Override
    public void onActivityStarted(Activity activity) {
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivitySateChange(activity, ActivityState.STARTED);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivitySateChange(activity, ActivityState.RESUMED);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivitySateChange(activity, ActivityState.PAUSED);
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivitySateChange(activity, ActivityState.STOPPED);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityStack.removeActivity(activity);
        for (ActivityChangeListener changeListener : changeListeners) {
            changeListener.onActivityDestroy(activity);
            changeListener.onActivitySateChange(activity, ActivityState.DESTROYED);
        }
    }
}
