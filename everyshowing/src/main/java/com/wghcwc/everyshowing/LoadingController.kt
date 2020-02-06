package com.wghcwc.everyshowing

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.*
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import com.wghcwc.everyshowing.LoadingAnimateUtil.getAnimationResource
import com.wghcwc.everyshowing.listener.OnDismissListener
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle
import com.wghcwc.everyshowing.loadstyle.LoadingMaskType
import com.wghcwc.everyshowing.loadstyle.LoadingStyle
import com.wghcwc.everyshowing.view.LoadingDefaultView
import com.wghcwc.everyshowing.view.SVCircleProgressBar
import java.lang.ref.WeakReference

/**
 * @author wghcwc
 */
open class LoadingController @JvmOverloads constructor(context: Context, private val style: LoadingStyle = BaseLoadingStyle()) {
    private val contextWeak: WeakReference<Context> = WeakReference(context)
    private var isShowing = false
    private var isDismissing = false
    private val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    )
    private lateinit var rootView: ViewGroup
    private lateinit var mSharedView: LoadingDefaultView
    private lateinit var decorView: ViewGroup
    private var outAnim: Animation? = null
    private var inAnim: Animation? = null
    private val gravity: Int = style.gravity
    private var onDismissListener: OnDismissListener? = null

    init {
        initViews()
        initDefaultView()
        initAnimation()
    }

    private fun initViews() {
        val context = contextWeak.get() ?: return
        decorView = (context as Activity).window.decorView.findViewById(android.R.id.content)
        rootView = View.inflate(context, R.layout.layout_svprogresshud, null) as ViewGroup
        rootView.layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    private fun initDefaultView() {
        val context = contextWeak.get() ?: return
        mSharedView = LoadingDefaultView(context, style)
        params.gravity = gravity
        mSharedView.layoutParams = params
    }

    private fun initAnimation() {
        inAnim = inAnim ?: inAnimation
        outAnim = outAnim ?: outAnimation
    }

    /**
     * show的时候调用
     */
    private fun onAttached() {
        isShowing = true
        decorView.addView(rootView)
        if (mSharedView.parent != null) (mSharedView.parent as ViewGroup).removeView(mSharedView)
        rootView.addView(mSharedView)
    }

    /**
     * 添加这个View到Activity的根视图
     */
    private fun svShow() {
        mHandler.removeCallbacksAndMessages(null)
        onAttached()
        mSharedView.startAnimation(inAnim)
    }


    fun show(maskType: LoadingMaskType = style.maskType) {
        if (isShowing()) return
        //判断maskType
        setMaskType(maskType)
        mSharedView.show()
        svShow()
    }


    fun showWithStatus(string: String, maskType: LoadingMaskType = style.maskType) {
        if (isShowing()) return
        setMaskType(maskType)
        mSharedView.showWithStatus(string)
        svShow()
    }


    fun showInfoWithStatus(string: String?, maskType: LoadingMaskType = style.maskType) {
        if (isShowing()) return
        setMaskType(maskType)
        mSharedView.showInfoWithStatus(string)
        svShow()
        scheduleDismiss()
    }


    fun showSuccessWithStatus(string: String?, maskType: LoadingMaskType = style.maskType) {
        if (isShowing()) return
        setMaskType(maskType)
        mSharedView.showSuccessWithStatus(string)
        svShow()
        scheduleDismiss()
    }


    fun showErrorWithStatus(string: String?, maskType: LoadingMaskType = style.maskType) {
        if (isShowing()) return
        setMaskType(maskType)
        mSharedView.showErrorWithStatus(string)
        svShow()
        scheduleDismiss()
    }

    fun showWithProgress(string: String?, maskType: LoadingMaskType?) {
        if (isShowing()) return
        setMaskType(maskType)
        mSharedView.showWithProgress(string)
        svShow()
    }

    val progressBar: SVCircleProgressBar?
        get() = mSharedView.circleProgressBar

    fun setText(string: String?) {
        mSharedView.setText(string)
    }

    private fun setMaskType(maskType: LoadingMaskType?) {
        when (maskType) {
            LoadingMaskType.Clear -> configMaskType(android.R.color.transparent, clickable = false, cancelable = true)
            LoadingMaskType.ClearCancel -> configMaskType(android.R.color.transparent, cancelable = true)
            LoadingMaskType.Black -> configMaskType(R.color.bgColor_overlay)
            LoadingMaskType.BlackCancel -> configMaskType(R.color.bgColor_overlay, cancelable = true)
            LoadingMaskType.Gradient -> configMaskType(R.drawable.bg_overlay_gradient)
            LoadingMaskType.GradientCancel -> configMaskType(R.drawable.bg_overlay_gradient, cancelable = true)
            LoadingMaskType.None -> configMaskType(android.R.color.transparent, cancelable = false)
        }
    }

    private fun configMaskType(bg: Int, clickable: Boolean = true, cancelable: Boolean = false) {
        rootView.setBackgroundResource(bg)
        rootView.isClickable = clickable
        setCancelable(cancelable)
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    fun isShowing(): Boolean {
        return rootView.parent != null || isShowing
    }

    fun dismiss() {
        if (isDismissing || !isShowing()) return
        isDismissing = true
        //消失动画
        outAnim!!.setAnimationListener(outAnimListener)
        mSharedView.dismiss()
        mSharedView.startAnimation(outAnim)
    }

    fun dismissImmediately() {
        mSharedView.dismiss()
        rootView.removeView(mSharedView)
        decorView.removeView(rootView)
        isShowing = false
        isDismissing = false
        if (onDismissListener != null) {
            onDismissListener!!.onDismiss(mSharedView)
        }
    }

    private val inAnimation: Animation?
        get() = contextWeak.get()?.let {
            val res = getAnimationResource(gravity, true)
            AnimationUtils.loadAnimation(it, res)
        }


    private val outAnimation: Animation?
        get() = contextWeak.get()?.let {
            val res = getAnimationResource(gravity, false)
            AnimationUtils.loadAnimation(it, res)
        }


    private fun setCancelable(isCancelable: Boolean) {
        val view = rootView.findViewById<View>(R.id.sv_outmost_container)
        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener)
        } else {
            view.setOnTouchListener(null)
        }
    }

    private val mHandler: Handler = InnerHandler(this)

    private class InnerHandler(loadingController: LoadingController) : Handler(Looper.getMainLooper()) {
        private val mWeakReference: WeakReference<LoadingController> = WeakReference(loadingController)
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                DISMISS -> mWeakReference.get()?.dismiss()
                DISMISS_IMMEDIATELY -> mWeakReference.get()?.dismissImmediately()
                else -> {
                }
            }
        }

    }

    private fun scheduleDismiss() {
        mHandler.removeCallbacksAndMessages(null)
        mHandler.sendEmptyMessageDelayed(DISMISS, DISMISS_DELAYED)
    }

    private val onCancelableTouchListener = OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            dismiss()
            setCancelable(false)
        }
        false
    }
    private val outAnimListener: AnimationListener = object : AnimationListener {
        override fun onAnimationStart(animation: Animation) {}
        override fun onAnimationEnd(animation: Animation) {
            mSharedView.post { dismissImmediately() }
        }

        override fun onAnimationRepeat(animation: Animation) {}
    }

    companion object {
        private const val DISMISS_DELAYED: Long = 1000
        private const val DISMISS_IMMEDIATELY = 1
        private const val DISMISS = 0
    }


}