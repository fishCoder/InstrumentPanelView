package com.fjwangjia.android.instrumentpanel;

/**
 * Created by flb on 16/3/1.
 */
public class Block {
    int iColor;
    float  fRate;
    String sText;
    public Block(int iColor, float fRate) {
        this.iColor = iColor;
        this.fRate = fRate;
    }

    public Block(int iColor, float fRate, String sText) {
        this.iColor = iColor;
        this.fRate = fRate;
        this.sText = sText;
    }

    String rateToString(float rate){
        return String.format("%.0f%%",rate*100);
    }


    @Override
    public String toString() {
        if(sText == null || sText.length() == 0){
            return rateToString(fRate);
        }
        return sText;
    }
}
