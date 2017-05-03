package com.baibu.test.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by minna_Zhou on 2017/4/13.
 */
public class ZhizhuView extends View {
    private int centerX;
    private int centerY;
    private float radius;//最外层半径
    private int count = 6;//六边形liuceng
    private Canvas mCanvas;
    private float angle = (float) (Math.PI * 2 / count);//每个的角度，一个60°
    private Paint mPolygonPaint;//多边形框框
    private Paint mHoVerPaint;//横轴数轴
    private Paint mTextPaint;//文字
    private Paint valuePaint;               //数据区画笔
    private int mWidth;
    private int mHeight;
    private String[] titles = {"第一个", "第二个", "第三个", "第四个", "第五个", "第六个"};
    private double[] data = {100, 60, 60, 60, 100, 50, 10, 20}; //各维度分值
    private float maxValue = 100;             //数据最大值


    public ZhizhuView(Context context) {
        this(context, null);
    }

    public ZhizhuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhizhuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
        initHoriAndVerPaint();
    }

    private void initHoriAndVerPaint() {
        mHoVerPaint = new Paint();
        mHoVerPaint.setAntiAlias(true);
        mHoVerPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        mHoVerPaint.setStyle(Paint.Style.STROKE);
        mHoVerPaint.setStrokeWidth(2);

    }

    private void initPaint() {
        mPolygonPaint = new Paint();
        mPolygonPaint.setAntiAlias(true);
        mPolygonPaint.setColor(getResources().getColor(android.R.color.black));
        mPolygonPaint.setStyle(Paint.Style.STROKE);
        mPolygonPaint.setStrokeWidth(2);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(30);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.BLUE);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;

        radius = Math.min(h, w) / 2 * 0.9f;
        centerX = w / 2;
        centerY = h / 2;
        System.out.println("--centerx=" + centerX + ",centery=" + centerY);
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;

        drawHoVer();
        drawDuobianxing();
        drawInner();
        drawText();
        drawRegion();
    }

    private void drawRegion() {
        Path path = new Path();
        valuePaint.setAlpha(255);
        for (int i = 0; i < count; i++) {
            double percent = data[i] / maxValue;
            float x = (float) (centerX + radius * Math.cos(angle * i) * percent);
            float y = (float) (centerY + radius * Math.sin(angle * i) * percent);
            if (i == 0) {
                path.moveTo(x, centerY);
            } else {
                path.lineTo(x, y);
            }
            //绘制小圆点
            mCanvas.drawCircle(x, y, 10, valuePaint);
        }
        //画点点
        valuePaint.setStyle(Paint.Style.STROKE);
        mCanvas.drawPath(path, valuePaint);

        //画区域
        valuePaint.setAlpha(127);
        //绘制填充区域
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCanvas.drawPath(path, valuePaint);
    }

    /**
     * 画文字
     * 文字的点 和终端点是一样的 偏移一点就好
     */
    private void drawText() {
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;//baseline之上 是ascent,之下是descent，所以字体高度是descent-ascent
        // http://mikewang.blog.51cto.com/3826268/871765/
        for (int i = 0; i < count; i++) {
            float x = (float) (centerX + (radius + fontHeight / 2) * Math.cos(angle * i));//radius+fontHeight/2在原来的基础上加了一个字体的高度一半
            float y = (float) (centerY + (radius + fontHeight / 2) * Math.sin(angle * i));
            float degree = angle * i;

            if (degree >= 0 && degree <= Math.PI / 2) {//第4象限
                mCanvas.drawText(titles[i], x, y, mTextPaint);
            } else if (degree >= 3 * Math.PI / 2 && degree <= Math.PI * 2) {//第1象限
                mCanvas.drawText(titles[i], x, y, mTextPaint);
            } else if (degree > Math.PI / 2 && degree <= Math.PI) {//第3象限
                float dis = mTextPaint.measureText(titles[i]);//文本长度
                mCanvas.drawText(titles[i], x - dis, y, mTextPaint);
            } else if (degree >= Math.PI && degree < 3 * Math.PI / 2) {//第2象限
                float dis = mTextPaint.measureText(titles[i]);//文本长度
                mCanvas.drawText(titles[i], x - dis, y, mTextPaint);
            }
        }
    }

    /**
     * 画里面的线,圆心和末端断点
     */
    private void drawInner() {
        if (mCanvas == null) {
            return;
        }
        Path path = new Path();
        for (int i = 0; i < count; i++) {
            path.reset();
            path.moveTo(centerX, centerY);//中心点
            float x = (float) (centerX + Math.cos(i * angle) * radius);//cos(0)=1,sin(0)=0
            float y = (float) (centerY + Math.sin(i * angle) * radius);//第一二象限的角度是负角度，三四象限的角度是正角度。
            path.lineTo(x, y);
            mCanvas.drawPath(path, mPolygonPaint);
        }
    }

    /**
     * 画水平和垂直的线
     */
    private void drawHoVer() {
        Path path = new Path();
        path.reset();
        path.moveTo(mWidth, centerY);
        path.lineTo(0, centerY);
        mCanvas.drawPath(path, mHoVerPaint);

        path.reset();

        path.moveTo(centerX, 0);
        path.lineTo(centerX, mHeight);

        mCanvas.drawPath(path, mHoVerPaint);
    }

    /**
     * 画多边形
     */
    private void drawDuobianxing() {
        if (mCanvas == null) {
            return;
        }

        //先画一条横轴和数轴


        float d = radius / count;//d是蜘蛛丝之间的间距
        Path path = new Path();
        for (int i = 1; i <= count; i++) {
            float currentR = d * i;//当前的半径
            path.reset();//划一圈就重置path清除Path中的内容
            //currentR==1时，最里面的框框

            //画六个点
            for (int j = 0; j < count; j++) {
                if (j == 0) {
                    path.moveTo(centerX + currentR, centerY);
                } else {
                    float x = (float) (Math.cos(angle * j) * currentR + centerX);
                    float y = (float) (Math.sin(angle * j) * currentR + centerY);
                    System.out.println("--" + j + "点(" + x + "," + y + ")");
                    path.lineTo(x, y);
                }
            }
            path.close();//闭合 第一个点和最后一个点闭合，如果不是闭合的，自动闭合
            mCanvas.drawPath(path, mPolygonPaint);
        }
    }
}
