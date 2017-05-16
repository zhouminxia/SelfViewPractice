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
public class HDFragmentTwo extends Fragment {
    private ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hdfragment_two, container, false);
        return view;
    }

    public static Fragment newInstance() {
        HDFragmentTwo one = new HDFragmentTwo();
        return one;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        listview = (ListView) view.findViewById(R.id.listview);
//        String[] strs = new String[]{"22第一条", "22第二条", "22第三条", "22第四条", "22第五条", "22第六条", "22第七条", "22第八条", "22第九条", "22第十条", "22第十一条", "22第十二条", "22第十三条", "22第十四条", "22第十五条", "22第十六条"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strs);
//        listview.setAdapter(arrayAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
