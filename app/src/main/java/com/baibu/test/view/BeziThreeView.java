package com.baibu.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by minna_Zhou on 2017/4/21.
 * 圆变成心形
 */
public class BeziThreeView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private float mCircleRadius = 100;//圆的半径
    private float mOffset = 40;
    private float mDifference = mCircleRadius * C;//控制点和数据点之间的差距
    private Canvas mCanvas;
    private int mCenterX;
    private int mCenterY;
    private Paint mRedPaint;

    private PointF point0;
    private PointF point1;
    private PointF point2;
    private PointF point3;
    private PointF ponit011;
    private PointF ponit012;
    private PointF ponit121;
    private PointF ponit122;
    private PointF ponit231;
    private PointF ponit232;
    private PointF ponit341;
    private PointF ponit342;


    private float mDuration = 1000;                     // 变化总时长
    private float mCurrent = 0;                         // 当前已进行时长
    private float mCount = 100;                         // 将时长总共划分多少份
    private float mPiece = mDuration / mCount;            // 每一份的时长
    private Path mPath;
    private float mInterpolatedTime;//0--1
    // interpolatedTime The value of the normalized time (0.0 to 1.0)
// after it has been run through the interpolation function.

    public BeziThreeView(Context context) {
        this(context, null);
    }

    public BeziThreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeziThreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initDataAndCtrol();
    }

    private void initPaint() {
        mRedPaint = new Paint();
        mRedPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        mRedPaint.setStyle(Paint.Style.FILL);
    }


    //需要12个点
    private void initDataAndCtrol() {

        point0 = new PointF(0, mCircleRadius);
        point1 = new PointF(mCircleRadius, 0);
        point2 = new PointF(0, -mCircleRadius);
        point3 = new PointF(-mCircleRadius, 0);

        ponit011 = new PointF(point0.x + mDifference, point0.y);
        ponit012 = new PointF(point1.x, point1.y + mDifference);
        ponit121 = new PointF(point1.x, point1.y - mDifference);
        ponit122 = new PointF(point2.x + mDifference, point2.y);
        ponit231 = new PointF(point2.x - mDifference, point2.y);
        ponit232 = new PointF(point3.x, point3.y - mDifference);
        ponit341 = new PointF(point3.x, point3.y + mDifference);
        ponit342 = new PointF(point0.x - mDifference, point0.y);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mCanvas = canvas;

        mCanvas.translate(mCenterX, mCenterY);

        if (mInterpolatedTime > 0 && mInterpolatedTime < 0.2) {
            move2();

        } else if (mInterpolatedTime >= 0.2 && mInterpolatedTime < 0.5) {
            move3();
        } else if (mInterpolatedTime >= 0.5 && mInterpolatedTime < 0.8) {

        } else if (mInterpolatedTime >= 0.8 && mInterpolatedTime < 0.9) {
            move4();
        } else if (mInterpolatedTime >= 0.9 && mInterpolatedTime <= 1) {
            move1();
        }

        //画目标圆心
        drawCircle();
    }

    private void move1() {
        point0.x = 0;
        point1.x = mCircleRadius;
        ponit012.x = point1.x;
        ponit121.x = point1.x;


        point3.x = -mCircleRadius;
        ponit232.x = point3.x;
        ponit341.x = point3.x;

    }

    //第三个状态
    private void move3() {
        point1.x = mCircleRadius + mOffset;
        ponit012.x = point1.x;
        ponit121.x = point1.x;

        point3.x = -mCircleRadius - mOffset;
        ponit232.x = point3.x;
        ponit341.x = point3.x;

    }

    //第二个状态
    private void move2() {
        point1.x = mCircleRadius + mOffset;
        ponit012.x = point1.x;
        ponit121.x = point1.x;
    }

    private void move4() {
        point1.x = mCircleRadius + mOffset;
        ponit012.x = point1.x;
        ponit121.x = point1.x;

        point3.x = -mCircleRadius - mOffset;
        ponit232.x = point3.x;
        ponit341.x = point3.x;
    }

    private void drawCircle() {
        mPath = new Path();//坐标的y是向下的
        mPath.moveTo(point0.x, point0.y);//第一个点
        mPath.cubicTo(ponit011.x, ponit011.y, ponit012.x, ponit012.y, point1.x, point1.y);//一象限的弧度
        mPath.cubicTo(ponit121.x, ponit121.y, ponit122.x, ponit122.y, point2.x, point2.y);//二象限的弧度
        mPath.cubicTo(ponit231.x, ponit231.y, ponit232.x, ponit232.y, point3.x, point3.y);//三象限的弧度
        mPath.cubicTo(ponit341.x, ponit341.y, ponit342.x, ponit342.y, point0.x, point0.y);//四象限的弧度

        mCanvas.drawPath(mPath, mRedPaint);

    }


    /**
     * 供外面调用，启动动画
     */
    public void startAnim() {
        mPath.reset();
        mInterpolatedTime = 0;
        MyAnimation animation = new MyAnimation();
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        startAnimation(animation);
    }

    private class MyAnimation extends Animation {
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            mInterpolatedTime = interpolatedTime;
            invalidate();
        }
    }
}
