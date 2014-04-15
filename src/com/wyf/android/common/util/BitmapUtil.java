package com.wyf.android.common.util;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;

@SuppressLint("FloatMath")
public class BitmapUtil {

  /**
   * 转换图片成圆形 方法1
   * 
   * @param bitmap
   * @return
   */
  public static Bitmap toRoundBitmap(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    float roundPx;
    float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
    if (width <= height) {
      roundPx = width / 2;
      top = 0;
      bottom = width;
      left = 0;
      right = width;
      height = width;
      dst_left = 0;
      dst_top = 0;
      dst_right = width;
      dst_bottom = width;
    } else {
      roundPx = height / 2;
      float clip = (width - height) / 2;
      left = clip;
      right = width - clip;
      top = 0;
      bottom = height;
      width = height;
      dst_left = 0;
      dst_top = 0;
      dst_right = height;
      dst_bottom = height;
    }
    Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
    final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
    final RectF rectF = new RectF(dst);
    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, src, dst, paint);
    return output;
  }

  /**
   * 转换图片成圆形 方法2
   * 
   * @param bitmap
   * @return
   */
  public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    final RectF rectF = new RectF(rect);
    final float roundPx = bitmap.getWidth() / 2;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);
    return output;
  }

  /**
   * 复制位图
   * 
   * @param bitmap
   * @return
   */
  public static Bitmap copyBitmap(Bitmap bitmap) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRect(rect, paint);

    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);
    return output;
  }
  
//按宽高压缩载入图片方法
  @SuppressLint("FloatMath")
  public static Bitmap getBitmapFromPath(String imageFilePath, int displayWidth, int displayHeight) {
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmapOptions.inJustDecodeBounds = true;
    Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bitmapOptions);
    // 编码后bitmap的宽高,bitmap除以屏幕宽度得到压缩比
    int widthRatio = (int) FloatMath.ceil(bitmapOptions.outWidth / (float) displayWidth);
    int heightRatio = (int) FloatMath.ceil(bitmapOptions.outHeight / (float) displayHeight);
    if (widthRatio > 1 && heightRatio > 1) {
      if (widthRatio > heightRatio) {
        // 压缩到原来的(1/widthRatios)
        bitmapOptions.inSampleSize = widthRatio;
      } else {
        bitmapOptions.inSampleSize = heightRatio;
      }
    }
    bitmapOptions.inJustDecodeBounds = false;
    bmp = BitmapFactory.decodeFile(imageFilePath, bitmapOptions);
    return bmp;
  }
  
  public static Bitmap getBitmapFromDrawable(Resources resources, int drawableId, int displayWidth, int displayHeight) {
    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
    bitmapOptions.inJustDecodeBounds = true;
    Bitmap bmp = BitmapFactory.decodeResource(resources, drawableId, bitmapOptions);
    // 编码后bitmap的宽高,bitmap除以屏幕宽度得到压缩比
    int widthRatio = (int) FloatMath.ceil(bitmapOptions.outWidth / (float) displayWidth);
    int heightRatio = (int) FloatMath.ceil(bitmapOptions.outHeight / (float) displayHeight);
    if (widthRatio > 1 && heightRatio > 1) {
      if (widthRatio > heightRatio) {
        // 压缩到原来的(1/widthRatios)
        bitmapOptions.inSampleSize = widthRatio;
      } else {
        bitmapOptions.inSampleSize = heightRatio;
      }
    }
    bitmapOptions.inJustDecodeBounds = false;
    bmp = null;
    bmp = BitmapFactory.decodeResource(resources, drawableId,bitmapOptions);
    return bmp;
  }
}
