package com.wghcwc.everyshow;

import com.wghcwc.everyshowing.loadstyle.BaseLoadingStyle;
import com.wghcwc.everyshowing.loadstyle.LoadingMaskType;

/**
 * @author wghcwc
 * @date 20-1-7
 */
public class MyLoadingStyle extends BaseLoadingStyle {
    @Override
    public int getImage() {
        return R.mipmap.loadding;
    }

    @Override
    public int getBackground() {
        return R.color.tans;
    }

    @Override
    public LoadingMaskType getMaskType() {
        return LoadingMaskType.ClearCancel;
    }
}
