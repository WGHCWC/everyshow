package com.wghcwc.everyshowing.loadstyle;

/**
 * @author wghcwc
 * @date 20-1-7
 */
public enum LoadingMaskType {
    // 允许遮罩下面控件点击
    Clear,
    // 不允许遮罩下面控件点击
    Black,
    // 不允许遮罩下面控件点击，背景黑色半透明
    Gradient,
    // 不允许遮罩下面控件点击，背景渐变半透明
    ClearCancel,
    // 不允许遮罩下面控件点击，点击遮罩消失
    BlackCancel,
    // 不允许遮罩下面控件点击，背景黑色半透明，点击遮罩消失
    GradientCancel,
    // 不允许遮罩下面控件点击，背景透明
    None,
}
