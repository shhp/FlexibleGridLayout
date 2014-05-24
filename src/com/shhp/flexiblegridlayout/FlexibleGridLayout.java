/*
 * Copyright 2014 shhp

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.shhp.flexiblegridlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * FlexibleGridLayout is a kind of grid layout that can automatically arrange the order
 * of its child views. The child views need not to have identical width. When a child view 
 * is added to the layout, FlexibleGridLayout will put the view in the first suitable row which 
 * has enough space to hold the view. Once a child view is removed, FlexibleGridLayout will rearrange
 * the remaining child views.
 * 
 * @author shhp
 *
 */
public class FlexibleGridLayout extends RelativeLayout {
    
    /**
     * The listener will be notified when FlexibleGridLayout finish its onLayout() procedure.
     * @author shhp
     *
     */
    public static interface OnLayoutListener {
        public void onFinishLayout();
    }

    private static final String TAG = "FlexibleGridLayout";
    
    private int mWidth;
    private int mCurrentRow;
    private SparseIntArray mRowRemainingWidth;
    private SparseIntArray mRowColumnNumber;
    private OnLayoutListener mLayoutListener;
    private int mHorizontalMargin, mVerticalMargin;
    
    public FlexibleGridLayout(Context context) {
        super(context);
        init();
    }
    
    public FlexibleGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public void setOnLayoutListener(OnLayoutListener listener) {
        this.mLayoutListener = listener;
    }
    
    /**
     * Set the horizontal space among child views.
     * @param margin  
     */
    public void setHorizontalMargin(int margin) {
        this.mHorizontalMargin = margin;
    }
    
    /**
     * Set the vertical space among child views.
     * @param margin
     */
    public void setVerticalMargin(int margin) {
        this.mVerticalMargin = margin;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }
    
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (this.mLayoutListener != null) {
            this.mLayoutListener.onFinishLayout();
        }
    }
    
    /**
     * Remove all child views from the layout.
     */
    public void clear() {
        mCurrentRow = -1;
        mRowRemainingWidth.clear();
        mRowColumnNumber.clear();
        this.removeAllViews();
    }
    
    /**
     * Add a view to the layout. FlexibleGridLayout will put the view in the first suitable row which 
     * has enough space to hold the view.
     * @param view
     */
    public void addAView(View view) {
        if (view == null)
            throw new IllegalArgumentException("The view to be added is null!");
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        Log.i(TAG, "mWidth:"+this.mWidth +",view width:"+view.getMeasuredWidth());
        
        int row = findFitRow(view.getMeasuredWidth() + mHorizontalMargin);
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (mRowColumnNumber.indexOfKey(row) < 0) {
            if (row > 0) {
                lp.addRule(RelativeLayout.BELOW, Integer.valueOf((row-1)+""+1));
                lp.topMargin = this.mVerticalMargin;
            }
        mRowColumnNumber.put(row, 1);
        ++mCurrentRow;
        view.setId(Integer.valueOf(row+""+1));
        } else {
            int column = mRowColumnNumber.get(row);
            int siblingId = Integer.valueOf(row+""+column);
            lp.addRule(RelativeLayout.RIGHT_OF, siblingId);
            lp.addRule(RelativeLayout.ALIGN_TOP, siblingId);
            lp.leftMargin = this.mHorizontalMargin;
            mRowColumnNumber.put(row, column+1);
            view.setId(Integer.valueOf(row+""+(column+1)));
        }
        int remainingWidth = mRowRemainingWidth.indexOfKey(row) < 0 ? mWidth : mRowRemainingWidth.get(row)-mHorizontalMargin;
        mRowRemainingWidth.put(row, remainingWidth - view.getMeasuredWidth());
        addView(view, lp);
    }
    
    /**
     * Remove the view from the layout. FlexibleGridLayout will rearrange
     * the remaining child views.
     * @param view
     */
    public void removeAView(View view) {
        if (view != null) {
            View[] views = new View[this.getChildCount()];
            for (int i = 0; i < this.getChildCount(); i++)
                views[i] = this.getChildAt(i);
            this.removeAllViews();
            this.init();
            for (View child : views) {
                if (child != view) {
                    this.addAView(child);
                }
            }
        }
    }
    
    private void init() {
        mCurrentRow = -1;
        mRowRemainingWidth = new SparseIntArray();
        mRowColumnNumber = new SparseIntArray();
    }
    
    private int findFitRow(int viewWidth) {
        int i;
        for (i = 0; i <= mCurrentRow; i++) {
            if (mRowRemainingWidth.get(i) >= viewWidth) {
                break;
        }
        }
        return i;
    }

}
