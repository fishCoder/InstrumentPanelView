package com.fjwangjia.android.instrumentpanel;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

/**
 * Created by flb on 16/2/23.
 */
public class Utils {
    public  static int  dpTopx(Context context,float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static double distance(Point p, Point p1) {
        return Math.hypot(p.x-p1.x, p.y-p1.y);
    }
    //点到线段的最短距离,x0,y0是圆心
    public static double pointToLine(Point p1,Point p2, Point p) {
        double ans = 0;
        double a, b, c;
        a = distance(p1, p2);
        b = distance(p1, p);
        c = distance(p2, p);
        if (c+b==a) {//点在线段上
            ans = 0;
            return ans;
        }
        if (a<=0.00001) {//不是线段，是一个点
            ans = b;
            return ans;
        }
        if (c*c >= a*a + b*b) { //组成直角三角形或钝角三角形，p1为直角或钝角
            ans = b;
            return ans;
        }
        if (b * b >= a * a + c * c) {// 组成直角三角形或钝角三角形，p2为直角或钝角
            ans = c;
            return ans;
        }
        // 组成锐角三角形，则求三角形的高
        double p0 = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p0 * (p0 - a) * (p0 - b) * (p0 - c));// 海伦公式求面积
        ans = 2*s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return ans;
    }

    /**
     * 阻尼震荡方程式
     * @param startOffset  起始的位移量
     * @param time         时间
     * @return 相对位移量
     */
    public static float dampingFunction(float startOffset,float time){
        time = time/10;
        //阻尼系数系数
        float q = 50;
        //角动量
        float w0 = 360;
        float w = (float) Math.sqrt(q*q+w0*w0);
        float relativeOffset = (float) (startOffset * Math.exp(-q*time)*Math.cos(Math.toDegrees(w*time)));
        Log.d("relativeOffset",""+relativeOffset);
        return relativeOffset;
    }

    // 根据圆心 半径 及角度算出在所指向圆上的点
    public static Point getCircleSide(Point circlePoint,float radius,float currentRate){
        float corner = currentRate * 180;
        float sinValue = (float) Math.sin(Math.toRadians(corner));
        float cosValue = (float) Math.cos(Math.toRadians(corner));
        float endX = circlePoint.x - radius * cosValue;
        float endY = circlePoint.y - radius * sinValue;

        return new Point((int)endX,(int)endY);
    }
}
