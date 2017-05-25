package com.baibu.test.uiactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baibu.test.presenter.BasePresenter;

/**
 * Created by minna_Zhou on 2017/5/25.
 * 把view视图接口和p层
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attatchViewInterfaceToPresenter();
        if (getContentViewId() != 0) {
            setContentView(getContentViewId());
        }else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        initViewWidgets();
    }

    protected abstract void initViewWidgets();

    protected abstract int getContentViewId();

    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        mPresenter.detatchViewInerfaceFromPresenter();
        super.onDestroy();

    }
}
