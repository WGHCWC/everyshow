package com.wghcwc.everyshowing

import android.util.TypedValue
import com.wghcwc.activitylifecycle2.ActivityLifecycle

/**
 * @author wghcwc
 * @date 20-1-5
 */
object ScreenUtils {
    @JvmStatic
    fun dp2px(dpValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, ActivityLifecycle.getApp()?.resources?.displayMetrics).toInt()
    }

    /**
     * sp转px
     */
    @JvmStatic
    fun sp2px(spValue: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, ActivityLifecycle.getApp()?.resources?.displayMetrics).toInt()
    }
}