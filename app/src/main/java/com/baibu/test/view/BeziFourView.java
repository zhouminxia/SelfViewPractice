package com.baibu.test.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import com.baibu.test.R;

/**
 * Created by minna_Zhou on 2017/5/2.
 * 箭头跟着走
 * <p/>
 * PathMeasure measure = new PathMeasure(path, false);
 * pathmeasure.getPosTan(float distance,float[] pos,float[] tan)
 * distance:距离Path起点的长度;获取当前位置的坐标以及趋势,到pos  tan
 */
public class BeziFourView extends View {

    private int mViewWidth;
    private int mViewHeight;
    private Paint mDeafultPaint;
    private float mCurrentValue;//距起点多少距离
    private Matrix matrix;
    private float[] pos = new float[2];//距离路径起点的 点的横坐标
    private float[] tan = new float[2];//距离路径起点的 tan值 tan[1]是y,tan[0]是x
    private Bitmap bitmap;

    public BeziFourView(Context context) {
        this(context, null);
    }

    public BeziFourView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeziFourView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mDeafultPaint = new Paint();
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setStrokeWidth(3);
        mDeafultPaint.setColor(context.getResources().getColor(R.color.colorAccent));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;//缩放比例
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_right, options);

        matrix = new Matrix();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mViewWidth / 2, mViewHeight / 2);      // 平移坐标系

        Path path = new Path();
        path.addCircle(0, 0, 200, Path.Direction.CW);


        mCurrentValue += 0.005;
        if (mCurrentValue > 1) {
            mCurrentValue = 0;
        }

        PathMeasure measure = new PathMeasure(path, false);//构造pathmeasure

        //==========1.自己封装matrix
//        measure.getPosTan(measure.getLength() * mCurrentValue, pos, tan);//距离起点一定距离的，放到pos 和tan，图片的旋转角度
//
//        float degrees = (float) (Math.atan2(tan[1], tan[0]) * 180 / Math.PI);// 计算图片旋转角度
//
//        matrix.reset();//每次都置空
//        matrix.postRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);//返回boolean值
//        matrix.postTranslate(pos[0] - bitmap.getWidth() / 2, pos[1] - bitmap.getHeight() / 2);//把图片的起点 与当前点 一致
//
//        canvas.drawPath(path, mDeafultPaint);//画外面的红色圈圈
//        canvas.drawBitmap(bitmap, matrix, mDeafultPaint);


        //==========2.利用measure.getmeasure.

        //两个flag都选
        measure.getMatrix(measure.getLength() * mCurrentValue,matrix,PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);
        matrix.preTranslate(- bitmap.getWidth() / 2,- bitmap.getHeight() / 2);
        canvas.drawPath(path, mDeafultPaint);//画外面的红色圈圈
        canvas.drawBitmap(bitmap, matrix, mDeafultPaint);//matrix包含了旋转角度，矩阵对旋转角度默认为图片的左上角
        invalidate();
    }
}
