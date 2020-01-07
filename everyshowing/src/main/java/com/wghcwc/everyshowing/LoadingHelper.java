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

    public static LoadingParentView get() {
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
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.loadingParent.show();
        }
    }

    public static void showWith() {
        showWith("加载中..");
    }


    public static void showWith(String info) {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.loadingParent.showWithStatus(info);
        }
    }

    public static void dismiss() {
        LoadWrapper wrapper = getDismissWrapper();
        if (wrapper != null) {
            wrapper.loadingParent.dismiss();
        }
    }
/*
    public static void delayShow() {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.delayShow();
        }
    }

    public static void delayShow(int times) {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.delayShow(times);
        }
    }

    public static void delayDismiss() {
        LoadWrapper wrapper = getDismissWrapper();
        if (wrapper != null) {
            wrapper.delayDismiss();
        }
    }
*/

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
        private LoadingParentView loadingParent;
        //        private Disposable disposable;
        //        private int count;

        private LoadWrapper(Activity activity, LoadingStyle style) {
            ActivityLifecycle.getInstance().add(activity, this);
            loadingParent = new LoadingParentView(activity, style);
        }

        public LoadingParentView getLoadingParent() {
            return loadingParent;
        }

        /*
         *//**
         * 0.5秒之内delayDismiss()调用则不显示加载动画,
         *//*
        private void delayShow() {
            if (loadingParent.isShowing()) {
                return;
            }
            Observable.timer(500, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        protected void onSuccess(Long aLong) {
                            loadingParent.showWithStatus("加载中...");
                        }


                    });
        }

        *//**
         * 多个请求
         *
         * @param times 请求个数
         *//*
        private void delayShow(int times) {
            count = times;
            delayShow();
        }

        */

        /**
         * 0.5秒之内未显示则取消,已显示则延迟0.5秒消失
         *//*
        private void delayDismiss() {
            if (--count > 0) {
                return;
            }
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            if (loadingParent.isShowing()) {
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Long>() {
                            @Override
                            protected void onSuccess(Long aLong) {
                                loadingParent.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                loadingParent.dismiss();
                            }
                        });
            }
        }*/

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
