package com.baibu.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by minna_Zhou on 2017/4/14.
 * 贝塞尔曲线一:
 */
public class BeziOneView extends View {
    private Paint mDataPaint;
    private Paint mControlPaint;
    private Paint mPaint;
    private Paint mSecondPaint;
    private int mWidth;
    private int mHeight;
    private Canvas mCanvus;
    private PointF controlPoint;
    private PointF endPoint;
    private PointF startPoint;
    private int centerX;
    private int centerY;

    public BeziOneView(Context context) {
        this(context, null);
    }

    public BeziOneView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeziOneView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initPoint();
    }

    /**
     * 初始化三个点的位置
     */
    private void initPoint() {
        startPoint = new PointF();
        startPoint.x=0;
        startPoint.y=0;

        endPoint = new PointF();
        endPoint.x=0;
        endPoint.y=0;

        controlPoint = new PointF();
        controlPoint.x=0;
        controlPoint.y=0;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        mDataPaint = new Paint();
        mDataPaint.setColor(Color.BLACK);
        mDataPaint.setStrokeWidth(15);
        mDataPaint.setStyle(Paint.Style.STROKE);
        mDataPaint.setTextSize(60);

        mControlPaint = new Paint();
        mControlPaint.setColor(Color.BLUE);
        mControlPaint.setStrokeWidth(15);
        mControlPaint.setStyle(Paint.Style.STROKE);
        mControlPaint.setTextSize(60);


        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.STROKE);

        mSecondPaint = new Paint();
        mSecondPaint.setColor(Color.BLUE);
        mSecondPaint.setStrokeWidth(3);
        mSecondPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        centerX = mWidth / 2;
        centerY = mHeight / 2;

        startPoint.set(centerX - 200, centerY);
        endPoint.set(centerX + 200, centerY);
        controlPoint.set(centerX, centerY - 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvus = canvas;
        drawBeziOne();
    }

    /**
     * 画贝塞尔曲线 一阶
     */
    private void drawBeziOne() {


        //绘制数据点和控制点
        mCanvus.drawPoint(startPoint.x, startPoint.y, mDataPaint);
        mCanvus.drawPoint(endPoint.x, endPoint.y, mDataPaint);
        mCanvus.drawPoint(controlPoint.x, controlPoint.y, mControlPaint);

        //绘制辅助线
        mCanvus.drawLine(startPoint.x, startPoint.y, controlPoint.x, controlPoint.y, mSecondPaint);
        mCanvus.drawLine(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y, mSecondPaint);


        //绘制贝塞尔曲线
        mPaint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);//起点
        path.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y);//控制点、结束点

        mCanvus.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {//根据触摸位置移动重绘
        controlPoint.x = event.getX();
        controlPoint.y = event.getY();
        invalidate();
        return true;
    }
}
