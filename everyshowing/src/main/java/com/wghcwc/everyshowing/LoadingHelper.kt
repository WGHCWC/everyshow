package com.wghcwc.everyshowing

import android.app.Activity
import com.wghcwc.activitylifecycle2.ActivityChangeListener
import com.wghcwc.activitylifecycle2.ActivityLifecycle
import com.wghcwc.activitylifecycle2.ActivityStack
import com.wghcwc.activitylifecycle2.ActivityState
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle
import com.wghcwc.everyshowing.loadstyle.LoadingStyle
import java.util.*

/**
 * 使用前需要依赖
 *
 *
 * allprojects {
 * repositories {
 * ....
 * maven { url 'https://jitpack.io' }
 * }
 * }
 * implementation 'com.github.WGHCWC:activitylifecycle:1.0.4'
 *
 * [ActivityLifecycle]
 * 在 application 中初始化ActivityLifecycle.init(this);
 *
 * @author wghcwc
 * @date 19-11-26
 */
object LoadingHelper {
    private val wrapperMap: MutableMap<Activity, LoadWrapper> = WeakHashMap()
    private var mStyle: LoadingStyle = BaseLoadingStyle()
    /**
     * 设置全局样式
     */
    @JvmStatic
    fun setStyle(style: LoadingStyle) {
        mStyle = style
    }

    /**
     *   @return LoadingController 代理对象
     * */
    @JvmStatic
    fun get(): LoadingController? {
        return currentWrapper?.loadingParent
    }

    /**
     * 设置当前activity加载样式,仅当前LoadWrapper有效
     */
    @JvmStatic
    fun setCurStyle(style: LoadingStyle) :LoadingController? {
       return currentActivity?.let {
            wrapperMap.remove(it)
            get()?.dismissImmediately()
            getLoadingWrapper(it, style).loadingParent
        }
    }

    /**
     * 是否强行显示新的加载动画
     *
     * @param force true
     * false
     */
    @JvmStatic
    @JvmOverloads
    fun show(force: Boolean = false) {
        if (force) {
            get()?.dismissImmediately()
        }
        get()?.show()
    }

    @JvmStatic
    @JvmOverloads
    fun showWith(info: String = "加载中..", force: Boolean = false) {
        if (force) {
            get()?.dismissImmediately()
        }
        get()?.showWithStatus(info)

    }

    @JvmStatic
    fun dismiss() {
        dismissWrapper?.loadingParent?.dismiss()
    }

    @JvmStatic
    fun dismissImmediately() {
        dismissWrapper?.loadingParent?.dismissImmediately()
    }

    /**
     * 当前activity
     * */
    private val currentActivity: Activity?
        get() = ActivityStack.currentActivity()

    /**
     * 无wrapper 则创建
     * */
    @JvmStatic
    private val currentWrapper: LoadWrapper?
        get() = currentActivity?.let {
            getLoadingWrapper(it)
        }

    /**
     * 无LoadWrapper,则不创建并返回
     */
    private val dismissWrapper: LoadWrapper?
        get() = currentActivity?.let {
            wrapperMap[it]
        }

    private fun getLoadingWrapper(activity: Activity): LoadWrapper {
        return getLoadingWrapper(activity, mStyle)
    }

    private fun getLoadingWrapper(activity: Activity, style: LoadingStyle): LoadWrapper {
        var wrapper = wrapperMap[activity]
        if (wrapper == null) {
            wrapper = LoadWrapper(activity, style)
            wrapperMap[activity] = wrapper
        }
        return wrapper
    }

    /**
     * 监听activity 改变,自动创建释放对应LoadingParentView
     */
    class LoadWrapper constructor(activity: Activity, style: LoadingStyle) : ActivityChangeListener {
        val loadingParent: LoadingController

        /**
         * 不能在此移除监听,会导致迭代异常
         */
        override fun onActivityDestroy(activity: Activity) {
            wrapperMap.remove(activity)
        }

        override fun onActivitySateChange(activity: Activity, state: ActivityState) {}

        init {
            ActivityLifecycle.add(activity, this)
            loadingParent = LoadingController(activity, style)
        }

    }

}