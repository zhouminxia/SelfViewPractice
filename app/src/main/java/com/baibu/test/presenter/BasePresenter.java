package com.baibu.test.presenter;

import java.lang.ref.WeakReference;

/**
 * Created by minna_Zhou on 2017/5/25.
 * 为避免内存泄露，MVC和MVP的实现方法都是在activity的ondestroy中分离和p层的联系
 * 由于每个activity都要有一个presenter，activity在Oncreate和Ondestroy中，都要相应的attach和detatch
 */
public abstract class BasePresenter<V> {

    /**
     * 同一包、子类
     * 用弱引用保存view的引用
     */
    protected WeakReference<V> mViewRefe;

    public void attatchViewInterfaceToPresenter() {
        mViewRefe = new WeakReference<V>((V) this);

    }

    public void detatchViewInerfaceFromPresenter() {
        if (mViewRefe != null) {
            mViewRefe.clear();
            mViewRefe = null;
        }
    }

    public abstract void fetchData();
}
