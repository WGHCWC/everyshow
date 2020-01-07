package com.wghcwc.everyshowing.loadstyle;

import android.view.animation.Animation;
import android.widget.ImageView;

/**
 * @author wghcwc
 * @date 20-1-5
 */
public interface LoadingStyle {
    /**
     * 加载图片
     *
     * @return 加载需要图片
     */
    int getImage();

    /**
     * 加载背景
     *
     * @return 加载背景
     */
    int getBackground();

    /**
     * 加载位置
     *
     * @return 加载位置
     */
    int getGravity();

    int marginLeft();

    int marginRight();

    int marginTop();

    int marginBottom();

    /**
     * 加载padding
     *
     * @return 加载padding
     */
    int getPadding();

    /**
     * 提示图片
     *
     * @return 提示图片
     */
    int infoImage();

    /**
     * 成功图片
     *
     * @return 成功图片
     */
    int successImage();

    /**
     * 失败图片
     *
     * @return 失败图片
     */
    int errorImage();

    /**
     * 遮罩
     *
     * @return 遮罩
     */
    LoadingMaskType getMaskType();

    /**
     * 动画
     *
     * @return 动画
     */
    Animation getAnimation();

    /**
     * 设置图片ScaleType
     *
     * @return 图片ScaleType
     */
    ImageView.ScaleType getScaleType();
}
