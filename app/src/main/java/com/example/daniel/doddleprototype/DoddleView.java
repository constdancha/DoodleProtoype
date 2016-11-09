package com.example.daniel.doddleprototype;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by Daniel on 11/3/16.
 */

public class DoddleView extends View {

    ArrayList<myPaint> listofPaths = new ArrayList<myPaint>();
    Paint paint= new Paint();
    Path path = new Path();
    RelativeLayout rl = (RelativeLayout)findViewById(R.id.activity_main);

    public DoddleView(Context context){
        super(context);
        init(null,0);
    }

    public DoddleView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(attrs,0);
    }

    public DoddleView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs);
        init(attrs,defStyle);
    }

    private void init(AttributeSet attrs, int defStyle){

        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    public void setWidth(float f){
       paint.setStrokeWidth(f);
    }

    public void setColor(int c){
        paint.setColor(c);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(!listofPaths.isEmpty()){
            for(myPaint p:listofPaths){

                canvas.drawPath(p._path,p._paint);

            }
        }
        canvas.drawPath(path,paint);



    }



    public void clear(){
        listofPaths.clear();
        invalidate();


    }

    public void setBitmap(Bitmap bitmap){
        Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap,170,220,false);
        BitmapDrawable drawable = new BitmapDrawable(bitmap2);
        setBackground(drawable);
    }

    public void switchObjects(){
        paint = new Paint();
        Paint mostRecent = listofPaths.get(listofPaths.size()-1).get_paint();
        paint.setColor(mostRecent.getColor());
        paint.setAntiAlias(true);
        paint.setStyle(mostRecent.getStyle());
        paint.setStrokeWidth(mostRecent.getStrokeWidth());
        path = new Path();

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float touchX = motionEvent.getX();
        float touchY = motionEvent.getY();


        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                listofPaths.add(new myPaint(paint,path));
                switchObjects();
                break;


        }
        invalidate();
        return true;
    }

    public class myPaint{

        Paint _paint;
        Path _path;

        public myPaint(Paint paint, Path path){
            _paint=paint;
            _path=path;
        }

        public Path get_path() {
            return _path;
        }

        public void set_path(Path _path) {
            this._path = _path;
        }

        public Paint get_paint() {
            return _paint;
        }

        public void set_paint(Paint _paint) {
            this._paint = _paint;
        }


    }



}
