package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baibu.test.view.BeziThreeView;

/**
 * Created by minna_Zhou on 2017/4/21.
 */
public class BeziThreeActivity extends AppCompatActivity {

    private BeziThreeView bezi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezithree);
        bezi = (BeziThreeView) findViewById(R.id.beizi);
    }

    public void startAnimation(View view){
        bezi.startAnim();

    }
}
