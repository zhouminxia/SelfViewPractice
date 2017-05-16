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
public class HDFragmentThree extends Fragment {
    private ListView listview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hdfragment_three, container, false);
        return view;
    }

    public static Fragment newInstance() {
        HDFragmentThree one = new HDFragmentThree();
        return one;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        listview = (ListView) view.findViewById(R.id.listview);
//        String[] strs = new String[]{"33第一条", "33第二条", "33第三条", "33第四条", "33第五条", "33第六条", "33第七条", "33第八条", "33第九条", "33第十条", "33第十一条", "33第十二条", "33第十三条", "33第十四条", "33第十五条", "33第十六条"};
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strs);
//        listview.setAdapter(arrayAdapter);
        super.onViewCreated(view, savedInstanceState);
    }
}
