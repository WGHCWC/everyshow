package com.wghcwc.everyshow;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wghcwc.everyshowing.LoadingUtils;

/**
 * @author wghcwc
 * @date 20-1-6
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LoadingUtils.show();
        startActivity(new Intent(this, TestActivity.class));
    }

}
