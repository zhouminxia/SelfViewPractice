package com.baibu.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.baibu.test.view.MyHuiTanActivity;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_IMAGE = 112;
    public static final int REQUEST_SCAN = 110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.enter:
                Intent intent = new Intent(MainActivity.this, MyScanActivity.class);
                startActivityForResult(intent, REQUEST_SCAN);
                break;
            case R.id.zhizhu:
                Intent intent2 = new Intent(MainActivity.this, Secondeactivity.class);
                startActivity(intent2);
                break;
            case R.id.bezi_one:
                Intent intent3 = new Intent(MainActivity.this, BeziOneActivity.class);
                startActivity(intent3);
                break;
            case R.id.bezi_two:
                Intent intent4 = new Intent(MainActivity.this, BeziTwoActivity.class);
                startActivity(intent4);
                break;
            case R.id.bezi_three:
                Intent intent5 = new Intent(MainActivity.this, BeziThreeActivity.class);
                startActivity(intent5);
                break;
            case R.id.bezi_four:
                Intent intent6 = new Intent(MainActivity.this, BeziFourActivity.class);
                startActivity(intent6);
                break;
            case R.id.path_one:
                Intent intent7 = new Intent(MainActivity.this, PathViewOneActivity.class);
                startActivity(intent7);
                break;
            case R.id.path_one_me:
                Intent intent8 = new Intent(MainActivity.this, PathViewOneMeActivity.class);
                startActivity(intent8);
                break;
            case R.id.zoom_imageview:
                Intent intent9 = new Intent(MainActivity.this, ZoomImageActivity.class);
                startActivity(intent9);
                break;
            case R.id.taglayout:
                Intent intent10 = new Intent(MainActivity.this, TagLayoutActivity.class);
                startActivity(intent10);
                break;
            case R.id.taglayout_my:
                Intent intent11 = new Intent(MainActivity.this, MyTagLayoutActivity.class);
                startActivity(intent11);
                break;
            case R.id.huitan:
                Intent intent12 = new Intent(MainActivity.this, MyHuiTanActivity.class);
                startActivity(intent12);
                break;
            case R.id.scroll_conflict_one:
                Intent intent13 = new Intent(MainActivity.this, HuadongOneActivity.class);
                startActivity(intent13);
                break;
            case R.id.scroll_conflict_two:
                Intent intent14 = new Intent(MainActivity.this, HuadongTwoActivity.class);
                startActivity(intent14);
                break;
            case R.id.viewflipper:
                Intent intent15 = new Intent(MainActivity.this, HuadongXunhuanActivity.class);
                startActivity(intent15);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SCAN) {
                String content = data.getStringExtra("content");
                System.out.println("--content=" + content);
            }
        }
    }
}
