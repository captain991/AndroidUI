package com.example.captain.customview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by captain on 2017/6/5.
 */

public class RevealDrawable extends Drawable {
    private Drawable mSelectedDrawable = null;
    private Drawable mUnselectedDrawable = null;
    private Context mContext = null;

    public RevealDrawable(Context context, int selectedRes, int unselectedRes) {
        mContext = context;
        mSelectedDrawable = context.getDrawable(selectedRes);
        mUnselectedDrawable = context.getDrawable(unselectedRes);
    }

    @Override
    public int getIntrinsicHeight() {
        return super.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return super.getIntrinsicWidth();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }
}
