package com.wghcwc.everyshow;

import android.app.Application;

import com.wghcwc.activitylifecycle2.ActivityLifecycle;
/**
 * @author wghcwc
 * @date 20-1-6
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycle.init(this);
//        LoadingHelper.setStyle(new MyLoadingStyle());
    }


}
