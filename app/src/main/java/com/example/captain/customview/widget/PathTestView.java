package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by captain on 2017/6/8.
 * 验证path的op和FillType效果的测试View,结论如下:
 *
 * 1.PathA.op(PathB,Path.Op)
 * DIFFERENCE（差集）     A-B
 * INTERSECT(相交)       A∩B
 * UNION(并集)           A∪B
 * XOR(异或)             A⊕B
 * REVERSE_DIFFERENCE   B-A
 *
 *
 *
 * 2.Path.FillType
 * WINDING              A∪B
 * EVEN_ODD             A⊕B
 * INVERSE_WINDING      U-(A∪B)
 * INVERSE_EVEN_ODD     U-(A⊕B)
 * ps:INVERSE补的意思.U为全集,取包裹A∪B的最小矩形区域.
 */

public class PathTestView extends View {
    private Paint mPaint = new Paint();
    private Path mPath = new Path();
    private Path path = new Path();
    private Paint paint = new Paint();

    public PathTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.RED);


        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
//        mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.addCircle(200, 200, 100, Path.Direction.CW);

        //测试drawTextOnPath的
//        canvas.drawLine(260, 0, 260, getHeight(), mPaint);
//        canvas.drawLine(300, 0, 300, getHeight(), mPaint);
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawTextOnPath("善恶终有报，天道好轮回。 不信抬头看，苍天饶过谁。善恶终有报，天道好轮回。 不信抬头看，苍天饶过谁。", mPath, 0, 0, mPaint);
//
//
//        canvas.translate(0, 250);
//
//
//        canvas.drawPath(mPath, mPaint);
//        canvas.drawTextOnPath("善恶终有报，天道好轮回。 不信抬头看，苍天饶过谁。善恶终有报，天道好轮回。 不信抬头看，苍天饶过谁。", mPath, 40, 0, mPaint);

        path.addCircle(300, 300, 100, Path.Direction.CW);


        canvas.drawPath(mPath, paint);
        canvas.drawPath(path, paint);
        mPath.op(path, Path.Op.XOR);
        canvas.drawPath(mPath, mPaint);

    }
}
