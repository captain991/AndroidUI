package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by captain on 2017/5/14.
 */

public class MinionsView extends View {
    //身体的宽高比例3/5
    private final float BODY_WIDTH_HEIGHT_SCALE = 0.6f;
    //身体整体等比例放大，身体的宽/View宽和身体的高/View的高，谁有一个达到3/5就停止放大
    private final float BODY_VIEW_SCALE = 0.6f;

    private final int VIEW_DEFAULT_SIZE = 200;
    private int VIEW_HEIGHT_UNSPECIFIED;
    private int VIEW_WIDTH_UNSPECIFIED;

    private Paint mPaint = new Paint();
    private RectF mBodyRect = new RectF();
    private float mBodyWidth;
    private float mBodyHeight;
    private float mRadius;

    private int mColorClothes = Color.rgb(32, 116, 160);//衣服的颜色
    private int mColorBody = Color.rgb(249, 217, 70);//皮肤的颜色
    private int mColorStroke = Color.BLACK;

    private int mStrokeWidth = 10;
    private int mOffset = mStrokeWidth / 2;
    private float mHandsHeight;//计算出吊带的高度时，可以用来做手的高度

    public MinionsView(Context context) {
        super(context);
    }

    public MinionsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getDimension(widthMeasureSpec, true), getDimension(heightMeasureSpec, false));
    }

    public int getDimension(int measureSpec, boolean isWidth) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int size;
        switch (specMode) {
            case MeasureSpec.EXACTLY:
            case MeasureSpec.AT_MOST:
                size = specSize;
                if (isWidth)
                    VIEW_WIDTH_UNSPECIFIED = size;
                else
                    VIEW_HEIGHT_UNSPECIFIED = size;
                break;
            case MeasureSpec.UNSPECIFIED:
            default:
                if (isWidth)
                    size = VIEW_WIDTH_UNSPECIFIED;
                else
                    size = VIEW_HEIGHT_UNSPECIFIED;
                if (size == 0)
                    size = dp2px(VIEW_DEFAULT_SIZE);
                break;
        }
        return size;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        drawBody(canvas);
        drawClothes(canvas);
        drawStroke(canvas);
    }

    public void init() {
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mBodyWidth = Math.min(getWidth(), getHeight() * BODY_WIDTH_HEIGHT_SCALE) * BODY_VIEW_SCALE;
        mBodyHeight = Math.min(getHeight(), getWidth() / BODY_WIDTH_HEIGHT_SCALE) * BODY_VIEW_SCALE;
        mBodyRect.left = (getWidth() - mBodyWidth) / 2;
        mBodyRect.top = (getHeight() - mBodyHeight) / 2;
        mBodyRect.right = mBodyRect.left + mBodyWidth;
        mBodyRect.bottom = mBodyRect.top + mBodyHeight;
        mRadius = mBodyWidth / 2;
        mHandsHeight = (getHeight() + mBodyHeight) / 2 + mOffset - mRadius * 1.65f;
    }

    public void initPaint() {
        if (mPaint == null)
            mPaint = new Paint();
        else
            mPaint.reset();
        mPaint.setAntiAlias(true);
    }

    public void drawBody(Canvas canvas) {
        mPaint.setColor(mColorBody);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(mBodyRect, mRadius, mRadius, mPaint);
    }

    public void drawStroke(Canvas canvas) {
        initPaint();
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColorStroke);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(mBodyRect, mRadius, mRadius, mPaint);
    }

    public void drawClothes(Canvas canvas) {
        RectF rect = new RectF();

        rect.left = (getWidth() - mBodyWidth) / 2 + mOffset;
        rect.top = (getHeight() + mBodyHeight) / 2 - mRadius * 2 + mOffset;
        rect.right = rect.left + mBodyWidth - mOffset * 2;
        rect.bottom = rect.top + mRadius * 2 - mOffset * 2;

        mPaint.setColor(mColorClothes);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawArc(rect, 0, 180, true, mPaint);


        int h = (int) (mRadius * 0.5);
        int w = (int) (mRadius * 0.3);

        rect.left += w;
        rect.top = rect.top + mRadius - h;
        rect.right -= w;
        rect.bottom = rect.top + h;

        canvas.drawRect(rect, mPaint);

        //画横线
        initPaint();
        mPaint.setColor(mColorStroke);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        float[] pts = new float[20];//5条线

        pts[0] = rect.left - w;
        pts[1] = rect.top + h;
        pts[2] = pts[0] + w;
        pts[3] = pts[1];

        pts[4] = pts[2];
        pts[5] = pts[3] + mOffset;
        pts[6] = pts[4];
        pts[7] = pts[3] - h;

        pts[8] = pts[6] - mOffset;
        pts[9] = pts[7];
        pts[10] = pts[8] + (mRadius - w) * 2;
        pts[11] = pts[9];

        pts[12] = pts[10];
        pts[13] = pts[11] - mOffset;
        pts[14] = pts[12];
        pts[15] = pts[13] + h;

        pts[16] = pts[14] - mOffset;
        pts[17] = pts[15];
        pts[18] = pts[16] + w;
        pts[19] = pts[17];
        canvas.drawLines(pts, mPaint);

        //画左吊带
        initPaint();
        mPaint.setColor(mColorClothes);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        path.moveTo(rect.left - w - mOffset, mHandsHeight);
        path.lineTo(rect.left + h / 4, rect.top + h / 2);
        final float smallW = w / 2 * (float) Math.sin(Math.PI / 4);
        path.lineTo(rect.left + h / 4 + smallW, rect.top + h / 2 - smallW);
        final float smallW2 = w / (float) Math.sin(Math.PI / 4) / 2;
        path.lineTo(rect.left - w - mOffset, mHandsHeight - smallW2);

        canvas.drawPath(path, mPaint);
        initPaint();
        mPaint.setColor(mColorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
        initPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(rect.left + h / 5, rect.top + h / 4, mStrokeWidth * 0.7f, mPaint);

        //画右吊带

        initPaint();
        mPaint.setColor(mColorClothes);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        path.reset();
        path.moveTo(rect.left - w + 2 * mRadius - mOffset, mHandsHeight);
        path.lineTo(rect.right - h / 4, rect.top + h / 2);
        path.lineTo(rect.right - h / 4 - smallW, rect.top + h / 2 - smallW);
        path.lineTo(rect.left - w + 2 * mRadius - mOffset, mHandsHeight - smallW2);

        canvas.drawPath(path, mPaint);
        initPaint();
        mPaint.setColor(mColorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mPaint);
        initPaint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(rect.right - h / 5, rect.top + h / 4, mStrokeWidth * 0.7f, mPaint);

        //中间口袋
        initPaint();
        mPaint.setColor(mColorStroke);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        path.reset();
        float radiusBigPokect = w / 2.0f;
        path.moveTo(rect.left + 1.5f * w, rect.bottom - h / 4);
        path.lineTo(rect.right - 1.5f * w, rect.bottom - h / 4);
        path.lineTo(rect.right - 1.5f * w, rect.bottom + h / 4);
        path.addArc(rect.right - 1.5f * w - radiusBigPokect * 2, rect.bottom + h / 4 - radiusBigPokect,
                rect.right - 1.5f * w, rect.bottom + h / 4 + radiusBigPokect, 0, 90);
        path.lineTo(rect.left + 1.5f * w + radiusBigPokect, rect.bottom + h / 4 + radiusBigPokect);

        path.addArc(rect.left + 1.5f * w, rect.bottom + h / 4 - radiusBigPokect,
                rect.left + 1.5f * w + 2 * radiusBigPokect, rect.bottom + h / 4 + radiusBigPokect, 90, 90);
        path.lineTo(rect.left + 1.5f * w, rect.bottom - h / 4 - mOffset);
        canvas.drawPath(path, mPaint);
        //      下边一竖，分开裤子
        canvas.drawLine(mBodyRect.left + mBodyWidth / 2, mBodyRect.bottom - h * 0.8f, mBodyRect.left + mBodyWidth / 2, mBodyRect.bottom, mPaint);
//      左边的小口袋
        float radiusSamllPokect = w * 1.2f;
        canvas.drawArc(mBodyRect.left - radiusSamllPokect, mBodyRect.bottom - mRadius - radiusSamllPokect,
                mBodyRect.left + radiusSamllPokect, mBodyRect.bottom - mRadius + radiusSamllPokect, 80, -60, false, mPaint);
//      右边小口袋
        canvas.drawArc(mBodyRect.right - radiusSamllPokect, mBodyRect.bottom - mRadius - radiusSamllPokect,
                mBodyRect.right + radiusSamllPokect, mBodyRect.bottom - mRadius + radiusSamllPokect, 100, 60, false, mPaint);
//        canvas.drawArc(left + w/5,);

    }

    public int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5f);
    }
}
