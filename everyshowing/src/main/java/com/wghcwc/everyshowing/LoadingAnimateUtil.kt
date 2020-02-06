package com.wghcwc.everyshowing

import android.view.Gravity

/**
 * @author wghcwc
 */
object LoadingAnimateUtil {
    private const val INVALID = -1
    @JvmStatic
    fun getAnimationResource(gravity: Int, isInAnimation: Boolean): Int {
        when (gravity) {
            Gravity.TOP -> return if (isInAnimation) R.anim.svslide_in_top else R.anim.svslide_out_top
            Gravity.BOTTOM -> return if (isInAnimation) R.anim.svslide_in_bottom else R.anim.svslide_out_bottom
            Gravity.CENTER -> return if (isInAnimation) R.anim.svfade_in_center else R.anim.svfade_out_center
            else -> {
            }
        }
        return INVALID
    }
}