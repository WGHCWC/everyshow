package com.wghcwc.everyshowing.toast.style;

import android.content.Context;
import android.view.Gravity;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/ToastUtils
 * time   : 2018/09/01
 * desc   : 默认白色样式实现
 */
public class ToastWhiteStyle extends ToastBlackStyle {

    public ToastWhiteStyle(Context context) {
        super(context);
    }

    @Override
    public int getBackgroundColor() {
        return 0XFEFEFEFE;
    }

    @Override
    public int getTextColor() {
        return 0xff000000;
    }

    @Override
    public int getYOffset() {
        return dp2px(60);
    }

    @Override
    public int getPaddingStart() {
        return dp2px(24);
    }

    @Override
    public int getPaddingTop() {
        return dp2px(13);
    }

    @Override
    public int getGravity() {
        return Gravity.BOTTOM;
    }
}