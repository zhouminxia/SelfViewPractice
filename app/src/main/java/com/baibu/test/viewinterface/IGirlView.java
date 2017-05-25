package com.baibu.test.viewinterface;

import com.baibu.test.bean.Girl;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 * UI的显示逻辑
 */
public interface IGirlView {
    void showLoading();

    void showGirls(List<Girl> girls);
}
