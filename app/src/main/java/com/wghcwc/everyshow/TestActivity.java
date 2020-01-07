package com.wghcwc.everyshow;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wghcwc.everyshowing.LoadingUtils;

/**
 * @author wghcwc
 * @date 20-1-7
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LoadingUtils.show();
        LoadingUtils.setCurStyle(new MyLoadingStyle());
        LoadingUtils.show();
    }

}
