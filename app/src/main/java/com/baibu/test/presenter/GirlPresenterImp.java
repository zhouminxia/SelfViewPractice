package com.baibu.test.presenter;

import com.baibu.test.bean.Girl;
import com.baibu.test.modle.GirlModelImpl;
import com.baibu.test.modle.IGirlModel;
import com.baibu.test.viewinterface.IGirlView;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 * 业务逻辑，
 */
public class GirlPresenterImp {
    /**
     * 成员变量girlView,接收activity的引用。activity和p互相引用。
     */
    private IGirlView girlView;

    /**
     * 成员变量girlModel，接收model引用。和Model的数据回调。
     */
    private IGirlModel girlModel = new GirlModelImpl();

    public GirlPresenterImp(IGirlView girlView) {
        this.girlView = girlView;
    }


    public void fetchData() {
        girlView.showLoading();
        girlModel.loadGirls(new IGirlModel.GirlOnLoadLister() {
            @Override
            public void onComplet(List<Girl> girls) {
                if (girlView != null) {
                    girlView.showGirls(girls);
                }
            }
        });
    }

}
