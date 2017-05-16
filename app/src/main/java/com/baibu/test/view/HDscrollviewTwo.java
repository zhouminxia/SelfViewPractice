package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;

import com.baibu.test.R;

/**
 * Created by minna_Zhou on 2017/5/16.
 */
public class HDscrollviewTwo extends ScrollView {

    private ListView mListView;
    private int nowY;

    public HDscrollviewTwo(Context context) {
        this(context,null);
    }

    public HDscrollviewTwo(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HDscrollviewTwo(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        View childAt = getChildAt(0);
        mListView = (ListView) childAt.findViewById(R.id.listview);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercepted = false;
        int y = (int) event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                nowY = y;
                intercepted = super.onInterceptTouchEvent(event);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(mListView.getFirstVisiblePosition()==0
                        && y>nowY){
                    intercepted = true;
                    break;
                }
                else if(mListView.getLastVisiblePosition()==mListView.getCount()-1
                        && y<nowY){
                    intercepted = true;
                    break;
                }
                intercepted = false;
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }

        return intercepted;
    }
}
