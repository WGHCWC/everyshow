package com.wghcwc.everyshowing;

import android.util.TypedValue;

import com.wghcwc.activitylifecycle2.ActivityLifecycle;

/**
 * @author wghcwc
 * @date 20-1-5
 */
public class ScreenUtils {

    public static int dp2px(float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, ActivityLifecycle.getApp().getResources().getDisplayMetrics());
    }
    /**
     * sp转px
     */
    public static int sp2px(float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, ActivityLifecycle.getApp().getResources().getDisplayMetrics());
    }
}
