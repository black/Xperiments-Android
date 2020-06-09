package com.example.canvasexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {

    private List<Integer> xAxisPoints = new ArrayList<>();
    private int dataSize;
    private int zoom = 10;
    public GraphView(Context context) {
        super(context);
        init(null);
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    protected void init(@Nullable AttributeSet set){

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        polygon(canvas);
        invalidate();
    }
    public void setMaxData(int numbers){
        dataSize=numbers;
    }
    public void addData(int numbers){
        addDataToPoint(numbers);
    }

    private void polygon(Canvas canvas){
        Path polygon = new Path();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        for(int i=1;i<xAxisPoints.size();i++){
         Integer itemDataModel1 = xAxisPoints.get(i-1)+canvas.getHeight()/2;
         Integer itemDataModel2 = xAxisPoints.get(i)+canvas.getHeight()/2;
            polygon.moveTo((i-1)*zoom,itemDataModel1);
            polygon.lineTo(i*zoom,itemDataModel2);
            ellipse(canvas,i*zoom,itemDataModel2,3,Color.WHITE);
        }
        canvas.drawPath(polygon,paint);
    }

    // draw Circles
    private void ellipse(Canvas canvas, float x, float y, float r, int color) {
        Paint ellipse = new Paint();
        ellipse.setAntiAlias(true);
        ellipse.setStyle(Paint.Style.FILL);
        ellipse.setColor(color);
        RectF circle = new RectF(x - r, y - r, x + r, y + r);
        canvas.drawOval(circle, ellipse);
    }

    private void addDataToPoint(int point){
        if(xAxisPoints.size()<dataSize)xAxisPoints.add(point);
        else xAxisPoints.remove(0);
    }

}
