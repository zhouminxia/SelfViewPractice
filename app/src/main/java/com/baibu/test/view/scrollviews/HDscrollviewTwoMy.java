package com.baibu.test.view.scrollviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.baibu.test.R;

/**
 * Created by minna_Zhou on 2017/5/17.
 */
public class HDscrollviewTwoMy extends ScrollView {

    private ListView listview;
    private int lastY;

    public HDscrollviewTwoMy(Context context) {
        this(context, null);
    }

    public HDscrollviewTwoMy(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HDscrollviewTwoMy(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        View view = getChildAt(0);
        listview = (ListView) view.findViewById(R.id.listview);
    }

    //外部拦截法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int downY = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = downY;
                isIntercept = super.onInterceptTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:

                //优化。当第一个或最后一个出来一点点，scrollview也会拦截
//                //Lisstview第一个，并且还下滑；
                if ((isTop(listview)) && (downY - lastY > 0)) {
                    isIntercept = true;
                    break;
                }

                //listview最后一个，还上滑动
                else if ((isBottom(listview)) && (downY - lastY < 0)) {
                    isIntercept = true;
                    break;
                }
//                if ((listview.getFirstVisiblePosition() == 0) && (downY - lastY > 0)) {
//                    isIntercept = true;
//                    break;
//                }
//
//                //listview最后一个，还上滑动
//                else if ((listview.getLastVisiblePosition() == listview.getCount() - 1) && (downY - lastY < 0)) {
//                    isIntercept = true;
//                    break;
//                }
                isIntercept = false;
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                break;
            default:
                break;
        }
        return isIntercept;
    }


    /**
     * 比单纯的比较listview.getLastVisiblePosition()==0 更完善点
     *
     * @param listView
     * @return
     */
    public boolean isBottom(final ListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        }
        return result;
    }

    /**
     * 比单纯的比较listview.getFirstVisiblePosition() == 0更完善点
     *
     * @param listView
     * @return
     */
    public boolean isTop(final ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }


}
