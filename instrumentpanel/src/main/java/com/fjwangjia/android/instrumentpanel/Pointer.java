package com.fjwangjia.android.instrumentpanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by flb on 16/2/22.
 */
public class Pointer implements Element {

    Point circlePoint = new Point(0,0);
    float mRadius;
    float mHeight;
    float mWidth;
    float mPointerLength;
    float originalRate = 0f;
    float totalOffset = 0;
    float currentRate = 0f;
    float countTime = 0;
    float mMaxRate = 1f;
    int mBorder;

    public Pointer(){

    }

    /**
     *
     * @param border border width  (px)
     * @param point  the center point of little circle
     * @param radius circle radius
     * @param length the longest height of triangle
     */
    public void init(int border,Point point,float radius,float length,float maxRate){
        circlePoint = point;
        mRadius = radius;
        mPointerLength = length;
        mWidth = 2 * radius;
        mHeight = length + radius;
        mBorder = border;
        mMaxRate = maxRate;
    }

    public void setCirclePoint(Point circlePoint) {
        this.circlePoint = circlePoint;
    }

    public Point getCirclePoint() {
        return circlePoint;
    }

    public void setCurrentRate(float currentRate) {
        this.currentRate = currentRate;
    }

    @Override
    public void layout(Rect rect) {

    }

    @Override
    public void draw(Canvas canvas) {
        drawTriangle(canvas);
        drawCircle(canvas);
    }

    void drawCircle(Canvas canvas){
        Paint paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(0xFFA1A1A1);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(mBorder);
        canvas.drawCircle(circlePoint.x, circlePoint.y, mRadius+mBorder, paintBorder);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        Shader shader = new RadialGradient(
                circlePoint.x,circlePoint.y,
                mRadius,new int[]{0xFFABABAB, 0xFFCECECE ,0xFFABABAB},
                null, Shader.TileMode.CLAMP
        );
        paint.setShader(shader);
        canvas.drawCircle(circlePoint.x, circlePoint.y, mRadius + mBorder / 2, paint);

    }

    void drawTriangle(Canvas canvas){
        float corner = currentRate/mMaxRate * 180;
        float sinValue = (float) Math.sin(Math.toRadians(corner));
        float cosValue = (float) Math.cos(Math.toRadians(corner));
        float endX = circlePoint.x - mPointerLength * cosValue;
        float endY = circlePoint.y - mPointerLength * sinValue;


        // triangle
        Path path = new Path();
        float offsetX = mRadius * sinValue;
        float offsetY = mRadius * cosValue;
        // calculate  two angle point
        float aX = circlePoint.x + offsetX;
        float aY = circlePoint.y - offsetY;
        float bX = circlePoint.x - offsetX;
        float bY = circlePoint.y + offsetY;
        path.moveTo(aX, aY);
        path.lineTo(bX,bY);
        path.lineTo(endX, endY);
        path.close();

        // fill
        Paint paint = new Paint();
        Shader mShader = new LinearGradient(
                aX,aY,
                bX,bY,
                new int[]{0xFFABABAB, Color.WHITE ,0xFFABABAB},
                null, Shader.TileMode.MIRROR);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(mShader);
        paint.setColor(0xFFABABAB);
        paint.setAntiAlias(true);
        canvas.drawPath(path, paint);
        //draw border
        Paint paintBorder = new Paint();
        paintBorder.setAntiAlias(true);
        paintBorder.setColor(0xFFA1A1A1);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setStrokeWidth(mBorder);
//        paintBorder.setMaskFilter(new BlurMaskFilter(mBorder/2, BlurMaskFilter.Blur.NORMAL));
        canvas.drawPath(path, paintBorder);

    }


    public boolean isInside(int x,int y){

        Point point = new Point(x,y);

        float corner = currentRate * 180;
        float sinValue = (float) Math.sin(Math.toRadians(corner));
        float cosValue = (float) Math.cos(Math.toRadians(corner));
        Point endPoint = new Point();
        endPoint.x = (int) (circlePoint.x - mPointerLength * cosValue);
        endPoint.y = (int) (circlePoint.y - mPointerLength * sinValue);

        int distance = (int) Utils.pointToLine(endPoint,circlePoint,point);

        return distance < mRadius*5;
    }



    public void dragPoint(Point point){
        float c = (float) Utils.distance(point,circlePoint);
        float  b = circlePoint.y - point.y;

//        float sinValue = circlePoint.x > point.x ?  b/c : -b/c;
        float sinValue = b/c;
        double degree = Math.toDegrees(Math.asin(sinValue));
        if(circlePoint.x < point.x){
            degree = 180 - degree;
        }
        currentRate = (float) (degree/180);
    }

    public void clearDamping(){
        if(timer != null){
            timer.cancel();
        }
    }

    Timer timer;

    public void dampingTo(Handler handler,float originalRate,float currentRate){
        this.originalRate = originalRate;
        this.currentRate = currentRate;
        dampingBack(handler);
    }

    public void dampingBack(final Handler handler){
        if(timer != null){
            timer.cancel();
        }
        countTime = 0;
        timer = new java.util.Timer(true);
        totalOffset = originalRate - currentRate;
        TimerTask task = new TimerTask() {
            public void run() {
                countTime += 0.1;
                currentRate = originalRate - Utils.dampingFunction(totalOffset,countTime);
                handler.sendEmptyMessage(0);

                if(Math.abs(currentRate - originalRate)<0.001){
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 40, 40);
    }
}
