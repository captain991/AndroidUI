package com.example.captain.customview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {
    public static final String INTENT_KEY_WIDGET_CLASS = "WIDGET_CLASS";
    private Class<?> widgetCls;

    public static void launchActivity(Context context, Class<?> widgetCls) {
        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra(INTENT_KEY_WIDGET_CLASS, widgetCls);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        initData();
    }

    public void initData() {
        widgetCls = (Class<?>) getIntent().getSerializableExtra(INTENT_KEY_WIDGET_CLASS);
        View widget = null;
        try {
            widget = (View) widgetCls.newInstance();
        } catch (Exception ex) {
            Toast.makeText(this, " Class newInstance error", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
            return;
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().addContentView(widget, layoutParams);
    }
}
