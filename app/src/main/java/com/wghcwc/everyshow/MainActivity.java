package com.wghcwc.everyshow;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wghcwc.everyshowing.LoadingHelper;

/**
 * @author wghcwc
 * @date 20-1-6
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        LoadingHelper.showWith();
        startActivity(new Intent(this, TestActivity.class));
    }

}
