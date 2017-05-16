package com.baibu.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by minna_Zhou on 2017/5/16.
 */
public class HDFragmentOne extends Fragment {

    private ListView listview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hdfragment_one, container, false);
        return view;
    }

    public static Fragment newInstance() {
        HDFragmentOne one = new HDFragmentOne();
        return one;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        listview = (ListView) view.findViewById(R.id.listview);
//        String[] strs = new String[]{"第一条", "第二条", "第三条", "第四条", "第五条", "第六条", "第七条", "第八条", "第九条", "第十条", "第十一条", "第十二条", "第十三条", "第十四条", "第十五条", "第十六条"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strs);
//        listview.setAdapter(arrayAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
