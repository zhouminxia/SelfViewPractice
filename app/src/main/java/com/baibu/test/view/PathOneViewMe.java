package com.baibu.test.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by minna_Zhou on 2017/5/3.
 * 模仿搜索自定义view
 */
public class PathOneViewMe extends View {

    private Paint mPaint;
    private float[] pos;//望远镜把把的点
    private PathMeasure pathMeasure;
    private Path smallPath;
    private Path bigPath;
    private boolean isSearchOver;//搜索是否结束
    private float animatedValue;//每一种状态下的具体值
    private long duration = 1000;
    private ValueAnimator mStartValueAnimator;
    private ValueAnimator mSearchValueAnimator;
    private ValueAnimator mEndValueAnimator;
    private ValueAnimator.AnimatorUpdateListener updateListener;
    private Animator.AnimatorListener endListener;
    private Handler handler;
    private STATE mCurrentState = STATE.NONE;
    private int mWidth;//画布讲坐标轴移到中间，宽高是整个屏幕的一半
    private int mHeight;
    private int count = 0;

    public PathOneViewMe(Context context) {
        this(context, null);
    }

    public PathOneViewMe(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathOneViewMe(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAll();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w / 2;
        mHeight = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth, mHeight);
        startSearching(canvas);
    }

    private void startSearching(Canvas canvas) {
        switch (mCurrentState) {
            case NONE:
                canvas.drawPath(smallPath, mPaint);//一个望远镜
                break;
            case STARTING://望远镜逐渐变成一个点（把把上的一个点）
                pathMeasure.setPath(smallPath, false);
                float startD = pathMeasure.getLength() * animatedValue;
                float stopD = pathMeasure.getLength();
                Path des = new Path();
                pathMeasure.getSegment(startD, stopD, des, true);
                canvas.drawPath(des, mPaint);
                break;
            case SEARCHING:
                pathMeasure.setPath(bigPath, false);
                float stopD1 = pathMeasure.getLength() * animatedValue;
                float startD1 = (float) (stopD1 - ((0.5 - Math.abs(animatedValue - 0.5)) * 200f));//线段长度会周期性变化
                System.out.println("--startD1=" + startD1 + ",stopD1=" + stopD1);
                Path des1 = new Path();
                pathMeasure.getSegment(startD1, stopD1, des1, true);
                canvas.drawPath(des1, mPaint);
                break;
            case ENDING:
                pathMeasure.setPath(smallPath, false);
                Path des3 = new Path();
                pathMeasure.getSegment(pathMeasure.getLength() * animatedValue, pathMeasure.getLength(), des3, true);
                canvas.drawPath(des3, mPaint);
                break;
        }
    }

    private void initAll() {
        initPaint();
        initPath();
        initValueLister();
        initAnimator();
        initHandler();

        mCurrentState = STATE.STARTING;
        mStartValueAnimator.start();
    }

    //无、开始准备、搜索中、结束中
    enum STATE {
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    //handler负责 几个动作之间的切换
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                if (0 == what) {//动画的转场
                    switch (mCurrentState) {
                        case STARTING:
                            isSearchOver = false;
                            mCurrentState = STATE.SEARCHING;
                            mStartValueAnimator.removeAllListeners();
                            mSearchValueAnimator.start();
                            break;
                        case SEARCHING:
                            if (!isSearchOver) {//如果搜索没有结束
//                                mCurrentState = STATE.SEARCHING;
//                                mStartValueAnimator.removeAllListeners();
                                mSearchValueAnimator.start();

                                count++;
                                if (count > 2) {//转两个单位，结束
                                    isSearchOver = true;
                                    System.out.println("--restart");
//                                    mCurrentState = STATE.ENDING;
//                                    mSearchValueAnimator.removeAllUpdateListeners();
//                                    mEndValueAnimator.start();
                                }
                            } else {
//                                mSearchValueAnimator.removeAllListeners();
                                mCurrentState = STATE.ENDING;
                                mEndValueAnimator.start();
                            }
                            break;
                        case ENDING:
                            mCurrentState = STATE.NONE;
//                            mSearchValueAnimator.removeAllListeners();
                            break;
                    }
                }
            }
        };
    }

    private void initAnimator() {
        mStartValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        mSearchValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);
        mEndValueAnimator = ValueAnimator.ofFloat(0, 1).setDuration(duration);

        mStartValueAnimator.addUpdateListener(updateListener);
        mStartValueAnimator.addListener(endListener);

        mSearchValueAnimator.addUpdateListener(updateListener);
        mSearchValueAnimator.addListener(endListener);

        mEndValueAnimator.addUpdateListener(updateListener);
        mEndValueAnimator.addListener(endListener);
    }

    //知道搜索动画是否结束
    //数值
    private void initValueLister() {
        endListener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //===========发一个消息出去
                handler.sendEmptyMessage(0);
            }
        };

        updateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        };
    }

    private void initPath() {
        bigPath = new Path();
        smallPath = new Path();
        pathMeasure = new PathMeasure();


        RectF rectF2 = new RectF(-50, -50, 50, 50);
        smallPath.addArc(rectF2, 45, 359.9f);

        RectF rectF = new RectF(-100, -100, 100, 100);
        bigPath.addArc(rectF, 45, -359.9f);//-360°逆时针转

        pathMeasure.setPath(bigPath, false);
        pos = new float[2];
        pathMeasure.getPosTan(0, pos, null);
        smallPath.lineTo(pos[0], pos[1]);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }
}