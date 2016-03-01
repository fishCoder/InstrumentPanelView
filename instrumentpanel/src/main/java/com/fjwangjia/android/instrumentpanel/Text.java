package com.fjwangjia.android.instrumentpanel;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by flb on 16/2/22.
 */
public class Text implements Element {

    float mTextSize = 0;
    String mContent;
    int mColor;
    Paint mPaint = new Paint();
    float mWidth;
    float mHeight;

    Rect drawRect;

    public Text(float textSize,String content,int color){
        mTextSize = textSize;
        mContent = content;
        mColor = color;
        measure();
    }

    void measure(){
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);
        Rect rect = new Rect();
        mPaint.getTextBounds(mContent, 0, mContent.length(), rect);
        mWidth = rect.width();
        mHeight = rect.height();
    }


    public float getHeight() {
        return mHeight;
    }

    public float getWidth() {
        return mWidth;
    }

    @Override
    public void layout(Rect rect) {
        drawRect = rect;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(mContent,drawRect.left,drawRect.bottom,mPaint);
    }
}
