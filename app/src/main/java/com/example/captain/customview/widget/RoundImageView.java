package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.captain.customview.R;

/**
 * Created by captain on 2017/5/20.
 */

public class RoundImageView extends View {
    private Bitmap mBitmap;
    private Paint mPaint;
    private BitmapShader mBitmapShader;
    private ShapeDrawable mShapeDrawable;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.moon);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.setBounds(0, 0, getWidth(), getHeight());
        mPaint.setShader(mBitmapShader);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Matrix matrix = new Matrix();
//        float scaleX = (float) getWidth() / mBitmap.getWidth();
//        float scaleY = (float) getHeight() / mBitmap.getHeight();
//        float scale = (scaleX > scaleY) ? scaleX : scaleY;
//        matrix.postScale(scale, scale);
//        if (scaleX < scaleY)
//            matrix.postTranslate((getWidth() - mBitmap.getWidth() * scale) / 2, 0);
//        else
//            matrix.postTranslate(0, (getHeight() - mBitmap.getHeight() * scale) / 2);
////        Log.e("captain", "scaledX:" + mBitmap.getWidth() * scale + ",scaledY:" + scale * mBitmap.getHeight());
//        mBitmapShader.setLocalMatrix(matrix);
//
//        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//        //使用canvas画圆形
////        float radius = getWidth() < getHeight() ? getWidth() / 2 : getHeight() / 2;
////        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
//
//        //使用ShapeDrawable画
//        mShapeDrawable.setBounds(0, 0, getWidth(), getHeight());
//        mShapeDrawable.getPaint().setShader(mBitmapShader);
//        mShapeDrawable.draw(canvas);
//        canvas.restoreToCount(layerId);


//        canvas.drawColor(Color.BLUE);
//        Log.e("captain", "first save count:" + canvas.getSaveCount());
//
//        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 700, 700, mPaint);
//        int s = canvas.save();
//        Log.e("captain", "save count:" + canvas.getSaveCount() + "s:" + s);
//
//        mPaint.setColor(Color.GREEN);
//        canvas.translate(100, 100);
//        canvas.drawRect(0, 0, 700, 700, mPaint);
//        canvas.save();
//        Log.e("captain", "save count:" + canvas.getSaveCount());
//
//        canvas.restoreToCount(1);
//        mPaint.setColor(Color.YELLOW);
//        mPaint.setStrokeWidth(10);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(0, 0, 700, 700, mPaint);
//        Log.e("captain", "save count:" + canvas.getSaveCount());

        int id = canvas.save();
        canvas.translate(50, 50);
        int id2 = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        int id3 = canvas.save();
        Log.e("captain", "id1:" + id + ",id2:" + id2 + ",id3:" + id3 + ",saveCount:" + canvas.getSaveCount());
        canvas.drawColor(Color.GREEN);
        canvas.restoreToCount(id);
        canvas.drawRect(0, 0, 100, 100, mPaint);

    }
}
