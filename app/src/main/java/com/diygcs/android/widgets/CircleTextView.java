package com.diygcs.android.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Gui Zhou on 2016-05-05.
 */
public class CircleTextView extends TextView {

    private Paint mBgPaint = new Paint();
    private PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,
            Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);

    public CircleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }

    public CircleTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();
        int max = Math.max(measureWidth, measureHeight);
        setMeasuredDimension(max, max);
    }

    @Override
    public void setBackgroundColor(int color) {
        // TODO Auto-generated method stub
        mBgPaint.setColor(color);
    }

    /**
     *
     * @param
     */
    public void setNotifiText(int text) {
        setText(text + "");
    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Somthing else
        canvas.setDrawFilter(pfd);
        canvas.drawCircle(getWidth()/2, getHeight()/2,
                Math.max(getWidth(), getHeight())/2, mBgPaint);
        super.draw(canvas);
    }
}
