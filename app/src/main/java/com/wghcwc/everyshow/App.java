package com.wghcwc.everyshow;

import android.app.Application;

import com.wghcwc.activitylifecycle2.ActivityLifecycle;
import com.wghcwc.everyshowing.LoadingHelper;

/**
 * @author wghcwc
 * @date 20-1-6
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//       registerActivityLifecycleCallbacks(ActivityLifecycle.INSTANCE);

        ActivityLifecycle.init(this);
        LoadingHelper.setStyle(new MyLoadingStyle());
    }


}
