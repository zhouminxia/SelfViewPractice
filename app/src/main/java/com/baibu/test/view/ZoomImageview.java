package com.baibu.test.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by minna_Zhou on 2017/5/4.
 * 可以自动缩放的imageview
 */
public class ZoomImageview extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private float mIniScale = 1;
    private float mMaxScale;
    private float mMidScale;//双击时的缩放
    private boolean firstInit = false;
    private Matrix matrix;

    public ZoomImageview(Context context) {
        this(context, null);
    }

    public ZoomImageview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
        matrix = new Matrix();
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

            float mScale = 1;
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
            matrix.postTranslate(dx, dy);
            matrix.postScale(mIniScale, mIniScale, getWidth() / 2, getHeight() / 2);
            setImageMatrix(matrix);
        }
    }
}
