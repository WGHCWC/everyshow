package com.wghcwc.everyshowing;

import android.app.Activity;

import com.wghcwc.activitylifecycle2.ActivityChangeListener;
import com.wghcwc.activitylifecycle2.ActivityLifecycle;
import com.wghcwc.activitylifecycle2.ActivityStack;
import com.wghcwc.activitylifecycle2.ActivityState;
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle;
import com.wghcwc.everyshowing.loadstyle.LoadingStyle;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 使用前需要依赖
 * <p>
 * allprojects {
 * repositories {
 * ....
 * maven { url 'https://jitpack.io' }
 * }
 * }
 * implementation 'com.github.WGHCWC:activitylifecycle:1.0.4'
 * </P>
 * {@link ActivityLifecycle}
 * 在 application 中初始化ActivityLifecycle.init(this);
 *
 * @author wghcwc
 * @date 19-11-26
 */
public class LoadingHelper {
    private static Map<Activity, LoadWrapper> wrapperMap = new WeakHashMap<>();
    private static LoadingStyle mStyle = new BaseLoadingStyle();

    /**
     * 设置全局样式
     */
    public static void setStyle(LoadingStyle style) {
        mStyle = style;
    }

    public static LoadingController get() {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            return wrapper.loadingParent;
        }
        return null;
    }

    /**
     * 设置当前activity加载样式,仅当前LoadWrapper有效
     */
    public static void setCurStyle(LoadingStyle style) {
        Activity activity = ActivityStack.currentActivity();
        if (activity == null) {
            return;
        }
        LoadWrapper loadWrapper = wrapperMap.get(activity);
        if (loadWrapper != null) {
            loadWrapper.loadingParent.dismissImmediately();
        }
        loadWrapper = new LoadWrapper(activity, style);
        wrapperMap.put(activity, loadWrapper);
    }


    public static void show() {
        show(false);
    }

    /**
     * 是否强行显示新的加载动画
     *
     * @param force true
     *              false
     */
    public static void show(boolean force) {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            if (force) {
                wrapper.loadingParent.dismissImmediately();
            }
            wrapper.loadingParent.show();

        }
    }

    public static void showWith() {
        showWith("加载中..", false);
    }

    public static void showWith(boolean force) {
        showWith("加载中..", force);
    }

    public static void showWith(String info) {
        showWith(info, false);
    }

    public static void showWith(String info, boolean force) {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            if (force) {
                wrapper.loadingParent.dismissImmediately();
            }
            wrapper.loadingParent.showWithStatus(info);
        }
    }

    public static void dismiss() {
        LoadWrapper wrapper = getDismissWrapper();
        if (wrapper != null) {
            try {
                wrapper.loadingParent.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void dismissImmediately() {
        LoadWrapper wrapper = getDismissWrapper();
        if (wrapper != null) {
            wrapper.loadingParent.dismissImmediately();
        }
    }

    private static LoadWrapper getCurrentWrapper() {
        Activity activity = ActivityStack.currentActivity();
        if (activity == null) {
            return null;
        }
        return getLoadingWrapper(activity);
    }

    /**
     * 无LoadWrapper,则不创建并返回
     */
    private static LoadWrapper getDismissWrapper() {
        Activity activity = ActivityStack.currentActivity();
        if (activity == null) {
            return null;
        }
        LoadWrapper wrapper = wrapperMap.get(activity);
        if (wrapper == null) {
            return null;
        }
        return getLoadingWrapper(activity);
    }

    private static LoadWrapper getLoadingWrapper(Activity activity) {
        return getLoadingWrapper(activity, mStyle);
    }

    private static LoadWrapper getLoadingWrapper(Activity activity, LoadingStyle style) {
        LoadWrapper wrapper = wrapperMap.get(activity);
        if (wrapper == null) {
            wrapper = new LoadWrapper(activity, style);
            wrapperMap.put(activity, wrapper);
        }
        return wrapper;
    }

    /**
     * 监听activity 改变,自动创建释放对应LoadingParentView
     */
    public static class LoadWrapper implements ActivityChangeListener {
        private LoadingController loadingParent;

        private LoadWrapper(Activity activity, LoadingStyle style) {
            ActivityLifecycle.getInstance().add(activity, this);
            loadingParent = new LoadingController(activity, style);
        }

        public LoadingController getLoadingParent() {
            return loadingParent;
        }

        /**
         * 不能在此移除监听,会导致迭代异常
         */
        @Override
        public void onActivityDestroy(Activity activity) {
            wrapperMap.remove(activity);
        }

        @Override
        public void onActivitySateChange(Activity activity, ActivityState state) {

        }
    }
}
