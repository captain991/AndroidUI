package com.example.captain.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.captain.customview.R;

/**
 * Created by captain on 2017/6/9.
 */

public class WaveView extends View implements View.OnClickListener {
    private Paint mPaint = new Paint();
    private Path mWavePath = new Path();
    private int centerY;
    private int offset = 0;
    private int waveLength;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint.setColor(ContextCompat.getColor(context, R.color.seaBlue));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWavePath.reset();
        mWavePath.moveTo(-waveLength + offset, centerY);
        for (int i = 0; i < 3; i++) {
            mWavePath.quadTo(-waveLength * 3 / 4 + offset + i * waveLength, centerY + 60,
                    -waveLength / 2 + offset + i * waveLength, centerY);
            mWavePath.quadTo(-waveLength / 4 + offset + i * waveLength, centerY - 60,
                    offset + i * waveLength, centerY);
        }
        mWavePath.lineTo(getWidth(), getHeight());
        mWavePath.lineTo(0, getHeight());
        mWavePath.close();
        canvas.drawPath(mWavePath, mPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        waveLength = w / 2;
        centerY = h;
    }

    @Override
    public void onClick(View v) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, waveLength);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();

        ValueAnimator valueAnimator1 = ValueAnimator.ofInt(getHeight(), 0);
        valueAnimator1.setDuration(10000);
        valueAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator1.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                centerY = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator1.start();
    }
}
