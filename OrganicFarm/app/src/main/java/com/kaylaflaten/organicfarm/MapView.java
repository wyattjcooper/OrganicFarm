package com.kaylaflaten.organicfarm; /**
 * Created by Carmen on 2/17/2016.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class MapView extends View{
    public MapView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect[] sections = new Rect[getResources().getStringArray(R.array.sectionList).length];
        System.out.println(getResources().getStringArray(R.array.sectionList).length);
        //Rect ourRect = new Rect();
        //ourRect.set(0, 0, canvas.getWidth(), canvas.getHeight() / 2);

        //sections[0] = new Rect();
        //sections[0].set(0, 0, canvas.getWidth()/2, canvas.getHeight()/2);

        Paint blue = new Paint();
        blue.setColor(Color.BLUE);
        blue.setStyle(Paint.Style.FILL);

        //canvas.drawRect(ourRect, blue);

        //canvas.drawRect(sections[0], blue);
//        int x = 0;
//        for(int i = 0; i < 2; i++){
//            sections[i] = new Rect();
//            sections[i].set(x, 0, canvas.getWidth() / 10, canvas.getHeight() / 10);
//            canvas.drawRect(sections[i], blue);
//            x += canvas.getWidth() / 10 + 20;
//        }
        sections[0]= new Rect();
        //sections[1]= new Rect();
        sections[0].set(0, 0, 50, 50);
        //sections[1].set(100, 100, 50, 50);
        canvas.drawRect(sections[0], blue);
        //canvas.drawRect(sections[1], blue);
    }
}
