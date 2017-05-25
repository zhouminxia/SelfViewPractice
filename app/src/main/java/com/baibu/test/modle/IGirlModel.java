package com.baibu.test.modle;

import com.baibu.test.bean.Girl;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 * model负责数据的请求，回调给p层。p层有activity的引用，p层再回传给activity。
 * 将m和v隔开。
 */
public interface IGirlModel {
    void loadGirls(GirlOnLoadLister girlOnLoadLister);

    interface GirlOnLoadLister {
        void onComplet(List<Girl> girls);
    }
}
