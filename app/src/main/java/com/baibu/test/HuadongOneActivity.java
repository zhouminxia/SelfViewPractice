package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baibu.test.view.scrollviews.MyHorizontalScrollview;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 回弹
 */
public class HuadongOneActivity extends AppCompatActivity {


    private MyHorizontalScrollview scrollview;
    private LinearLayout ll_container;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huadong_one);
        scrollview = (MyHorizontalScrollview) findViewById(R.id.scrollView);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);


        DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //窗口的宽度
        screenWidth = dm.widthPixels;

        //窗口高度
        screenHeight = dm.heightPixels;



        String[] strs = new String[30];
        for (int i = 0; i < 30; i++) {
            strs[i] = "第" + i + "条记录";
        }
        for (int i = 0; i < 3; i++) {
            ListView listView = new ListView(this);
            listView.setLayoutParams(new ListView.LayoutParams(screenWidth, ListView.LayoutParams.WRAP_CONTENT));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
            listView.setAdapter(adapter);
            ll_container.addView(listView);
        }
    }
}
