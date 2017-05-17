package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;

import com.baibu.test.view.HDlistview;
import com.baibu.test.view.scrollviews.HDscrollviewTwoMy;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 回弹
 */
public class HuadongTwoActivity extends AppCompatActivity {


    private HDlistview listView;
    private HDscrollviewTwoMy scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huadong_two);
        listView = (HDlistview) findViewById(R.id.listview);
        scrollView = (HDscrollviewTwoMy) findViewById(R.id.scrollView);

        String[] strs = new String[30];

        for (int i = 0; i < 30; i++) {
            strs[i] = "第" + (i + 1) + "条";
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs);
        listView.setAdapter(arrayAdapter);
    }

}
