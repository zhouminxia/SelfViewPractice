package com.baibu.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.baibu.test.R;

/**
 * Created by minna_Zhou on 2017/5/13.
 * 标签云
 */
public class MyTaglayoutView extends ViewGroup {

    private float horizontalSpace;
    private float verticalSpace;
    private int parentWidthMeasureMode;
    private int parentHeightMeasureMode;
    private int parentWidthMeasureSize;
    private int parentHeightMeasureSize;

    public MyTaglayoutView(Context context) {
        this(context, null);
    }

    public MyTaglayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTaglayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    /**
     * 获得自定义属性
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyTagsLayout);
        if (typedArray != null) {
            horizontalSpace = typedArray.getDimension(R.styleable.MyTagsLayout_myTagHorizontalSpace, 10);
            verticalSpace = typedArray.getDimension(R.styleable.MyTagsLayout_myTagVerticalSpace, 10);
        }

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //parent提供的测量模式和测量大小
        parentWidthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        parentHeightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        parentWidthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        parentHeightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);

        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingRight = getPaddingRight();

        //如果parent是wrapcontent，要记录child的高宽
        int parentWrapChildWidth = 0;
        int parentWrapChildHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == GONE) {
                continue;//跳出本次循环，继续下次循环
            }

            //测量child
            measureChild(childAt, widthMeasureSpec, heightMeasureSpec);

            //拿到child的marginleft和marginright
            MarginLayoutParams childLayoutParams = (MarginLayoutParams) childAt.getLayoutParams();

            //child自身的宽、包含了leftmargin+rightmargin+horizontalspace
            int childWidth = (int) (childAt.getMeasuredWidth() + childLayoutParams.leftMargin + childLayoutParams.rightMargin + horizontalSpace);
            //child自身的高、包含了topmargin+bottommargin+verticalspace
            int childHeight = (int) (childAt.getMeasuredHeight() + childLayoutParams.topMargin + childLayoutParams.bottomMargin + verticalSpace);

            childAt.setTag(new Location(parentPaddingLeft, 0, parentPaddingRight, childHeight));

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == GONE) {
                return;
            }
            Location location = (Location) childAt.getTag();
            childAt.layout(location.left,location.top,location.right,location.bottom);
        }
    }

    /**
     * 记录子控件的坐标
     */
    public class Location {
        public Location(int left, int top, int right, int bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public int left;
        public int top;
        public int right;
        public int bottom;

    }
}
