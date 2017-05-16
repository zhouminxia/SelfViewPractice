package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by minna_Zhou on 2017/5/16.
 * 当滚动到顶部时，才允许parent拦截；
 */
public class MyScrollview extends ScrollView {
    public MyScrollview(Context context) {
        this(context, null);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //要父亲不拦截，我来处理
                getParent().requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_MOVE:
                int scrollY = getScrollY();
                //在顶部时，才拦截。
                if (scrollY == 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);//双重否定-肯定-
                    //不在顶部时，不拦截
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
