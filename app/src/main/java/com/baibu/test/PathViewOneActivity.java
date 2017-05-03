package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baibu.test.view.BeziThreeView;

/**
 * Created by minna_Zhou on 2017/4/21.
 */
public class PathViewOneActivity extends AppCompatActivity {

    private BeziThreeView bezi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_one);
    }
}
