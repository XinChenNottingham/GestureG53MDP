package com.example.pszxcadmin.gesturedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickST(View v){
        Intent intent = new Intent(MainActivity.this, GestureActivity.class);
        startActivity(intent);
    }

    public void onClickMT(View v){
        Intent intent = new Intent(MainActivity.this, MultiTouchActivity.class);
        startActivity(intent);
    }

}

