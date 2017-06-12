package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.captain.customview.R;

/**
 * Created by captain on 2017/5/23.
 */

public class ReflectionView extends View {
    private Paint mPaint = new Paint();
    private Bitmap mBitmapSrc;
    private Bitmap mBitmapShade;
    private Bitmap mBitmapReverse;

    public ReflectionView(Context context) {
        this(context, null);
    }


    public ReflectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);
        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.xyjy6);
        mBitmapShade = BitmapFactory.decodeResource(getResources(), R.drawable.reflection_shade);
        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);
        mBitmapReverse = Bitmap.createBitmap(mBitmapSrc, 0, 0, mBitmapSrc.getWidth(), mBitmapSrc.getHeight(),
                matrix, true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(mBitmapSrc, 0, 0, mPaint);
        canvas.translate(100, mBitmapSrc.getHeight());
        canvas.skew(1, 0);
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(mBitmapReverse, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mBitmapShade, 0, 0, mPaint);
        mPaint.setXfermode(null);
    }
}
