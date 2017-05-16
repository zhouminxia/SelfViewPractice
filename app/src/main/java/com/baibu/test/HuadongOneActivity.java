package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 回弹
 */
public class HuadongOneActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private MyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huadong_one);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapter = new MyAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private class MyAdapter extends FragmentPagerAdapter {
        Fragment[] fragments = new Fragment[]{HDFragmentOne.newInstance(), HDFragmentTwo.newInstance(), HDFragmentThree.newInstance()};

        public MyAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return fragments.length;
        }
    }

}
