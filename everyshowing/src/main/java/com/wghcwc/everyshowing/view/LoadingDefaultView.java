package com.wghcwc.everyshowing.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wghcwc.everyshowing.R;
import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle;
import com.wghcwc.everyshowing.loadstyle.LoadingStyle;


/**
 * @author wghcwc
 * @date 20-1-5
 */
public class LoadingDefaultView extends LinearLayout {
    private ImageView ivBigLoading, ivSmallLoading;
    private SVCircleProgressBar circleProgressBar;
    private TextView tvMsg;
    private LoadingStyle mStyle;
    private LinearLayout background;

    public LoadingDefaultView(Context context, LoadingStyle style) {
        super(context);
        this.mStyle = style;
        initViews();
    }

    public LoadingDefaultView(Context context) {
        this(context, new BaseLoadingStyle());
    }


    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_svprogressdefault, this, true);
        background = findViewById(R.id.background);
        background.setBackgroundResource(mStyle.getBackground());
        ivBigLoading = findViewById(R.id.ivBigLoading);
        ivBigLoading.setScaleType(mStyle.getScaleType());
        ivBigLoading.setPadding(mStyle.getPadding(), mStyle.getPadding(), mStyle.getPadding(), mStyle.getPadding());
        ivSmallLoading = findViewById(R.id.ivSmallLoading);
        circleProgressBar = findViewById(R.id.circleProgressBar);
        tvMsg = findViewById(R.id.tvMsg);
    }

    public void show() {
        clearAnimations();
        ivBigLoading.setImageResource(mStyle.getImage());
        ivBigLoading.setVisibility(View.VISIBLE);
        ivSmallLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.GONE);
        tvMsg.setVisibility(View.GONE);
        ivBigLoading.startAnimation(mStyle.getAnimation());
    }

    public void showWithStatus(String string) {
        if (string == null) {
            show();
            return;
        }
        showBaseStatus(mStyle.getImage(), string);
        ivSmallLoading.startAnimation(mStyle.getAnimation());
    }

    public void showInfoWithStatus(String string) {
        showBaseStatus(mStyle.infoImage(), string);
    }

    public void showSuccessWithStatus(String string) {
        showBaseStatus(mStyle.successImage(), string);
    }

    public void showErrorWithStatus(String string) {
        showBaseStatus(mStyle.errorImage(), string);
    }

    public void showWithProgress(String string) {
        showProgress(string);
    }

    public SVCircleProgressBar getCircleProgressBar() {
        return circleProgressBar;
    }

    public void setText(String string) {
        tvMsg.setText(string);
    }

    public void showProgress(String string) {
        clearAnimations();
        tvMsg.setText(string);
        ivBigLoading.setVisibility(View.GONE);
        ivSmallLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.VISIBLE);
    }

    public void showBaseStatus(int res, String string) {
        clearAnimations();
        ivSmallLoading.setImageResource(res);
        tvMsg.setText(string);
        ivBigLoading.setVisibility(View.GONE);
        circleProgressBar.setVisibility(View.GONE);
        ivSmallLoading.setVisibility(View.VISIBLE);
        tvMsg.setVisibility(View.VISIBLE);
    }

    public void dismiss() {
        clearAnimations();
    }

    private void clearAnimations() {
        ivBigLoading.clearAnimation();
        ivSmallLoading.clearAnimation();
    }

}
