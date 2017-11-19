package com.example.pszxcadmin.gesturedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by pszxcadmin on 06/11/2017.
 */

public class BaseGestureView extends View {

    private Matrix translate;
    private Bitmap imgB;
    private Matrix animateStart;
    private Interpolator animateInterpolator;
    private long startTime;
    private long endTime;
    private float totalAnimDx;
    private float totalAnimDy;

    public BaseGestureView(Context context) {
        super(context);
        translate = new Matrix();
        imgB = BitmapFactory.decodeResource(getResources(),
                R.drawable.cat);
    }

    public void onAnimateMove(float dx, float dy, long duration) {
        animateStart = new Matrix(translate);
        animateInterpolator = new OvershootInterpolator();
        startTime = System.currentTimeMillis();
        endTime = startTime + duration;
        totalAnimDx = dx;
        totalAnimDy = dy;
        post(new Runnable() {
            @Override
            public void run() {
                onAnimateStep();
            }
        });
    }

    private void onAnimateStep() {
        long curTime = System.currentTimeMillis();
        float percentTime = (float) (curTime - startTime)
                / (float) (endTime - startTime);
        float percentDistance = animateInterpolator
                .getInterpolation(percentTime);
        float curDx = percentDistance * totalAnimDx;
        float curDy = percentDistance * totalAnimDy;
        translate.set(animateStart);
        onMove(curDx, curDy);
        if (percentTime < 1.0f) {
            post(new Runnable() {
                @Override
                public void run() {
                    onAnimateStep();
                }
            });
        }
    }
    public void onMove(float dx, float dy){
        translate.postTranslate(dx,dy);
        invalidate();
    }
    public void onResetLocation(){
        translate.reset();
        invalidate();
    }
    public void onSetLocation(float dx, float dy){
        translate.postTranslate(dx,dy);
    }
    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(imgB,translate, null);
    }

}
