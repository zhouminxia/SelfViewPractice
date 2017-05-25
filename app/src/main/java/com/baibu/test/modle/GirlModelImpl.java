package com.baibu.test.modle;

import com.baibu.test.R;
import com.baibu.test.bean.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 */
public class GirlModelImpl implements IGirlModel {
    @Override
    public void loadGirls(final GirlOnLoadLister girlOnLoadLister) {

                List<Girl> girls = new ArrayList<>();

                girls.add(new Girl(R.drawable.girl_one));
                girls.add(new Girl(R.drawable.girl_two));
                girls.add(new Girl(R.drawable.girl_three));
                girls.add(new Girl(R.drawable.girl_four));
                girls.add(new Girl(R.drawable.girl_five));

                girlOnLoadLister.onComplet(girls);
    }
}
