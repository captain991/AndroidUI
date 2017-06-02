package com.example.captain.customview.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 2017/5/26.
 */

public class RippleLayout extends RelativeLayout {
    private Paint mPaint = new Paint();
    private int minRadius = 100;
    private int maxRadius;
    private int mRippleCount = 6;
    private int mDuration = 3000;

    public RippleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void init() {


        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        maxRadius = Math.min(getWidth(), getHeight()) / 2;
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        for (int i = 0; i < mRippleCount; i++) {
            CircleView circleView = new CircleView(getContext());
            LayoutParams layoutParams = new LayoutParams(minRadius * 2, minRadius * 2);
            layoutParams.addRule(CENTER_IN_PARENT, TRUE);
            this.addView(circleView, layoutParams);


            ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(circleView, "ScaleX", (float) maxRadius / minRadius);
            scaleXAnimator.setStartDelay(i * 500);
            scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ValueAnimator.RESTART);
//            scaleXAnimator.setDuration(mDuration);
//            scaleXAnimator.start();
            animators.add(scaleXAnimator);

            ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(circleView, "ScaleY", (float) maxRadius / minRadius);
            scaleYAnimator.setStartDelay(i * 500);
            scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ValueAnimator.RESTART);
//            scaleYAnimator.setDuration(mDuration);
//            scaleYAnimator.start();
            animators.add(scaleYAnimator);

            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(circleView, "Alpha", 1f, 0f);
            alphaAnimator.setStartDelay(i * 500);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ValueAnimator.RESTART);
//            alphaAnimator.setDuration(mDuration);
//            alphaAnimator.start();
            animators.add(alphaAnimator);
        }
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(mDuration).playTogether(animators);
        animatorSet.start();
    }

    private class CircleView extends View {
        public CircleView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Log.e("captain", "width:" + getWidth() + ",height:" + getHeight());
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, mPaint);
        }
    }
}
