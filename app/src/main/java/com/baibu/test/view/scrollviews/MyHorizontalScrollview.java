package com.baibu.test.view.scrollviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by minna_Zhou on 2017/5/17.
 * 水平的scrollveiw嵌套listview，制造滑动冲突。
 * 因为viewpager+fragemtn，viewpager内部已经做好滑动冲突处理了，所以不好模拟。
 * 就采用HorizontalScrollView
 * <p/>
 * <p/>
 * 但是我没有模拟出冲突情况，所以只是看看思想就好。毕竟现在水平滑动的，已经有viewpager+fragemnt了
 * 水平和垂直滑动冲突：
 * 外部拦截法：父亲都拦截下来，如果父容器需要则拦截，如果不需要则不拦截
 * 内部拦截发：父亲都不拦截，都给子控件分发。同时父亲的down事件不能拦截
 */
public class MyHorizontalScrollview extends HorizontalScrollView {

    private boolean isIntercept = false;

    private int mLastDownX;
    private int mLastDownY;

    public MyHorizontalScrollview(Context context) {
        this(context, null);
    }

    public MyHorizontalScrollview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyHorizontalScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        super.onMeasure(expandSpec, heightMeasureSpec);
//    }


    //外部拦截
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        int downX = (int) ev.getX();
//        int downY = (int) ev.getY();
//
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                isIntercept = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                int deltaX = downX - mLastDownX;
//                int deltaY = downY - mLastDownY;
//                if (Math.abs(deltaX) > Math.abs(deltaY)) {
//                    isIntercept = true;
//                } else {
//                    isIntercept = false;
//                }
//
//                break;
//            case MotionEvent.ACTION_UP:
//                isIntercept = false;
//                break;
//        }
//        mLastDownX = downX;
//        mLastDownY = downY;
//
//        return isIntercept;
//    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (MotionEvent.ACTION_DOWN == action) {
            return false;
        } else {
            return true;
        }
    }
}
