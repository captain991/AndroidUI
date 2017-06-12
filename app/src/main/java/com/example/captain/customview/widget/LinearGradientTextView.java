package com.example.captain.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

/**
 * Created by captain on 2017/6/6.
 * 线性渲染效果的TextView
 */

public class LinearGradientTextView extends AppCompatTextView {
    private Paint mPaint;
    private Matrix matrix = new Matrix();
    private float textWidth;
    private ValueAnimator valueAnimator;
    LinearGradient linearGradient;

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = getPaint();
        textWidth = mPaint.measureText(getText().toString());
        linearGradient = new LinearGradient(0, 0, textWidth, 0,
                new int[]{0x33ffffff, 0xffffffff, 0x33ffffff}, new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        valueAnimator = ValueAnimator.ofFloat(-textWidth, textWidth);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(800);
        valueAnimator.start();
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                matrix.setTranslate((float) animation.getAnimatedValue(), 0);
                linearGradient.setLocalMatrix(matrix);
                invalidate();
            }
        });
    }

}
