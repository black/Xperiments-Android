package com.example.yesnogame;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

@SuppressLint("ViewConstructor")
public class BasicRadar extends View{
    private Context ctx;
    private int d, l,blinkRadius=0;
    private float t, speed;
    private boolean pressed = false;
    private RelativeLayout gridLayout;
    private String[] buttonLables = {"yes","no","maybe","prev"};
 /*   private FusionViewModel fusionSliderViewModel;*/
    public BasicRadar(Context context, View v, View ParentView) {
        super(context);
        this.ctx = context;
        this.gridLayout = (RelativeLayout) v;
        RelativeLayout parentView = (RelativeLayout) ParentView;
        d = parentView.getWidth() / 2;
        l = d/2;
        t = 180;
        parentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                pressed = true;
                Log.d("Click","Pressed");
            }
        });
        SharedPreferences sharedPreferencesNuOS = getContext().getSharedPreferences("NUOS SETTINGS", Context.MODE_PRIVATE);
        speed = (float) sharedPreferencesNuOS.getInt("scan",1)/4.0f;

/*
        fusionSliderViewModel = ViewModelProviders.of((FragmentActivity) ctx).get(FusionViewModel.class);
        fusionSliderViewModel.getScan().observe((LifecycleOwner) ctx, new Observer<Float>() {
            @Override
            public void onChanged(@Nullable Float val) {
               speed = val;
            }
        });
        fusionSliderViewModel.getTrigger().observe((LifecycleOwner) ctx, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean bool) {
                pressed=bool;
            }
        });*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.argb(0, 0, 0, 0));

        //get the position of the tip
        float x = (float) (d + l * Math.cos(Math.toRadians(t)));
        float y = (float) (d + l * Math.sin(Math.toRadians(t)));

        // draw pointer
        drawScanner(canvas, x, y);

        //get distance from the each section
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int index = i + j * 2;
                float dis = (float) Math.sqrt(Math.pow(x - (i * d + d * 0.5f), 2) + Math.pow(y - (j * d + d * 0.5f), 2));
                // draw grid
                // drawGrid(canvas, i * d, j * d, index, dis);
                if (dis < d/2) {
                    highlightItem(index, 1);
                    if (pressed) {
                        pressed = false;
                        triggerItem(index);
                    }
                } else {
                    highlightItem(index, 0);
                }
            }
        }
        t += speed;
        invalidate();
    }

    // draw Rectangles
    public void rect(Canvas canvas, float x, float y, float w, float h) {
        Paint rectangle = new Paint();
        rectangle.setAntiAlias(true);
        rectangle.setStyle(Paint.Style.STROKE);
        rectangle.setColor(Color.GRAY);
        RectF _rect = new RectF(x, y, x + w, y + h);
        canvas.drawRect(_rect, rectangle);
    }

    // draw Circles
    public void ellipse(Canvas canvas, float x, float y, float r, int color) {
        Paint ellipse = new Paint();
        ellipse.setAntiAlias(true);
        ellipse.setStyle(Paint.Style.FILL);
        ellipse.setColor(color);
        RectF circle = new RectF(x - r, y - r, x + r, y + r);
        canvas.drawOval(circle, ellipse);
    }

    // draw Lines
    public void line(Canvas canvas, float x, float y, float xx, float yy) {
        Paint line = new Paint();
        line.setAntiAlias(true);
        line.setStrokeWidth(5);
        line.setStyle(Paint.Style.STROKE);
        line.setColor(Color.WHITE);
        canvas.drawLine(x, y, xx, yy, line);
    }

    // draw text
    public void label(Canvas canvas, float x, float y, String msg) {
        Paint label = new Paint();
        label.setAntiAlias(true);
        label.setColor(Color.BLACK);
        label.setTextSize(20);
        canvas.drawText(msg, x, y, label);
    }

    public void drawScanner(Canvas canvas, float x, float y) {
        // Paint line
        line(canvas, d, d, x, y); // body
        ellipse(canvas, d, d, 4,Color.WHITE); // tip
        ellipse(canvas, x, y, 6,Color.WHITE); // tail
    }

    public void triggerItem(int position) {
        int layoutID = getResources().getIdentifier(buttonLables[position], "id", ctx.getPackageName());
        ((LinearLayout)gridLayout.findViewById(layoutID)).callOnClick();
        t = 180;
    }

    private void highlightItem(int position, int state) {
        int layoutID = getResources().getIdentifier(buttonLables[position], "id", ctx.getPackageName());
        ((LinearLayout)gridLayout.findViewById(layoutID)).setBackgroundResource((state == 1) ? R.drawable.highlightbg : R.drawable.roundcorner);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

}