package com.example.captain.customview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.example.captain.customview.R;

/**
 * Created by captain on 2017/6/9.
 */

public class DragBubbleView extends View {

    public final int BUBBLE_STATE_DEFAULT = 0;
    public final int BUBBLE_STATE_CONNECT = 1;
    public final int BUBBLE_STATE_DETACH = 2;
    public final int BUBBLE_STATE_DISMISS = 3;

    private int mBubbleState;
    //静止的点半径
    private int initRadius = 60;
    private int mStillRadius;

    //移动的点的半径
    private int mMovableRadius;

    //移动的点和静止的点之间的圆心距
    private int mCenterDistance;

    //当前该画爆炸的图片的index
    private int mCurrentBurstIndex;

    //静止的点的圆心
    private PointF mPointStillCenter;

    //移动的点的圆心
    private PointF mPointMovableCenter;


    private Paint mBezierPaint = new Paint();
    private Paint mBubblePaint = new Paint();
    private Paint mTextPaint = new Paint();

    //贝瑟尔曲线路径
    private Path mBezierPath = new Path();

    private Rect mBurstRect = new Rect();

    //爆炸图片的Bitmap数组
    private Bitmap[] mBurstBitmap;
    private String mTextStr = "99+";
    private int[] mBurstDrawablesArray = {R.drawable.burst_1, R.drawable.burst_2
            , R.drawable.burst_3, R.drawable.burst_4, R.drawable.burst_5};

    public DragBubbleView(Context context) {
        this(context, null);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DragBubbleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mBubblePaint.setStyle(Paint.Style.FILL);
        mBubblePaint.setColor(Color.RED);

        mBezierPaint.setStyle(Paint.Style.FILL);
        mBezierPaint.setColor(Color.RED);

        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(50);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mBurstBitmap = new Bitmap[mBurstDrawablesArray.length];
        for (int i = 0; i < mBurstDrawablesArray.length; i++) {
            //将气泡爆炸的drawable转为bitmap
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBurstDrawablesArray[i]);
            mBurstBitmap[i] = bitmap;
        }
    }

    public void init() {
        if (mPointStillCenter == null)
            mPointStillCenter = new PointF(getWidth() / 2, getHeight() / 2);
        else
            mPointStillCenter.set(getWidth() / 2, getHeight() / 2);
        if (mPointMovableCenter == null)
            mPointMovableCenter = new PointF(getWidth() / 2, getHeight() / 2);
        else
            mPointMovableCenter.set(getWidth() / 2, getHeight() / 2);
        mStillRadius = initRadius;
        mMovableRadius = initRadius;
        mBubbleState = BUBBLE_STATE_DEFAULT;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mBubbleState != BUBBLE_STATE_DEFAULT)
                    return false;
                mCenterDistance = (int) Math.hypot(mPointMovableCenter.x - mPointStillCenter.x,
                        mPointMovableCenter.y - mPointStillCenter.y);
                if (mCenterDistance < mMovableRadius + 20) {
                    mBubbleState = BUBBLE_STATE_CONNECT;
                } else {
                    mBubbleState = BUBBLE_STATE_DEFAULT;
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mPointMovableCenter.x = event.getX();
                mPointMovableCenter.y = event.getY();
                mCenterDistance = (int) Math.hypot(mPointMovableCenter.x - mPointStillCenter.x,
                        mPointMovableCenter.y - mPointStillCenter.y);
                if (mCenterDistance > 8 * initRadius) {
                    mBubbleState = BUBBLE_STATE_DETACH;
                } else if (mStillRadius > 20) {
                    mStillRadius -= 1;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mBubbleState == BUBBLE_STATE_CONNECT) {
                    playResetAnimator();
                } else {
                    playBurstAnimator();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBubbleState == BUBBLE_STATE_CONNECT) {
            //画静止的点
            canvas.drawCircle(mPointStillCenter.x, mPointStillCenter.y, mStillRadius, mBubblePaint);

            //画贝瑟尔曲线
            int anchorX = (int) (mPointStillCenter.x + mPointMovableCenter.x) / 2;
            int anchorY = (int) (mPointStillCenter.y + mPointMovableCenter.y) / 2;
            //两个园的圆心距
            float sinTheta = (mPointMovableCenter.y - mPointStillCenter.y) / mCenterDistance;
            float cosTheta = (mPointMovableCenter.x - mPointStillCenter.x) / mCenterDistance;

            int stillStartX = (int) (mPointStillCenter.x - mStillRadius * sinTheta);
            int stillStartY = (int) (mPointStillCenter.y + mStillRadius * cosTheta);

            int moveEndX = (int) (mPointMovableCenter.x - mMovableRadius * sinTheta);
            int moveEndY = (int) (mPointMovableCenter.y + mMovableRadius * cosTheta);

            int moveStartX = (int) (mPointMovableCenter.x + mMovableRadius * sinTheta);
            int moveStartY = (int) (mPointMovableCenter.y - mMovableRadius * cosTheta);

            int stillEndX = (int) (mPointStillCenter.x + mStillRadius * sinTheta);
            int stillEndY = (int) (mPointStillCenter.y - mStillRadius * cosTheta);


            mBezierPath.reset();
            mBezierPath.moveTo(stillStartX, stillStartY);
            mBezierPath.quadTo(anchorX, anchorY, moveEndX, moveEndY);
            mBezierPath.lineTo(moveStartX, moveStartY);
            mBezierPath.quadTo(anchorX, anchorY, stillEndX, stillEndY);
            mBezierPath.close();

            canvas.drawPath(mBezierPath, mBezierPaint);
        } else if (mBubbleState == BUBBLE_STATE_DISMISS) {
            mBurstRect.set((int) mPointMovableCenter.x - mMovableRadius,
                    (int) mPointMovableCenter.y - mMovableRadius,
                    (int) mPointMovableCenter.x + mMovableRadius,
                    (int) mPointMovableCenter.y + mMovableRadius);
            canvas.drawBitmap(mBurstBitmap[mCurrentBurstIndex], null, mBurstRect, mBubblePaint);
        }

        if (mBubbleState != BUBBLE_STATE_DISMISS) {
            //画移动的点
            canvas.drawCircle(mPointMovableCenter.x, mPointMovableCenter.y, mMovableRadius, mBubblePaint);

            //画移动的点的文字
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float x = mPointMovableCenter.x;
            float y = mPointMovableCenter.y - (fontMetrics.bottom + fontMetrics.top) / 2;
            canvas.drawText(mTextStr, x, y, mTextPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init();
    }

    public void playResetAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointFEvaluator(),
                mPointMovableCenter, mPointStillCenter);
        valueAnimator.setInterpolator(new OvershootInterpolator(5f));
        valueAnimator.setDuration(200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPointMovableCenter = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                init();
            }
        });
        valueAnimator.start();
    }

    public void playBurstAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, mBurstBitmap.length - 1);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentBurstIndex = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                mBubbleState = BUBBLE_STATE_DISMISS;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                init();
            }
        });
        valueAnimator.start();
    }

}
