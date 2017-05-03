package com.baibu.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by minna_Zhou on 2017/4/21.
 * 圆变成心形
 */
public class BeziTwoView extends View {
    private static final float C = 0.551915024494f;     // 一个常量，用来计算绘制圆形贝塞尔曲线控制点的位置
    private float mCircleRadius = 200;//圆的半径
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

    public BeziTwoView(Context context) {
        this(context, null);
    }

    public BeziTwoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeziTwoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initDataAndCtrol();
    }

    private void initPaint() {
        mRedPaint = new Paint();
        mRedPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        mRedPaint.setStyle(Paint.Style.STROKE);
        mRedPaint.setStrokeWidth(5);
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

        //画坐标轴
        drawCoordidate();

        //画目标圆心
        drawCircle();
    }

    private void drawCircle() {
        Path path = new Path();
        path.moveTo(point0.x, point0.y);//第一个点
        path.cubicTo(ponit011.x, ponit011.y, ponit012.x, ponit012.y, point1.x, point1.y);//一象限的弧度
        path.cubicTo(ponit121.x, ponit121.y, ponit122.x, ponit122.y, point2.x, point2.y);//二象限的弧度
        path.cubicTo(ponit231.x, ponit231.y, ponit232.x, ponit232.y, point3.x, point3.y);//三象限的弧度
        path.cubicTo(ponit341.x, ponit341.y, ponit342.x, ponit342.y, point0.x, point0.y);//四象限的弧度

        mCanvas.drawPath(path, mRedPaint);

        mCurrent += mPiece;//每次加个10
        if (mCurrent < mDuration) {
            //y轴上的 纵坐标减少
            point2.y += 80 / mCount;

            ponit011.y -= 50 / mCount;
            ponit012.x -= 20 / mCount;
            ponit342.y -= 50 / mCount;
            ponit341.x += 20 / mCount;

            postInvalidateDelayed((long) mPiece);
        }
    }

    private void drawCoordidate() {
        mCanvas.drawLine(0, mCenterY, 0, -mCenterY, mRedPaint);
        mCanvas.drawLine(-mCenterX, 0, mCenterX, 0, mRedPaint);
    }
}
