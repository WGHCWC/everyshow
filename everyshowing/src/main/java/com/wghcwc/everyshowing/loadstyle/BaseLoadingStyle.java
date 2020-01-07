package com.wghcwc.everyshowing.loadstyle;

import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.wghcwc.everyshowing.R;

/**
 * @author wghcwc
 * @date 20-1-5
 */
public class BaseLoadingStyle implements LoadingStyle {
    protected RotateAnimation rotateAnimation;

    protected void initAnimation() {
        rotateAnimation = new RotateAnimation(0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000L);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.RESTART);
    }

    @Override
    public int getImage() {
        return R.drawable.ic_svstatus_loading;
    }

    @Override
    public int getBackground() {
        return R.drawable.bg_svprogresshuddefault;
    }

    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public int marginLeft() {
        return 0;
    }

    @Override
    public int marginRight() {
        return 0;
    }

    @Override
    public int marginTop() {
        return 0;
    }

    @Override
    public int marginBottom() {
        return 0;
    }


    @Override
    public int getPadding() {
        return 0;
    }

    @Override
    public int infoImage() {
        return R.drawable.ic_svstatus_info;
    }

    @Override
    public int successImage() {
        return R.drawable.ic_svstatus_success;
    }

    @Override
    public int errorImage() {
        return R.drawable.ic_svstatus_error;
    }

    @Override
    public LoadingMaskType getMaskType() {
        return LoadingMaskType.Black;
    }

    @Override
    public Animation getAnimation() {
        if (rotateAnimation == null) {
            initAnimation();
        }
        return rotateAnimation;
    }

    @Override
    public ImageView.ScaleType getScaleType() {
        return ImageView.ScaleType.FIT_CENTER;
    }

}
