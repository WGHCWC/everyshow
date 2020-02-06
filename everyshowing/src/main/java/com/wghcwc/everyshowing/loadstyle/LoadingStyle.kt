package com.wghcwc.everyshowing.loadstyle

import android.view.animation.Animation
import android.widget.ImageView

/**
 * @author wghcwc
 * @date 20-1-5
 */
interface LoadingStyle {

    /**
     * 加载图片
     *
     * @return 加载需要图片
     */
    val image: Int

    /**
     * 加载背景
     *
     * @return 加载背景
     */
    val background: Int

    /**
     * 加载位置
     *
     * @return 加载位置
     */
    val gravity: Int

    val marginLeft: Int get() = 0
    val marginRight: Int get() = 0
    val marginTop: Int get() = 0
    val marginBottom: Int get() = 0
    /**
     * 加载padding
     *
     * @return 加载padding
     */
    val padding: Int get() = 0

    /**
     * 提示图片
     *
     * @return 提示图片
     */
    val infoImage: Int

    /**
     * 成功图片
     *
     * @return 成功图片
     */
    val successImage: Int

    /**
     * 失败图片
     *
     * @return 失败图片
     */
    val errorImage: Int

    /**
     * 遮罩
     *
     * @return 遮罩
     */
    val maskType: LoadingMaskType

    /**
     * 动画
     *
     * @return 动画
     */
    val animation: Animation

    /**
     * 设置图片ScaleType
     *
     * @return 图片ScaleType
     */
    val scaleType: ImageView.ScaleType
}