package com.baibu.test.view.listviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by minna_Zhou on 2017/5/17.
 */
public class HdListviewOne extends ListView {
    private int mLastDownX;
    private int mLastDownY;

    public HdListviewOne(Context context) {
        this(context,null);
    }

    public HdListviewOne(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HdListviewOne(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //内部拦截法:父亲都不拦截，都交给子控件。如果子控件需要，则自己消费；如果不需要，就调用父控件的requestdis....

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int downX= (int) ev.getX();
        int downY= (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);//要父亲不要拦截
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = downX - mLastDownX;
                int deltaY = downY - mLastDownY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);//要父亲拦截
                }

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastDownX = downX;
        mLastDownY = downY;
        return super.dispatchTouchEvent(ev);
    }
}
