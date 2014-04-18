package com.vicmns.rssreader.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {
	SquareImageInterface squareImageInterface;
	int gridItemIdx;
	
	public SquareImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setListener(SquareImageInterface squareImageInterface) {
		this.squareImageInterface = squareImageInterface;
	}
	
	public void setGridItemIdx(int gridItemIdx) {
		this.gridItemIdx = gridItemIdx;
	}

	public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
        if(squareImageInterface != null)
        	squareImageInterface.afterMeasure(gridItemIdx, getMeasuredWidth(), getMeasuredWidth());
    }
    
    public interface SquareImageInterface {
    	public void afterMeasure(int gridItemIdx, int width, int height);
    }
}
