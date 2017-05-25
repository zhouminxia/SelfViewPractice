package com.baibu.test.uiactivity;

import android.widget.ListView;
import android.widget.Toast;

import com.baibu.test.R;
import com.baibu.test.adapter.MyGirlAdapter;
import com.baibu.test.bean.Girl;
import com.baibu.test.presenter.GirlPresenterImp1;
import com.baibu.test.viewinterface.IGirlView;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 */
public class MyMvpActivityOne extends BaseActivity<IGirlView, GirlPresenterImp1<IGirlView>> implements IGirlView {
    private ListView listView;
    private MyGirlAdapter adapter;
    @Override
    protected void initViewWidgets() {
        listView = (ListView) findViewById(R.id.listview);
        mPresenter.fetchData();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_mvp;
    }

    @Override
    protected GirlPresenterImp1<IGirlView> createPresenter() {

        return new GirlPresenterImp1<>(this);
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
