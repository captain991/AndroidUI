package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by captain on 2017/5/24.
 */

public class FilterView extends View {
    private Paint mPaint;

    public FilterView(Context context) {
        super(context);
        init();
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//        RectF rectF1 = new RectF(100, 100, 200, 200);
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));
//        canvas.drawRect(rectF1, mPaint);
//        canvas.translate(200, 0);
//
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL));
//        canvas.drawRect(rectF1, mPaint);
//        canvas.translate(0, 200);
//
//        RectF rectF2 = new RectF(100, 100, 200, 200);
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));
//        canvas.drawRect(rectF2, mPaint);
//        canvas.translate(200, 0);
//
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.SOLID));
//        canvas.drawRect(rectF2, mPaint);
//        canvas.translate(0, 200);
//
//        RectF rectF3 = new RectF(100, 100, 200, 200);
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.OUTER));
//        canvas.drawRect(rectF3, mPaint);
//        canvas.translate(200, 0);
//
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
//        canvas.drawRect(rectF3, mPaint);
//        canvas.translate(0, 200);
//
//        RectF rectF4 = new RectF(100, 100, 200, 200);
//        mPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.INNER));
//        canvas.drawRect(rectF4, mPaint);
//        canvas.translate(200, 0);
//
//        mPaint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.INNER));
//        canvas.drawRect(rectF4, mPaint);
        canvas.restoreToCount(layerId);
    }
}
