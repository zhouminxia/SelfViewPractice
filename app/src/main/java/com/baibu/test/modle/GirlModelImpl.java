package com.baibu.test.modle;

import android.os.Handler;
import android.os.Looper;

import com.baibu.test.R;
import com.baibu.test.bean.Girl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 */
public class GirlModelImpl implements IGirlModel {
    Handler handler = new Handler(Looper.getMainLooper());//拿到主线程的Handler


    @Override
    public void loadGirls(final GirlOnLoadLister girlOnLoadLister) {

        new Thread() {
            @Override
            public void run() {
                final List<Girl> girls = new ArrayList<>();

                girls.add(new Girl(R.drawable.girl_one));
                girls.add(new Girl(R.drawable.girl_two));
                girls.add(new Girl(R.drawable.girl_three));
                girls.add(new Girl(R.drawable.girl_four));
                girls.add(new Girl(R.drawable.girl_five));

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        girlOnLoadLister.onComplet(girls);//要回调给主线程
                    }
                }, 3000);
            }
        }.start();


    }
}
