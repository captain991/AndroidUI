package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.captain.customview.R;

/**
 * Created by captain on 2017/5/18.
 */

public class XfermodeView extends View {
    private Bitmap mBitmapSrc;
    private Bitmap mBitmapDst;
    private Paint mPaint;
    private Path mGesturePath = new Path();

    public XfermodeView(Context context) {
        super(context);
    }

    public XfermodeView(Context context, AttributeSet attr) {
        super(context, attr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        mBitmapSrc = BitmapFactory.decodeResource(getResources(), R.drawable.xyjy6);
//        mBitmapDst = BitmapFactory.decodeResource(getResources(), R.drawable.shade);
        mBitmapDst = Bitmap.createBitmap(mBitmapSrc.getWidth(), mBitmapSrc.getHeight(), Bitmap.Config.ARGB_8888);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        Canvas c = new Canvas(mBitmapDst);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(30);
        mPaint.setColor(Color.parseColor("#337788"));
        c.drawPath(mGesturePath, mPaint);
        canvas.drawBitmap(mBitmapDst, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mBitmapSrc, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mGesturePath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mGesturePath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return true;
    }
}
