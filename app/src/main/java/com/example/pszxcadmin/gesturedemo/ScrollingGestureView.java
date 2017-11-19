package com.example.pszxcadmin.gesturedemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by pszxcadmin on 06/11/2017.
 */

public class ScrollingGestureView extends BaseGestureView {

    GestureDetector gestures;

    public ScrollingGestureView (Context context)
    {
        super(context);
        this.gestures = new GestureDetector(context, new ScrollGestureListener(this));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return gestures.onTouchEvent(event);
    }

    private class ScrollGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener  {

        ScrollingGestureView view;

        public ScrollGestureListener(ScrollingGestureView view) {
            this.view=view;
        }

        @Override
        public boolean onDown(MotionEvent e){
            Log.d("MDP", "onDown");
            return true;
        }
        @Override
        public boolean onFling(MotionEvent e1,MotionEvent e2, final float velocityX, final float velocityY){
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e){
            Log.d("MDP", "onDoubleTap");
            view.onResetLocation();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d("MDP", "onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.d("MDP", "onScroll");
            view.onMove(0, -distanceY);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d("MDP", "onShowPress");
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d("MDP", "onSingleTapUp");
            return false;
        }
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.d("MDP", "onDoubleTapEvent");
            return false;
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.d("MDP", "onSingleTapConfirmed");
            return false;
        }
    }
}
