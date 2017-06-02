package com.example.captain.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skyfishjy.library.RippleBackground;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ((RippleBackground) findViewById(R.id.ripple)).startRippleAnimation();
    }
}
