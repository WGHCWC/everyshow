package com.wghcwc.everyshow;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.wghcwc.everyshowing.LoadingHelper;

/**
 * @author wghcwc
 * @date 20-1-7
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LoadingHelper.showWith();
        LoadingHelper.show(true);

    }
}
