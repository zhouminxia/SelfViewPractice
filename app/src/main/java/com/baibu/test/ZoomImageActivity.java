package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baibu.test.view.ZoomImageViewOne;

/**
 * Created by minna_Zhou on 2017/5/4.
 */
public class ZoomImageActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private int[] mImgs = new int[]{R.drawable.icon_64, R.drawable.brighton, R.drawable.heart_lung_128};
    private ImageView[] mImageViews = new ImageView[mImgs.length];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_imageview);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mImgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ZoomImageViewOne imageView = new ZoomImageViewOne(
                        getApplicationContext());
                imageView.setImageResource(mImgs[position]);
                container.addView(imageView);
                mImageViews[position] = imageView;
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mImageViews[position]);
            }
        });

    }

}
