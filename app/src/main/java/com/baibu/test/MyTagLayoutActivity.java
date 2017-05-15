package com.baibu.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baibu.test.view.MyTaglayoutView;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 标签云
 */
public class MyTagLayoutActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout_my);


        MyTaglayoutView imageViewGroup = (MyTaglayoutView) findViewById(R.id.image_layout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String[] string = {"从我写代", "码那天起，", "我就没有打算写代码", "从我写代码那天起", "我就没有打算写代码", "没打算", "写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码写代码"};
        for (int i = 0; i < string.length; i++) {
            TextView textView = new TextView(this);
            textView.setText(string[i]);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.tag_background);
            imageViewGroup.addView(textView, lp);
        }
    }

}
