package com.wghcwc.everyshowing.loadstyle

/**
 * @author wghcwc
 * @date 20-1-7
 */
enum class LoadingMaskType {
    // 允许遮罩下面控件点击,点击取消,背景透明
    Clear,
    // 不允许遮罩下面控件点击，背景透明,点击取消
    ClearCancel,
    // 不允许遮罩下面控件点击,不允许取消
    Black,
    // 不允许遮罩下面控件点击，允许取消
    BlackCancel,
    // 不允许遮罩下面控件点击，背景黑色半透明,不允许取消
    Gradient,
    // 不允许遮罩下面控件点击，背景黑色半透明，点击遮罩消失
    GradientCancel,
    // 不允许遮罩下面控件点击，背景透明,不取消
    None
}