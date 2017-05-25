package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * Created by minna_Zhou on 2017/5/17.
 */
public class HuadongXunhuanActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huadong_xunhuan);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

        View view1 = View.inflate(this, R.layout.layout_viewflipper_container, null);
        View view2 = View.inflate(this, R.layout.layout_viewflipper_container, null);
        View view3 = View.inflate(this, R.layout.layout_viewflipper_container, null);
        viewFlipper.addView(view1);
        viewFlipper.addView(view2);
        viewFlipper.addView(view3);


        viewFlipper.post(new Runnable() {
            @Override
            public void run() {

            }
        });

        //初始化initNum=0
        ThreadLocal<Integer> initNum = new ThreadLocal<Integer>() {

            @Override
            protected Integer initialValue() {
                return 0;
            }
        };


    }
}
