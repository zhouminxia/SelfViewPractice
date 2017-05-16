package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by minna_Zhou on 2017/5/16.
 * 当下滑时且在顶部，UP后就回弹
 */
public class HuiTanView2 extends LinearLayout {
    private int downY;
    private int moveY;
    private int allMoveY;
    private ScrollView mScrollview;
    private boolean isIntercept = false;
    private int mMove;

    public HuiTanView2(Context context) {
        this(context, null);
    }

    public HuiTanView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuiTanView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScrollview = (ScrollView) getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        int scrollY = mScrollview.getScrollY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = y;
                Log.i("HT", " MotionEvent.ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("HT", " MotionEvent.ACTION_MOVE");
                moveY = y;
                if (moveY - downY > 0 && scrollY == 0) {
                    if (!isIntercept) {
//                        downY = (int) event.getY();
                        isIntercept = true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("HT", " MotionEvent.ACTION_UP");
                layout(getLeft(), getTop() - allMoveY, getRight(), getBottom() - allMoveY);
                allMoveY = 0;
                isIntercept = false;
                break;
        }
        if (isIntercept) {
            mMove = moveY - downY;
            allMoveY += mMove;
            layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
        }
        return isIntercept;
    }
}
