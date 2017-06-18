package com.example.captain.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.example.captain.customview.adapter.CustomViewAdapter;
import com.example.captain.customview.bean.CustomView;
import com.example.captain.customview.widget.DragBubbleView;
import com.example.captain.customview.widget.FilterView;
import com.example.captain.customview.widget.LinearGradientTextView;
import com.skyfishjy.library.RippleBackground;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomViewAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private List<CustomView> customViews = new ArrayList<>();
    private CustomViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ((RippleBackground) findViewById(R.id.ripple)).startRippleAnimation();
        initView();
        initData();
    }

    public void initView() {
//        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        DragBubbleView dragBubbleView = new DragBubbleView(this);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        getWindow().addContentView(dragBubbleView, layoutParams);
    }

    public void initData() {
//        customViews.add(new CustomView("可拖拽的小红点", DragBubbleView.class));
//        customViews.add(new CustomView("FilterView", FilterView.class));
//        customViews.add(new CustomView("线性渲染TextView", LinearGradientTextView.class));
//        adapter = new CustomViewAdapter(customViews, R.layout.recycler_view_item);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(int position, CustomView customView) {
//        DisplayActivity.launchActivity(this, customView.cls);
    }
}
