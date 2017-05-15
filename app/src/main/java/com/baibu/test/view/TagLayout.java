package com.baibu.test.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.baibu.test.R;

/**
 * Created by minna_Zhou on 2017/5/12.
 * 标签云
 * <p/>
 * <p/>
 * <com.whoislcj.views.TagsLayout
 * android:id="@+id/image_layout"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:layout_margin="10dp"
 * lee:tagHorizontalSpace="10dp"
 * lee:tagVerticalSpace="10dp" />
 * <p/>
 * <p/>
 * TagsLayout imageViewGroup = (TagsLayout) findViewById(R.id.image_layout);
 * ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
 * String[] string={"从我写代码那天起，我就没有打算写代码","从我写代码那天起","我就没有打算写代码","没打算","写代码"};
 * for (int i = 0; i < 5; i++) {
 * TextView textView = new TextView(this);
 * textView.setText(string[i]);
 * textView.setTextColor(Color.WHITE);
 * textView.setBackgroundResource(R.drawable.round_square_blue);
 * imageViewGroup.addView(textView, lp);
 * }
 */
public class TagLayout extends ViewGroup {
    private int childHorizontalSpace;
    private int childVerticalSpace;

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.TagsLayout);
        if (attrArray != null) {
            childHorizontalSpace = attrArray.getDimensionPixelSize(R.styleable.TagsLayout_tagHorizontalSpace, 0);
            childVerticalSpace = attrArray.getDimensionPixelSize(R.styleable.TagsLayout_tagVerticalSpace, 0);
            attrArray.recycle();
        }
    }

    /**
     * 负责设置子控件的测量模式和大小 根据所有子控件设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获得它的父容器为它设置的测量模式和大小
        int parentSizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentSizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int parentModeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int parentModeHeight = MeasureSpec.getMode(heightMeasureSpec);
        // 如果是warp_content情况下，记录宽和高
        int wrapWidth = 0;
        int wrapHeight = 0;
        /**
         * 记录每一行的宽度，width不断取最大宽度
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height
         */
        int lineHeight = 0;

        int count = getChildCount();
        int parentPaddingLeft = getPaddingLeft();
        int parentPaddingTop = getPaddingTop();
        // 遍历每个子元素
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 得到child的lp
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            // 当前子空间实际占据的宽度
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin + childHorizontalSpace;
            // 当前子空间实际占据的高度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin + childVerticalSpace;
            /**
             * 如果加入当前child，则超出最大宽度，则的到目前最大宽度给width，累加height 然后开启新行
             */
            if (lineWidth + childWidth > parentSizeWidth - getPaddingLeft() - getPaddingRight()) {
                wrapWidth = Math.max(lineWidth, childWidth);// 取最大的
                lineWidth = childWidth; // 重新开启新行，开始记录
                // 叠加当前高度，
                wrapHeight += lineHeight;
                // 开启记录下一行的高度
                lineHeight = childHeight;
                child.setTag(new Location(parentPaddingLeft, parentPaddingTop + wrapHeight, childWidth + parentPaddingLeft - childHorizontalSpace, wrapHeight + child.getMeasuredHeight() + parentPaddingTop));
            } else {// 否则累加值lineWidth,lineHeight取最大高度
                child.setTag(new Location(lineWidth + parentPaddingLeft, parentPaddingTop + wrapHeight, lineWidth + childWidth - childHorizontalSpace + parentPaddingLeft, wrapHeight + child.getMeasuredHeight() + parentPaddingTop));
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
        }
        wrapWidth = Math.max(wrapWidth, lineWidth) + getPaddingLeft() + getPaddingRight();
        wrapHeight += lineHeight;
        parentSizeHeight += getPaddingTop() + getPaddingBottom();
        wrapHeight += getPaddingTop() + getPaddingBottom();
        //EXACTLY父容器已经为子容器设置了尺寸,子容器应当服从这些边界,不论子容器想要多大的空间.
        setMeasuredDimension((parentModeWidth == MeasureSpec.EXACTLY) ? parentSizeWidth : wrapWidth, (parentModeHeight == MeasureSpec.EXACTLY) ? parentSizeHeight : wrapHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE)
                continue;
            Location location = (Location) child.getTag();
            child.layout(location.left, location.top, location.right, location.bottom);
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
