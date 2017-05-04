package com.baibu.test.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 可以自动缩放的imageview
 * <p/>
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
//        setScaleType(ScaleType.MATRIX);//不知道为什么设置这句话后，图片就变小了

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
        if (!firstInit) {
            firstInit = true;

            //整个控件的宽高
            int width = getWidth();
            int height = getHeight();

            Drawable drawable = getDrawable();
            if (drawable == null) return;

            //图片的宽高
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();

            mScale = 1;
            //intrinsicWidth大，intrinsicHeight大
            if (intrinsicHeight > height && intrinsicWidth > width) {
                mScale = Math.min(intrinsicHeight * 1.0f / height, intrinsicWidth * 1.0f / width);
            }
            //intrinsicWidth小，intrinsicHeight小
            if (intrinsicHeight < height && intrinsicWidth < width) {
                mScale = Math.min(intrinsicHeight * 1.0f / height, intrinsicWidth * 1.0f / width);
            }
            //intrinsicWidth大，intrinsicHeight小
            if (intrinsicHeight > height && intrinsicWidth < width) {
                mScale = intrinsicHeight * 1.0f / height;
            }
            //intrinsicWidth小，intrinsicHeight大
            if (intrinsicHeight < height && intrinsicWidth > width) {
                mScale = intrinsicWidth * 1.0f / width;
            }

            mIniScale = mScale;
            mMaxScale = mIniScale * 4;
            mMidScale = mIniScale * 2;


            //把图片移动到屏幕中心,移动的dx dy
            int dx = getWidth() / 2 - intrinsicWidth / 2;
            int dy = getHeight() / 2 - intrinsicHeight / 2;

            //利用matrix移动和缩放
            //先平移，再缩放
            mMatrix.postTranslate(dx, dy);
            mMatrix.postScale(mIniScale, mIniScale, getWidth() / 2, getHeight() / 2);
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
        if (getDrawable() == null) return true;

//        float scaleFactor = detector.getScaleFactor();
        float scaleFactor = mScale;
        float currentScale = getCurrentScale();

        if ((scaleFactor > 1.0f && scaleFactor * currentScale < mMaxScale) || scaleFactor < 1.0f && scaleFactor * currentScale > mIniScale) {

            if (scaleFactor * currentScale < mIniScale) {
                scaleFactor = mIniScale / currentScale;
            }
            if (scaleFactor * currentScale > mMaxScale) {
                scaleFactor = mMaxScale / currentScale;
            }
        }

        mMatrix.postScale(scaleFactor, scaleFactor, getWidth() / 2, getHeight() / 2);
        setImageMatrix(mMatrix);

        return true;
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
