package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by minna_Zhou on 2017/5/16.
 * 回弹view
 * <p/>
 * 实现控件的移动三种方式：1改变控件的布局参数；2平移动画；3scrllto或scrllby
 */
public class HuiTanView extends LinearLayout {
    //总共移动的y
    private int allMoveY = 0;
    //按下手指的y
    private int downY = 0;
    //移动的终点的y
    private int moveY = 0;

    //每一次移动的y
    private int singleMoveY = 0;


    public HuiTanView(Context context) {
        this(context, null);
    }

    public HuiTanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuiTanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //第一步：当向下滑时，记录滑动的距离和总滑动距离，让scrollview平移
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //接触屏幕时的点
        int touchY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = touchY;
                Log.i("HT", "ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = touchY;
                //如果是向下滑动
                if (moveY - downY > 0) {
                    singleMoveY = moveY - downY;
                    allMoveY += singleMoveY;
                    //向下滑动：通过改变控件的布局参数
                    layout(getLeft(), getTop() + singleMoveY, getRight(), getBottom() + singleMoveY);//重新布局
                }
                Log.i("HT", "ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - allMoveY, getRight(), getBottom() - allMoveY);
                //每次UP，记得归零
                allMoveY = 0;
                Log.i("HT", "ACTION_UP");
                break;
        }
        return true;

    }
}
