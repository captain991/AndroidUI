package com.example.captain.customview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Random;

/**
 * Created by captain on 2017/6/14.
 */

public class VolumeWaveView extends View {


    private int volume = 300;
    private int waveWidth = 800;
    //曲线取点时X轴的间隔
    private int intervalCurveX = 40;

    //网格相距的间隔
    private int intervalGrid = 30;


    //画曲线的画笔
    private Paint mPaintCurve = new Paint();
    //画网格的画笔
    private Paint mPaintGrid = new Paint();

    private LinearGradient mLinearGradientCurve;

    private Random random = new Random();


    private Path mPathCurve = new Path();
    private Path mPathDrawing = new Path();
    private PathMeasure pathMeasureCurve;
    private PathMeasure pathMeasureDrawing;
    private int lastDistance = 0;
    private float lastX = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int volume = (int) msg.obj;
            updateVolume(volume);
        }
    };

    public VolumeWaveView(Context context) {
        this(context, null);
    }

    public VolumeWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VolumeWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mPaintCurve.setColor(Color.parseColor("#27a2f2"));
        mPaintCurve.setStyle(Paint.Style.STROKE);
        mPaintCurve.setStrokeWidth(5);
        mPaintCurve.setAntiAlias(true);
        mPaintCurve.setPathEffect(new CornerPathEffect(80));

        mPaintGrid.setColor(Color.parseColor("#27a2f2"));
        mPaintGrid.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintGrid.setStrokeWidth(3);

        pathMeasureCurve = new PathMeasure(mPathCurve, false);
        pathMeasureDrawing = new PathMeasure(mPathDrawing, false);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mLinearGradientCurve = new LinearGradient(w / 2 - (waveWidth + intervalCurveX) / 2, h / 2,
                getWidth() / 2 + (waveWidth + intervalCurveX) / 2, h / 2,
                new int[]{0xff27a2f2, 0xff27e1f2, 0xff27a2f2}, new float[]{0f, 0.5f, 1f},
                Shader.TileMode.CLAMP);
        mPaintCurve.setShader(mLinearGradientCurve);

        mPaintGrid.setShader(new LinearGradient(0, 0, w, 0, new int[]{0x1127a2f2, 0x3327a2f2, 0x1127a2f2},
                new float[]{0f, 0.5f, 1f}, Shader.TileMode.CLAMP));


        mPathCurve.moveTo(0, h / 2);
        mPathDrawing.moveTo(0, h / 2);
        lastX = getWidth();

        //每隔一秒产生一个音量值

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int randomInt = random.nextInt(200);
                    if (randomInt < 20)
                        randomInt = 20;
                    Log.e("captain", "randomInt:" + randomInt);
                    Message msg = handler.obtainMessage(0, randomInt);
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(500);
                    } catch (Exception ex) {
                        Log.e("captain", "exception:" + ex.toString());
                        break;
                    }
                }
            }
        }).start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.clipRect(0, 0, getWidth(), getHeight());
        //画中轴线
        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mPaintCurve);


        //画波形图
        canvas.drawPath(mPathDrawing, mPaintCurve);


        canvas.translate(0, 200);
        mPaintCurve.setShader(null);
        mPaintCurve.setColor(Color.parseColor("#3327a2f2"));

        //画波形图的影子
        canvas.drawPath(mPathDrawing, mPaintCurve);

        //恢复
        canvas.restore();
        mPaintCurve.setShader(mLinearGradientCurve);
        mPaintCurve.setColor(Color.parseColor("#27a2f2"));

        //画网格背景
        for (int i = 0; i < getHeight() / intervalGrid; i++)
            canvas.drawLine(0, (i + 1) * intervalGrid, getWidth(), (i + 1) * intervalGrid, mPaintGrid);
        for (int i = 0; i < getWidth() / intervalGrid; i++)
            canvas.drawLine((i + 1) * intervalGrid, 0, (i + 1) * intervalGrid, getHeight(), mPaintGrid);


        //测试
//        float[] position = new float[2];
//        mPathCurve.rLineTo(200, 0);
//        mPathCurve.rLineTo(0, 200);
//        mPathCurve.offset(200, 0);
//        mPathDrawing.offset(200, 0);
//        pathMeasureCurve.setPath(mPathCurve, false);
//        pathMeasureCurve.getSegment(0, 400, mPathDrawing, false);
//        canvas.drawPath(mPathDrawing, mPaintCurve);


    }


    public void updateVolume(int volume) {
        this.volume = volume;
        //取出上一次已画出的长度
        pathMeasureCurve.setPath(mPathCurve, false);
        pathMeasureDrawing.setPath(mPathDrawing, false);
        //已经画过的长度
        float drewPathLength = pathMeasureDrawing.getLength();
        mPathCurve.rLineTo(intervalCurveX, lastDistance - volume);
        mPathCurve.rLineTo(intervalCurveX, volume * 2);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(drewPathLength, pathMeasureCurve.getLength());
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentLength = (float) animation.getAnimatedValue();
                //偏移path
                float[] drawingPosition = new float[2];
                pathMeasureCurve.setPath(mPathCurve, false);
                pathMeasureCurve.getPosTan(currentLength, drawingPosition, null);
                float offset = getWidth() - drawingPosition[0];
                Log.e("captain", "offset:" + offset);
                if (offset < 0) {
                    mPathCurve.offset(offset, 0);
                    mPathDrawing.offset(offset, 0);
//                    lastX = drawingPosition[0];
                }

                //取出path
                pathMeasureCurve.setPath(mPathCurve, false);
                pathMeasureDrawing.setPath(mPathDrawing, false);
                pathMeasureCurve.getSegment(pathMeasureDrawing.getLength(), currentLength, mPathDrawing, false);
                invalidate();
            }
        });
        valueAnimator.start();
        lastDistance = -volume;

    }


}