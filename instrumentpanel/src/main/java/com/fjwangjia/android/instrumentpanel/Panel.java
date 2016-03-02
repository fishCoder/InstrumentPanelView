package com.fjwangjia.android.instrumentpanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;


/**
 * Created by flb on 16/2/22.
 */
public class Panel implements Element {

    float mPreRate;
    float mRate;

    int mColor;

    float shortRadius;
    float longRadius;

    int mHeight;
    int mWidth;

    Point circlePoint = new Point();

    Rect drawRect = new Rect();

    public Panel(float preRare,float rate,int color){
        mColor = color;
        mPreRate = preRare;
        mRate = rate;
    }

    public Point getCirclePoint() {
        return circlePoint;
    }

    public float getLongRadius() {
        return longRadius;
    }

    @Override
    public void layout(Rect rect) {
        drawRect = rect;

        mWidth =  rect.right - rect.left;
        mHeight = rect.bottom - rect.top;

        if(mHeight > mWidth/2){
            longRadius = mWidth/2;
        }else {
            longRadius = mHeight;
        }
        shortRadius = (7*longRadius)/10;
        circlePoint.x = rect.left + mWidth/2;
        circlePoint.y = rect.bottom;

    }

    @Override
    public void draw(Canvas canvas) {
        RectF rect = new RectF();
        float paintWidth = longRadius - shortRadius;
        float radius = longRadius - paintWidth/2;
        rect.left = circlePoint.x - radius;
        rect.right = circlePoint.x + radius;
        rect.top =  circlePoint.y - radius;
        rect.bottom = circlePoint.y + radius;

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintWidth);

        Shader mRadialGradient = new RadialGradient(
                circlePoint.x,
                circlePoint.y,
                longRadius,
                new int[]{mColor, mColor-0xBB000000, mColor},
                new float[]{0.7f,0.75f,1f},
                Shader.TileMode.CLAMP);
        paint.setShader(mRadialGradient);

        Paint backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(paintWidth);
        canvas.drawArc(rect, 180 * (1 + mPreRate), (mRate - mPreRate) * 180, false, backgroundPaint);
        canvas.drawArc(rect, 180 * (1 + mPreRate), (mRate - mPreRate) * 180, false, paint);

        //draw divier

        if(mPreRate == 0){
            drawDivider(canvas, mPreRate,shortRadius,longRadius);
        }
        drawDivider(canvas,mRate,shortRadius,longRadius);

        //draw  degree scale
        for (float i=0;(i*0.05f+mPreRate)<mRate;i++){
            float degreeLength = paintWidth/10;
            if(i%4==0){
                degreeLength = paintWidth/5;
            }
            drawDivider(canvas,(i*0.05f+mPreRate),longRadius-degreeLength,longRadius);
        }
    }


    void drawDivider(Canvas canvas,float rate,float shortRadius,float longRadius){
        float coner = rate * 180;
        float sinValue = (float) Math.sin(Math.toRadians(coner));
        float cosValue = (float) Math.cos(Math.toRadians(coner));
        float startX = circlePoint.x -  shortRadius * cosValue;
        float startY = circlePoint.y -  shortRadius * sinValue ;
        float endX = circlePoint.x -  longRadius * cosValue ;
        float endY = circlePoint.y -  longRadius * sinValue ;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        canvas.drawLine(startX,startY,endX,endY,paint);
    }
}
