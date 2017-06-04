package com.example.captain.customview.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by captain on 2017/6/3.
 */

public class BitmapUtil {

    /**
     * @param context
     * @param resId   需要压缩的图片资源id
     * @param width   需要显示的宽度
     * @param height  需要显示的高度
     * @return 压缩过的bitmap
     */
    public static Bitmap getCompressedBitmap(Context context, int resId, int width, int height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(width, height, options);
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    private static int calculateSampleSize(int width, int height, BitmapFactory.Options options) {
        int sampleSize = 1;
        if (width < options.outWidth || height < options.outHeight) {
            int widthRatio = Math.round((float) options.outWidth / width);
            int heightRatio = Math.round((float) options.outHeight / height);
            sampleSize = widthRatio < heightRatio ? widthRatio : heightRatio;
        }
        return sampleSize;
    }
}
