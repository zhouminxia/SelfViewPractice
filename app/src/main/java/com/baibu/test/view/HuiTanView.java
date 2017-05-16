package com.baibu.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by minna_Zhou on 2017/5/16.
 * 回弹view
 * <p>
 * 实现控件的移动三种方式：1改变控件的布局参数；2平移动画；3scrllto或scrllby
 * <p>
 * 第一步：控件向下拉动时，实现回弹；
 * 第二步：添加scrollview，结果回弹效果无效（ontouchevent没有调用）
 * 第三步：向下滑动时，要parent拦截，parent处理；向上滑动时，要parent不拦截，child处理
 * 第四步：只要是向下滑动，就拦截，有一个问题：子view必须滑到顶部，才拦截；子view没有滑动到顶部，不拦截。
 * 第五步：在onInterceptTouchEvent方法的motion_move中，添加一个判断条件，当子childscrollview在顶部时，才给父亲拦截。
 */

/*
* 外部拦截法：外部拦截法是指点击事件先经过父容器的拦截处理，如果父容器需要处理此事件就进行拦截，如果不需要此事件就不拦截，
* 这样就可以解决滑动冲突的问题。外部拦截法需要重写父容器的onInterceptTouchEvent()方法，在内部做相应的拦截即可。
*
*内部拦截法：内部拦截法是指点击事件先经过子View处理，如果子View需要此事件就直接消耗掉，否则就交给父容器进行处理，
* 这样就可以解决滑动冲突的问题。内部拦截法需要配合requestDisallowInterceptTouchEvent()方法，来确定子View是否允许父布局拦截事件。
*
* */
public class HuiTanView extends LinearLayout {
    //总共移动的y
    private int allMoveY = 0;
    //按下手指的y
    private int downY = 0;
    //移动的终点的y
    private int moveY = 0;

    //每一次移动的y
    private int singleMoveY = 0;


    //parent是否拦截child,
    private boolean isIntercept = false;
    //parent的第一个child控件
    private ScrollView scrollView;


    public HuiTanView(Context context) {
        this(context, null);
    }

    public HuiTanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HuiTanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        scrollView = (ScrollView) getChildAt(0);
    }

    //分发事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    //是否拦截
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int touchY = (int) ev.getY();
        float scrollY = scrollView.getScaleY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = touchY;
                break;
            case MotionEvent.ACTION_MOVE:
                moveY = touchY;
                //向下移动，并且在顶部时，要拦截
                if (moveY - downY > 0 && scrollY == 0) {
                    isIntercept = true;
                    //向上移动，不要拦截
                } else if (moveY - downY < 0) {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return isIntercept;
    }

    //事件处理
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
