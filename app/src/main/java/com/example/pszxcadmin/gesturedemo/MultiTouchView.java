package com.example.pszxcadmin.gesturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pszxcadmin on 08/11/2017.
 */

public class MultiTouchView extends View {

    Paint paint;
    SparseArray<PointF> pointers;

    Matrix translate;
    Bitmap face;

    float zoom=1;
    float rotation = 0;

    float angle=0;
    float radius = 0;
    float prevRadius;
    float prevAngle;

    public MultiTouchView(Context context) {
        super(context);
        initView();
    }

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MultiTouchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public void initView()
    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        pointers = new SparseArray<>();

        translate = new Matrix();
        face = BitmapFactory.decodeResource(getResources(),
                R.drawable.cat);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int pointerIndex = event.getActionIndex();
        int pointerId = event.getPointerId(pointerIndex);
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN: {

                PointF f = new PointF();
                f.x = event.getX(pointerIndex);
                f.y = event.getY(pointerIndex);
                pointers.put(pointerId, f);
                if(pointers.size()==2)
                {
                    parseEvent();
                    prevRadius = radius;
                    prevAngle = angle;
                    Log.d("MDP", "Current Angle"+angle);
                }
                Log.d("MDP", "pointer down");
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (int size = event.getPointerCount(), i = 0; i < size; i++) {
                    PointF point = pointers.get(event.getPointerId(i));
                    if (point != null) {
                        point.x = event.getX(i);
                        point.y = event.getY(i);
                    }
                }
                if(pointers.size()==2)
                {
                    parseEvent();
                    float deltaZoom = radius - prevRadius;
                    prevRadius = radius;
                    zoom += deltaZoom/500;
                    Log.d("MDP", "Zoom"+zoom);
                }
                Log.d("MDP", "Pointer move");
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {

                if(pointers.size()==2)
                {
                    float deltaAngle = angle-prevAngle;

                    rotation=deltaAngle;
                    //if(deltaAngle>20)
                    //    rotation+=90;
                    //else if(deltaAngle<-20)
                    //    rotation-=90;
                }

                pointers.remove(pointerId);

                break;
            }
        }
        invalidate();

        return true;

    }

    PointF centre = new PointF();

    protected void parseEvent() {

        PointF p1 = pointers.valueAt(0);
        PointF p2 = pointers.valueAt(1);

        radius = (float) Math.sqrt((Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2))) / 2;
        angle = (float) Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x));
        centre.set((p1.x + p2.x)/2, (p1.y + p2.y)/2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        Matrix transform = new Matrix();
        transform.setScale(zoom, zoom);
        transform.postRotate(rotation, zoom*face.getWidth()/2, zoom*face.getHeight()/2);
        transform.postTranslate(this.getWidth()/2-(zoom*face.getWidth()/2),this.getHeight()/2-(zoom*face.getHeight()/2));

        canvas.drawBitmap(face, transform, null);

        paint.setARGB(255,0,0,255);

        for (int size = pointers.size(), i = 0; i < size; i++) {
            PointF point = pointers.valueAt(i);
            if (point != null)
                canvas.drawCircle(point.x, point.y, 150, paint);
        }

        if(pointers.size()==2)
        {
            paint.setARGB(100,0,0,255);

            PointF p1 = pointers.valueAt(0);
            PointF p2 = pointers.valueAt(1);

            canvas.drawCircle(centre.x, centre.y, radius, paint);

            paint.setARGB(255,150,150,150);
            paint.setStrokeWidth(20);

            canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);
            canvas.drawLine(p1.x, p1.y, p1.x, p2.y, paint);
            canvas.drawLine(p1.x, p2.y, p2.x, p2.y, paint);

            paint.setARGB(255,200,200,200);
            paint.setStrokeWidth(5);
            paint.setTextSize(100);
            canvas.drawText(String.format("r:%.0f a:%.0f", radius, angle), centre.x, centre.y, paint);
        }
    }
}

