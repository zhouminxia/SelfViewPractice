package com.baibu.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by minna_Zhou on 2016/12/25.
 */
public class MyScanActivity extends AppCompatActivity implements CodeUtils.AnalyzeCallback {
    private CaptureFragment captureFragment;
    private boolean isOpen=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myscan);

        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.frame_layout_scan);
        captureFragment.setAnalyzeCallback(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();

    }

    @Override
    public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
        System.out.println("--result=" + result);
    }

    @Override
    public void onAnalyzeFailed() {
        System.out.println("--result失败");

    }

    public void flash(View view){
        if (!isOpen) {
            CodeUtils.isLightEnable(true);
            isOpen = true;
        } else {
            CodeUtils.isLightEnable(false);
            isOpen = false;
        }
    }

}

