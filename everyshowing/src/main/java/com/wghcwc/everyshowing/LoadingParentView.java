package com.wghcwc.everyshowing;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.wghcwc.everyshowing.listener.OnDismissListener;
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle;
import com.wghcwc.everyshowing.loadstyle.LoadingStyle;
import com.wghcwc.everyshowing.loadstyle.LoadingMaskType;
import com.wghcwc.everyshowing.view.SVCircleProgressBar;
import com.wghcwc.everyshowing.view.LoadingDefaultView;

import java.lang.ref.WeakReference;


/**
 * @author wghcwc
 */
public class LoadingParentView {
    private WeakReference<Context> contextWeak;
    private static final long DISMISSDELAYED = 1000;
    private boolean isShowing;
    private boolean isDismissing;
    private LoadingStyle style;


    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    private ViewGroup rootView;
    private LoadingDefaultView mSharedView;
    private ViewGroup decorView;

    private Animation outAnim;
    private Animation inAnim;
    private int gravity;
    private OnDismissListener onDismissListener;


    public LoadingParentView(Context context) {
        this(context, new BaseLoadingStyle());
    }


    public LoadingParentView(Context context, LoadingStyle style) {
        this.contextWeak = new WeakReference<>(context);
        gravity = style.getGravity();
        this.style = style;
        initViews();
        initDefaultView();
        initAnimation();
    }

    protected void initViews() {
        Context context = contextWeak.get();
        if (context == null) return;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        decorView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (ViewGroup) layoutInflater.inflate(R.layout.layout_svprogresshud, null, false);
        rootView.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }

    protected void initDefaultView() {
        Context context = contextWeak.get();
        if (context == null) return;
        mSharedView = new LoadingDefaultView(context, style);
        params.gravity = gravity;
        mSharedView.setLayoutParams(params);
    }

    protected void initAnimation() {
        if (inAnim == null)
            inAnim = getInAnimation();
        if (outAnim == null)
            outAnim = getOutAnimation();
    }

    /**
     * show的时候调用
     */
    private void onAttached() {
        isShowing = true;
        decorView.addView(rootView);
        if (mSharedView.getParent() != null)
            ((ViewGroup) mSharedView.getParent()).removeView(mSharedView);
        rootView.addView(mSharedView);
    }

    /**
     * 添加这个View到Activity的根视图
     */
    private void svShow() {
        mHandler.removeCallbacksAndMessages(null);
        onAttached();
        mSharedView.startAnimation(inAnim);

    }

    public void show() {
        if (isShowing()) return;
        setMaskType(style.getMaskType());
        mSharedView.show();
        svShow();
    }

    public void showWithMaskType(LoadingMaskType maskType) {
        if (isShowing()) return;
        //判断maskType
        setMaskType(maskType);
        mSharedView.show();
        svShow();
    }

    public void showWithStatus(String string) {
        if (isShowing()) return;
        setMaskType(style.getMaskType());
        mSharedView.showWithStatus(string);
        svShow();
    }

    public void showWithStatus(String string, LoadingMaskType maskType) {
        if (isShowing()) return;
        setMaskType(maskType);
        mSharedView.showWithStatus(string);
        svShow();
    }

    public void showInfoWithStatus(String string) {
        if (isShowing()) return;
        setMaskType(style.getMaskType());
        mSharedView.showInfoWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showInfoWithStatus(String string, LoadingMaskType maskType) {
        if (isShowing()) return;
        setMaskType(maskType);
        mSharedView.showInfoWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showSuccessWithStatus(String string) {
        if (isShowing()) return;
        setMaskType(style.getMaskType());
        mSharedView.showSuccessWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showSuccessWithStatus(String string, LoadingMaskType maskType) {
        if (isShowing()) return;
        setMaskType(maskType);
        mSharedView.showSuccessWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showErrorWithStatus(String string) {
        if (isShowing()) return;
        setMaskType(style.getMaskType());
        mSharedView.showErrorWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showErrorWithStatus(String string, LoadingMaskType maskType) {
        if (isShowing()) return;
        setMaskType(maskType);
        mSharedView.showErrorWithStatus(string);
        svShow();
        scheduleDismiss();
    }

    public void showWithProgress(String string, LoadingMaskType maskType) {
        if (isShowing()) return;
        setMaskType(maskType);
        mSharedView.showWithProgress(string);
        svShow();
    }

    public SVCircleProgressBar getProgressBar() {
        return mSharedView.getCircleProgressBar();
    }

    public void setText(String string) {
        mSharedView.setText(string);
    }

    private void setMaskType(LoadingMaskType maskType) {
        switch (maskType) {
            case None:
                configMaskType(android.R.color.transparent, false, false);
                break;
            case Clear:
                configMaskType(android.R.color.transparent, true, false);
                break;
            case ClearCancel:
                configMaskType(android.R.color.transparent, true, true);
                break;
            case Black:
                configMaskType(R.color.bgColor_overlay, true, false);
                break;
            case BlackCancel:
                configMaskType(R.color.bgColor_overlay, true, true);
                break;
            case Gradient:
                configMaskType(R.drawable.bg_overlay_gradient, true, false);
                break;
            case GradientCancel:
                configMaskType(R.drawable.bg_overlay_gradient, true, true);
                break;
            default:
                break;
        }
    }

    private void configMaskType(int bg, boolean clickable, boolean cancelable) {
        rootView.setBackgroundResource(bg);
        rootView.setClickable(clickable);
        setCancelable(cancelable);
    }

    /**
     * 检测该View是不是已经添加到根视图
     *
     * @return 如果视图已经存在该View返回true
     */
    public boolean isShowing() {
        return rootView.getParent() != null || isShowing;
    }

    public void dismiss() {
        if (isDismissing || !isShowing()) return;
        isDismissing = true;
        //消失动画
        outAnim.setAnimationListener(outAnimListener);
        mSharedView.dismiss();
        mSharedView.startAnimation(outAnim);
    }

    public void dismissImmediately() {
        mSharedView.dismiss();
        rootView.removeView(mSharedView);
        decorView.removeView(rootView);
        isShowing = false;
        isDismissing = false;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }

    }

    public Animation getInAnimation() {
        Context context = contextWeak.get();
        if (context == null) return null;

        int res = LoadingAnimateUtil.getAnimationResource(this.gravity, true);
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        Context context = contextWeak.get();
        if (context == null) return null;

        int res = LoadingAnimateUtil.getAnimationResource(this.gravity, false);
        return AnimationUtils.loadAnimation(context, res);
    }

    private void setCancelable(boolean isCancelable) {
        View view = rootView.findViewById(R.id.sv_outmost_container);

        if (isCancelable) {
            view.setOnTouchListener(onCancelableTouchListener);
        } else {
            view.setOnTouchListener(null);
        }
    }

    private Handler mHandler = new InnerHandler(this);

    private static class InnerHandler extends Handler {
        private WeakReference<LoadingParentView> mWeakReference;

        private InnerHandler(LoadingParentView svProgressHUD) {
            mWeakReference = new WeakReference<>(svProgressHUD);
        }

        @Override
        public void handleMessage(Message msg) {
            mWeakReference.get().dismiss();
        }
    }


    private void scheduleDismiss() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(0, DISMISSDELAYED);
    }

    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                dismiss();
                setCancelable(false);
            }
            return false;
        }
    };

    private Animation.AnimationListener outAnimListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            dismissImmediately();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void setOnDismissListener(OnDismissListener listener) {
        this.onDismissListener = listener;
    }

    public OnDismissListener getOnDismissListener() {
        return onDismissListener;
    }

}
