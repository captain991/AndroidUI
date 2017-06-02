package com.example.captain.customview.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.captain.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 2017/5/25.
 * 希望一个控件实现全部波纹的效果
 */

public class RippleView extends View {
    private Bitmap mBitmapDst;
    private Bitmap mBitmapMask;
    private int mCircleCount = 1;
    private int DURATION = 3000;
    private int mColor;
    private List<Paint> paints = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();
    private List<Animator> animators = new ArrayList<>();

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
//        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mBitmapMask = BitmapFactory.decodeResource(getResources(), R.drawable.transparent_mask);
//        mColor = Color.parseColor("#2E9AFE");
        mColor = Color.RED;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBitmapDst = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        Circle.MAX_RADIUS = Math.min(w, h) / 2 - 100;

//        AnimatorSet animatorSet = new AnimatorSet();
        circles.clear();
        paints.clear();
        for (int i = 0; i < mCircleCount; i++) {
            final Circle circle = new Circle();
            circles.add(circle);

            final Paint paint = new Paint();
            paint.setColor(mColor);
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paints.add(paint);

            ValueAnimator mAnimator = ValueAnimator.ofInt(Circle.MIN_RADIUS, Circle.MAX_RADIUS);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    circle.radius = (int) animation.getAnimatedValue();
                    circle.alpha = (int) ((1.4 - (float) circle.radius / Circle.MAX_RADIUS) * 255);
                    Log.e("captain", "circle.radius:" + circle.radius + ",circle.alpha:" + circle.alpha);
                    paint.setAlpha(circle.alpha);
                    invalidate();
                }
            });
            mAnimator.setDuration(DURATION);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.setStartDelay(i * 800);
            mAnimator.start();
            animators.add(mAnimator);
        }


//        mPaint.setShader(new RadialGradient(getWidth() / 2, getHeight() / 2, currentRadius,
//                new int[]{Color.TRANSPARENT, Color.parseColor("#2E9AFE")},
//                new float[]{0.2f, 1}, Shader.TileMode.CLAMP));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCircleCount; i++) {
            Circle circle = circles.get(i);
            Paint mPaint = paints.get(i);
            int count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
            float scale = (float) circle.radius / (mBitmapMask.getWidth() / 2);
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            mBitmapDst.eraseColor(Color.TRANSPARENT);
            Canvas c = new Canvas(mBitmapDst);
            c.drawBitmap(mBitmapMask, matrix, mPaint);

            canvas.drawBitmap(mBitmapDst, getWidth() / 2 - circle.radius, getHeight() / 2 - circle.radius, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, circle.radius, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(count);
        }
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, currentRadius - 20, mPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, currentRadius - 40, mPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, currentRadius - 60, mPaint);
    }

    private static class Circle {
        private int radius;
        private int alpha;
        private static int MIN_RADIUS = 100;
        private static int MAX_RADIUS = 500;
    }

}
