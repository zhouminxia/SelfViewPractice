package com.baibu.test.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 可以自动缩放的imageview
 * <p>
 * 1.图片居中缩放；布局里面控件设置为全Matchparent，图片src自动居中缩放。
 * 2.多指缩放。
 */
public class ZoomImageview extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private float mIniScale = 1;
    private float mMaxScale;
    private float mMidScale;//双击时的缩放
    private boolean firstInit = false;
    private Matrix mMatrix;
    private Context mContext;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScale;//第一次缩放的
    private int mViewWidth;
    private int mViewHeight;

    public ZoomImageview(Context context) {
        this(context, null);
    }

    public ZoomImageview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);//不知道为什么设置这句话后，图片就变小了
        mScaleGestureDetector = new ScaleGestureDetector(mContext, this);
        setOnTouchListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        //整个控件的宽高
        mViewWidth = getWidth();
        mViewHeight = getHeight();
        if (!firstInit) {
            firstInit = true;

            Drawable drawable = getDrawable();
            if (isDrawableNull()) return;

            //图片的宽高
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            mScale = 1;

            //用大的除以小的，获得放大比例；用小的除以大的，获得缩小比例

            //intrinsicWidth大，intrinsicHeight大
            if (intrinsicHeight > mViewHeight && intrinsicWidth > mViewWidth) {
                mScale = Math.min(mViewHeight * 1.0f / intrinsicHeight, mViewWidth * 1.0f / intrinsicWidth);
            }
            //intrinsicWidth小，intrinsicHeight小
            if (intrinsicHeight < mViewHeight && intrinsicWidth < mViewWidth) {
                mScale = Math.min(mViewHeight * 1.0f / intrinsicHeight, mViewWidth * 1.0f / intrinsicWidth);
            }
            //intrinsicWidth大，intrinsicHeight小
            if (intrinsicHeight > mViewHeight && intrinsicWidth < mViewWidth) {
                mScale = intrinsicHeight * 1.0f / mViewHeight;
            }
            //intrinsicWidth小，intrinsicHeight大
            if (intrinsicHeight < mViewHeight && intrinsicWidth > mViewWidth) {
                mScale = intrinsicWidth * 1.0f / mViewWidth;
            }

            mIniScale = mScale;
            mMaxScale = mIniScale * 4;
            mMidScale = mIniScale * 2;

            //把图片移动到屏幕中心,移动的dx dy
            int dx = mViewWidth / 2 - intrinsicWidth / 2;
            int dy = mViewHeight / 2 - intrinsicHeight / 2;

            //利用matrix移动和缩放
            //先平移，再缩放
            mMatrix.postTranslate(dx, dy);
            mMatrix.postScale(mIniScale, mIniScale, mViewWidth / 2, mViewHeight / 2);
            setImageMatrix(mMatrix);
        }
    }

    /**
     * 当前matrix缩放比例
     *
     * @return
     */
    private float getCurrentScale() {
        float[] v = new float[9];
        mMatrix.getValues(v);
        return v[Matrix.MSCALE_X];

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (isDrawableNull()) return true;

        float scaleFactor = detector.getScaleFactor();
        Toast.makeText(mContext, "onScale,scaleFactor=" + scaleFactor, Toast.LENGTH_LONG).show();
        float currentScale = getCurrentScale();
        if ((scaleFactor > 1.0f && currentScale < mMaxScale) || scaleFactor < 1.0f && currentScale > mIniScale) {

            if (scaleFactor * currentScale < mIniScale) {
                scaleFactor = mIniScale / currentScale;
            }
            if (scaleFactor * currentScale > mMaxScale) {
                scaleFactor = mMaxScale / currentScale;
            }

            //永远以屏幕中心进行缩放
//            mMatrix.postScale(scaleFactor, scaleFactor, mViewWidth / 2, mViewHeight / 2);

            //以触摸点来进行缩放，但是有问题：会留白
            mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

            //时时来修正图片，设置图片偏移量，不要留白
            fixBorderBlank();

            setImageMatrix(mMatrix);
        }
        return true;
    }

    /**
     * 修正周围的留白部分，设置图片偏移量
     * 思路：分别判断水平和垂直方向的留白部分，进行修正
     */
    private void fixBorderBlank() {


        if (isDrawableNull()) {
            return;
        }
        RectF rectF = getMatrxRectF();
        //缩放后的图片的宽高
        float picWidth = rectF.width();
        float picHeight = rectF.height();
        float deltaX = 0;//X轴的偏移量
        float deltaY = 0;//Y轴的偏移量

        //水平方向的
        if (picWidth >= mViewWidth) {//大于宽度
            //左边留白
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            //右边留白
            if (mViewWidth - rectF.right > 0) {
                deltaX = mViewWidth - rectF.right;
            }
        } else {//小于宽度
            //居中
            deltaX = mViewWidth / 2 - (rectF.right - picWidth / 2);
        }

        //垂直方向的
        if (picHeight >= mViewHeight) {
            //上面留白
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            //下面留白
            if (mViewHeight - rectF.bottom > 0) {
                deltaY = mViewHeight - rectF.bottom;
            }
        } else {
            deltaY = mViewHeight / 2 - (rectF.bottom - picHeight / 2);
        }

        mMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 图片是否为空
     * true 没有设置图片
     *
     * @return
     */
    private boolean isDrawableNull() {
        return getDrawable() == null ? true : false;
    }

    /**
     * 获得图片缩小/放大后的rectf，left top right bottom
     *
     * @return
     */
    private RectF getMatrxRectF() {
        RectF rectF = new RectF();
        Drawable d = getDrawable();

        rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        Matrix matrix = mMatrix;

        /**测量rect并将测量结果放入rect中，
         * Apply this matrix to the rectangle, and write the transformed rectangle
         * back into it. This is accomplished by transforming the 4 corners of rect,
         * and then setting it to the bounds of those points*/
        matrix.mapRect(rectF);
        return rectF;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
