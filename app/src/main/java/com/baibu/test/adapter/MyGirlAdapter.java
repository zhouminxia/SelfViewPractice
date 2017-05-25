package com.baibu.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.baibu.test.R;
import com.baibu.test.bean.Girl;

import java.util.List;

/**
 * Created by minna_Zhou on 2017/5/25.
 */
public class MyGirlAdapter extends BaseAdapter {
    private Context mContext;
    private List<Girl> mGirls;

    public MyGirlAdapter(Context context, List<Girl> girls) {
        mContext = context;

        this.mGirls = girls;
    }

    @Override
    public int getCount() {
        return mGirls.size();
    }

    @Override
    public Object getItem(int position) {
        return mGirls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GrilHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_girl, parent, false);
            holder = new GrilHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (GrilHolder) convertView.getTag();
        }

        holder.icon.setImageResource(mGirls.get(position).getIcon());
        return convertView;
    }

    public static class GrilHolder {
        public ImageView icon;
    }
}
