package com.wghcwc.everyshowing.loadstyle

import android.view.Gravity
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView.ScaleType
import com.wghcwc.everyshowing.R
import com.wghcwc.everyshowing.ScreenUtils

/**
 * @author wghcwc
 * @date 20-1-5
 */
open class BaseLoadingStyle : LoadingStyle {
    private var rotateAnimation: RotateAnimation? = null

    private fun initAnimation(): RotateAnimation {
        rotateAnimation = RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation?.duration = 1000L
        rotateAnimation?.interpolator = LinearInterpolator()
        rotateAnimation?.repeatCount = -1
        rotateAnimation?.repeatMode = Animation.RESTART
        return rotateAnimation!!
    }

    override val image: Int
        get() = R.drawable.ic_svstatus_loading


    override val background: Int
        get() = R.drawable.bg_svprogresshuddefault
    override val gravity: Int
        get() = Gravity.CENTER


    override val infoImage: Int
        get() = R.drawable.ic_svstatus_info


    override val successImage: Int
        get() = R.drawable.ic_svstatus_success


    override val errorImage: Int
        get() = R.drawable.ic_svstatus_error

    override val maskType: LoadingMaskType
        get() = LoadingMaskType.Black

    override val animation: Animation
        get() {
            return rotateAnimation ?: initAnimation()
        }
    override val scaleType: ScaleType
        get() = ScaleType.FIT_CENTER

}