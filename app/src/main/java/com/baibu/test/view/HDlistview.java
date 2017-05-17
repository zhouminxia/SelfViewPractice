package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by minna_Zhou on 2017/5/16.
 * scrollview嵌套listview，listview内容显示不全
 *
 */
public class HDlistview extends ListView {

    public HDlistview(Context context) {
        this(context, null);
    }

    public HDlistview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HDlistview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, expandSpec);
//
//    }
}
