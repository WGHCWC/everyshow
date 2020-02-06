package com.wghcwc.everyshowing.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.wghcwc.everyshowing.R
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle
import com.wghcwc.everyshowing.loadstyle.LoadingStyle
import kotlinx.android.synthetic.main.view_svprogressdefault.view.*

/**
 * @author wghcwc
 * @date 20-1-5
 */
class LoadingDefaultView @JvmOverloads constructor(context: Context?, private val mStyle: LoadingStyle = BaseLoadingStyle()) : LinearLayout(context) {
    val circleProgressBar: SVCircleProgressBar? = null

    private fun initViews() {
        LayoutInflater.from(context).inflate(R.layout.view_svprogressdefault, this, true)
        llParent.setBackgroundResource(mStyle.background)
        ivBigLoading.scaleType = mStyle.scaleType
        ivBigLoading.setPadding(mStyle.padding, mStyle.padding, mStyle.padding, mStyle.padding)
    }

    fun show() {
        clearAnimations()
        ivBigLoading.setImageResource(mStyle.image)
        ivBigLoading.visibility = View.VISIBLE
        ivSmallLoading.visibility = View.GONE
        circleProgressBar?.visibility = View.GONE
        tvMsg.visibility = View.GONE
        ivBigLoading.startAnimation(mStyle.animation)
    }

    fun showWithStatus(string: String?) {
        if (string == null) {
            show()
            return
        }
        showBaseStatus(mStyle.image, string)
        ivSmallLoading.startAnimation(mStyle.animation)
    }

    fun showInfoWithStatus(string: String?) {
        showBaseStatus(mStyle.infoImage, string)
    }

    fun showSuccessWithStatus(string: String?) {
        showBaseStatus(mStyle.successImage, string)
    }

    fun showErrorWithStatus(string: String?) {
        showBaseStatus(mStyle.errorImage, string)
    }

    fun showWithProgress(string: String?) {
        showProgress(string)
    }

    fun setText(string: String?) {
        tvMsg.text = string
    }

    fun showProgress(string: String?) {
        clearAnimations()
        tvMsg.text = string
        ivBigLoading.visibility = View.GONE
        ivSmallLoading.visibility = View.GONE
        circleProgressBar?.visibility = View.VISIBLE
        tvMsg!!.visibility = View.VISIBLE
    }

    fun showBaseStatus(res: Int, string: String?) {
        clearAnimations()
        ivSmallLoading.setImageResource(res)
        tvMsg.text = string
        ivBigLoading.visibility = View.GONE
        circleProgressBar?.visibility = View.GONE
        ivSmallLoading.visibility = View.VISIBLE
        tvMsg.visibility = View.VISIBLE
    }

    fun dismiss() {
        clearAnimations()
    }

    private fun clearAnimations() {
        ivBigLoading.clearAnimation()
        ivSmallLoading.clearAnimation()
    }

    init {
        initViews()
    }
}