package com.example.pszxcadmin.gesturedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class GestureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture);

        FrameLayout frameA= (FrameLayout) findViewById(R.id.frameA);
        FrameLayout frameB= (FrameLayout) findViewById(R.id.frameB);

        ScrollingGestureView sV= new ScrollingGestureView(this);
        FlingGestureView fV= new FlingGestureView(this);
        frameA.addView(sV);
        frameB.addView(fV);

    }
}
