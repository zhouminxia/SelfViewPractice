package com.baibu.test.uiactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.baibu.test.R;
import com.baibu.test.adapter.MyGirlAdapter;
import com.baibu.test.bean.Girl;
import com.baibu.test.presenter.GirlPresenterImp;
import com.baibu.test.viewinterface.IGirlView;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 * mvp实现加载图片到listview
 */
public class MyMvpActivity extends AppCompatActivity implements IGirlView {

    private ListView listView;
    private GirlPresenterImp girlPresenterImp;
    private MyGirlAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mvp);
        listView = (ListView) findViewById(R.id.listview);

        girlPresenterImp = new GirlPresenterImp(this);
        girlPresenterImp.fetchData();
    }

    @Override
    public void showLoading() {
        Toast.makeText(this, "请求数据", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGirls(List<Girl> girls) {
        adapter = new MyGirlAdapter(this, girls);
        listView.setAdapter(adapter);
    }
}
