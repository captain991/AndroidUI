package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.captain.customview.R;
import com.example.captain.customview.util.BitmapUtil;

/**
 * Created by captain on 2017/6/2.
 * 放大镜控件
 */

public class Magnifier extends View {
    private Paint mPaint = new Paint();
    private Bitmap mBitmap;
    private int radius = 100;
    private int centerX = radius, centerY = radius;
    private int scale = 4;

    public Magnifier(Context context) {
        this(context, null);
    }

    public Magnifier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void init() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.moon, options);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);


//        canvas.translate(0, mBitmap.getHeight());


        Matrix matrix = new Matrix();
        matrix.preScale(scale, scale, centerX, centerY);
        Log.e("captain", "centerX:" + centerX + ",centerY:" + centerY + ",translate(" + (centerX - scale * centerX) + ","
                + (centerY - scale * centerY) + ")");
        BitmapShader bitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        bitmapShader.setLocalMatrix(matrix);
        mPaint.setShader(bitmapShader);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, mPaint);
//        canvas.drawRect(0, 0, scale * mBitmap.getWidth(), scale * mBitmap.getHeight(), mPaint);


//        canvas.translate(0, scale * mBitmap.getHeight());
////
//        Matrix matrix2 = new Matrix();
//        matrix2.preScale(scale, scale);
////        matrix2.preTranslate(centerX - scale * centerX, -centerY - scale * centerY);
//        BitmapShader bitmapShader2 = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        bitmapShader2.setLocalMatrix(matrix2);
//        mPaint.setShader(bitmapShader2);
//        mPaint.setStyle(Paint.Style.FILL);
////        canvas.drawCircle(centerX, centerY, radius, mPaint);
//        canvas.drawRect(0, 0, scale * mBitmap.getWidth(), scale * mBitmap.getHeight(), mPaint);
//

//        mPaint.setShader(null);
//        canvas.drawLine(0, 200, getWidth(), 200, mPaint);
//        canvas.drawLine(200, 0, 200, getHeight(), mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                centerX = (int) event.getX();
                centerY = (int) event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}
