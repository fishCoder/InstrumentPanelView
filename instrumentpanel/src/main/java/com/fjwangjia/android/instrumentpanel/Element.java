package com.fjwangjia.android.instrumentpanel;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by flb on 16/2/22.
 */
public interface Element {
    void layout(Rect rect);
    void draw(Canvas canvas);
}
