package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by minna_Zhou on 2017/5/16.
 * scrollview嵌套listview，listview内容显示不全
 */
public class HDlistview extends ListView {

    private int mLastY;

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


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int downY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = downY;
                getParent().requestDisallowInterceptTouchEvent(true);//要父亲不拦截
                break;
            case MotionEvent.ACTION_MOVE:

                //在最顶部，并且还在下滑
                if (isTop(this) && (downY - mLastY > 0)) {
                    getParent().requestDisallowInterceptTouchEvent(true);//要父亲不拦截
                    break;
                } else if (isBottom(this) && (downY - mLastY < 0)) {
                    getParent().requestDisallowInterceptTouchEvent(true);//要父亲不拦截
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isTop(ListView listView) {
        if (listView.getFirstVisiblePosition() == 0) {
            View childAt = listView.getChildAt(0);
            if (childAt.getTop() >= 0) {
                return true;
            }
        }

        return false;
    }

    private boolean isBottom(ListView listView) {
        if (listView.getLastVisiblePosition() == listView.getCount() - 1) {
            View childAt = listView.getChildAt(listView.getCount() - 1);
            if (listView.getBottom() < childAt.getBottom()) {
                return true;
            }
        }
        return false;
    }


}
