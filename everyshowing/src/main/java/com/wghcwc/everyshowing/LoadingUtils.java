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
 * @author wghcwc
 * @date 19-11-26
 */
public class LoadingUtils {
    private static Map<Activity, LoadWrapper> wrapperMap = new WeakHashMap<>();
    private static LoadingStyle mStyle = new BaseLoadingStyle();
    public static void setStyle(LoadingStyle style) {
        mStyle = style;
    }

    public static LoadingParentView get() {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            return wrapper.getSvProgressHUD();
        }
        return null;
    }

    public static void setCurStyle(LoadingStyle style) {
        Activity activity = ActivityStack.currentActivity();
        if (activity == null) {
            return;
        }
        LoadWrapper loadWrapper = wrapperMap.get(activity);
        if (loadWrapper != null) {
            loadWrapper.svProgressHUD.dismissImmediately();
        }
        loadWrapper = new LoadWrapper(activity, style);
        wrapperMap.put(activity, loadWrapper);

    }


    public static void show() {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.show();
        }
    }

    public static void showWith() {
        showWith("加载中..");
    }


    public static void showWith(String info) {
        LoadWrapper wrapper = getCurrentWrapper();
        if (wrapper != null) {
            wrapper.getSvProgressHUD().showWithStatus(info);
        }
    }

    public static void dismiss() {
        LoadWrapper wrapper = getDismissWrapper();

        if (wrapper != null) {
            wrapper.dismiss();
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

    public static class LoadWrapper implements ActivityChangeListener {
        private LoadingParentView svProgressHUD;
        //        private Disposable disposable;
        private int count;

        private LoadWrapper(Activity activity, LoadingStyle style) {
            ActivityLifecycle.getInstance().add(activity, this);
            svProgressHUD = new LoadingParentView(activity, style);
        }

        private LoadingParentView getSvProgressHUD() {
            return svProgressHUD;
        }

        private void show() {
            svProgressHUD.show();
        }

        private void dismiss() {
            svProgressHUD.dismiss();
        }
        /*
         *//**
         * 0.5秒之内delayDismiss()调用则不显示加载动画,
         *//*
        private void delayShow() {
            if (svProgressHUD.isShowing()) {
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
                            svProgressHUD.showWithStatus("加载中...");
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
            if (svProgressHUD.isShowing()) {
                Observable.timer(500, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<Long>() {
                            @Override
                            protected void onSuccess(Long aLong) {
                                svProgressHUD.dismiss();
                            }

                            @Override
                            public void onError(Throwable e) {
                                svProgressHUD.dismiss();
                            }
                        });
            }
        }*/
        @Override
        public void onActivityDestroy(Activity activity) {
            wrapperMap.remove(activity);
        }

        @Override
        public void onActivitySateChange(Activity activity, ActivityState state) {

        }
    }
}
