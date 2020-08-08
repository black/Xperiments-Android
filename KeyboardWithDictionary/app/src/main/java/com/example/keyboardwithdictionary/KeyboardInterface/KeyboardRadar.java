package com.example.keyboardwithdictionary.KeyboardInterface;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.keyboardwithdictionary.R;

import java.util.ArrayList;
import java.util.List;

public class KeyboardRadar extends View {
    private int level = 3;
    private int selectedInterface;
    private Context ctx;
    private float t,scan,move;
    private boolean pressed = false;
    private int width,height,fw,fh,safezone=64,step=0;
    private float cx,cy,tx,ty;
    private int black50;
    private boolean initialized=true;
    List<Box> boxes = new ArrayList<>();

//    private FusionViewModel fusionViewModel;
    private RecyclerView gridView;
    private RelativeLayout ParentView;

    public KeyboardRadar(Context context, View v, View ParentView) {
        super(context);
        black50 = getResources().getColor(R.color.colorPrimaryDark);
        this.ctx = context;
        cx = 100;
        cy = 100;
        this.gridView = (RecyclerView) v;
        this.gridView.setClickable(false);
        this.gridView.setFocusable(false);
        this.ParentView = (RelativeLayout) ParentView;
        SharedPreferences sharedPreferencesNuOS = getContext().getSharedPreferences("NUOS SETTINGS", Context.MODE_PRIVATE);
        scan = (float) sharedPreferencesNuOS.getInt("scan2",10)/4.0f;
        move = (float) sharedPreferencesNuOS.getInt("move2",10)/4.0f;
        ParentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed=true;
            }
        });

        tx = gridView.getWidth();
        ty = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(0, 0, 0, 0));
        //canvas.drawColor(Color.RED);
        width = getWidth();
        height = getHeight();
        fw = width;
        fh = height-safezone;
        if(initialized){
            setup(fw/10,fh/5);
            initialized = false;
        }
        drawRadar(canvas);
        highlight(cx,cy);
        switch (step){
            case 0:
                scanLine();
                break;
            case 1:
                moveLine();
                break;
            default:
                selectTile(cx,cy);
                step = 0;
                break;
        }
        if (pressed) {
            step++;
            pressed=false;
        }
        drawGrid(canvas);
        invalidate();
    }

    public void setup(int w,int h){
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < (j < 1 ? 5 : 10); i++) {
                int x = i * (j<1?w*2:w);
                int y = j * h;
                boxes.add(new Box(x, y, j<1?w*2:w, h));
            }
        }
    }

    // for debuggin purpose only
    public void highlight(float x,float y){
        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            if (box.x < x && x < box.x + box.w && box.y < y && y < box.y + box.h) {
                highlightItem(i,1);
            } else {
                highlightItem(i,0);
            }
        }
    }

    public void drawGrid(Canvas canvas){
        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            rect(canvas,box.x , box.y,box.w,  box.h);
        }
    }

    public void  scanLine(){
        float df = scan;
        if(ty<=0 && tx <= width) tx+=df;
        else if(tx>=width && ty <= height)ty+=df;
        else if(ty >= height && 0<=tx) tx -=df;
        else if(tx<=0 && 0<=ty) ty-=df;
    }

    public void moveLine(){
        float dx = tx - cx;
        float dy = ty - cy;
        float ang = (float) Math.atan2(dy,dx);

        /*setting safe zone boundary*/
        if(cx>=safezone/2 && cx <= width-safezone/2)cx = (float) (cx+move*Math.cos(ang));
        else if(cx<safezone/2)cx = (float) (safezone/2+move*Math.cos(ang));
        else if (cx>width-safezone/2) cx = (float) ((width-safezone/2)+move*Math.cos(ang));

        if(cy>=safezone/2 && cy <= height-safezone/2) cy = (float) (cy+move*Math.sin(ang));
        else if(cy<safezone/2)cy = (float) (safezone/2+move*Math.sin(ang));
        else if (cy>height-safezone/2) cy = (float) ((height-safezone/2)+move*Math.sin(ang));
    }

    // draw Rectangles
    public void rect(Canvas canvas, float x, float y, float w, float h) {
        Paint rectangle = new Paint();
        rectangle.setAntiAlias(true);
        rectangle.setStyle(Paint.Style.STROKE);
        rectangle.setColor(Color.BLACK);
        RectF _rect = new RectF(x, y, x + w, y + h);
        canvas.drawRect(_rect, rectangle);
    }

    // draw Circles
    public void ellipse(Canvas canvas, float x, float y, float r) {
        Paint ellipse = new Paint();
        ellipse.setAntiAlias(true);
        ellipse.setStyle(Paint.Style.FILL);
        ellipse.setColor(black50);
        RectF circle = new RectF(x - r, y - r, x + r, y + r);
        canvas.drawOval(circle, ellipse);
    }


    // draw Lines
    public void line(Canvas canvas, float x, float y, float xx, float yy) {
        Paint line = new Paint();
        line.setAntiAlias(true);
        line.setStrokeWidth(4);
        line.setStyle(Paint.Style.STROKE);
        line.setColor(black50);
        canvas.drawLine(x, y, xx, yy, line);
    }

    // for debuggin purpose only
    public void selectTile(float x,float y){
        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            if (box.x < x && x < box.x + box.w && box.y < y && y < box.y + box.h) {
                highlightItem(i,1);
                triggerItem(i);
            }
        }
    }

    private void drawRadar(Canvas canvas){
        ellipse(canvas,cx,cy,5);
        line(canvas,cx,cy,tx,ty);
    }

    public void triggerItem(int position) {
        if(gridView != null && gridView.getChildAt(position) != null) {
            gridView.getChildAt(position).setEnabled(true);
            gridView.findViewHolderForAdapterPosition(position).itemView.performClick();
        }
    }

    private void highlightItem(int position, int state) {
        if(gridView != null && gridView.getChildAt(position) != null) {
            gridView.getChildAt(position).setEnabled((state==1)?false:true);
            gridView.getChildAt(position).setPressed((state==1)?true:false);
        }
    }

    private class Box{
        public float x,y,w,h;
        public Box(float x,float y,float w,float h){
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }
    }

}