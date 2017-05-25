package com.baibu.test.presenter;

import com.baibu.test.bean.Girl;
import com.baibu.test.modle.GirlModelImpl;
import com.baibu.test.modle.IGirlModel;
import com.baibu.test.viewinterface.IGirlView;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 */
public class GirlPresenterImp1<V> extends BasePresenter<V> {

    private IGirlView girlView;
    private IGirlModel girlModel = new GirlModelImpl();

    public GirlPresenterImp1(IGirlView girlView) {
        this.girlView = girlView;
    }

    @Override
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
