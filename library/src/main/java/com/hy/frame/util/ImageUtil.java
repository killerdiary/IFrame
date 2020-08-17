package com.hy.frame.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ImageUtil {

    /**
     * 质量压缩图片
     *
     * @param d    原图
     * @param size 目标大小 (100kb: 100x1024)
     */
    public static Bitmap cmpBitmapBySize(Bitmap d, int size) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        d.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int length = baos.toByteArray().length;
        if (length <= size) return d;//该图已满足条件
        int quality = getQualityBySize(100, length, size);
        while (length > size) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); //重置baos即清空baos
            d.compress(Bitmap.CompressFormat.JPEG, quality, baos); //这里压缩options%，把压缩后的数据存放到baos中
            length = baos.toByteArray().length;
            int newQuality = getQualityBySize(quality, length, size);
            if (newQuality >= quality) break;
            quality = newQuality;
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(bais, null, null);//把ByteArrayInputStream数据生成图片
    }

    /**
     * 获取压缩质量
     *
     * @param fSize 当前大小
     * @param dSize 目标大小
     */
    private static int getQualityBySize(int quality, int fSize, int dSize) {
        if (fSize > dSize) {
            if (fSize >= dSize * 2)
                quality = quality - 10;
            else
                quality = quality - 5;
        }
        if (quality <= 0) return 1;
        return quality;
    }

//    /**
//     * 获取图片旋转度
//     * @param d 图片地址
//     */
//    public static int readPictureDegree(String d) {
//        int degree = 0;
//        try {
//            val exifInterface = ExifInterface(path)
//            val orientation: Int = exifInterface.getAttributeInt(
//                    ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_NORMAL
//            )
//            when (orientation) {
//                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
//                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
//                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        MyLog.d("degree: $degree")
//        return degree
//    }

    public static boolean cmpBitmap(String path, String newPath, int quality, int maxWidth, int maxHeight) {
        return cmpBitmap(path, newPath, quality, maxWidth, maxHeight, 0);
    }

    public static boolean cmpBitmap(String path, String newPath, int quality, int maxWidth, int maxHeight, int degree) {
        int maxW = maxWidth;
        int maxH = maxHeight;
        //判断图片是否时被旋转
        if (degree == 90 || degree == 270) {
            maxW = maxHeight;
            maxH = maxWidth;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);//此时返回bm为空
        options.inJustDecodeBounds = false;
        int w = options.outWidth;
        int h = options.outHeight;
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1; //be=1表示不缩放
        if (maxW > 0 && w > h && w > maxW) {//如果宽度大的话根据宽度固定大小缩放
            be = w / maxW;
        } else if (maxH > 0 && w < h && h > maxH) {//如果高度高的话根据宽度固定大小缩放
            be = h / maxH;
        }
        if (be <= 0)
            be = 1;
        options.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        // 压缩完毕 判断是否需要纠正旋转
        if (degree != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            try {
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } catch (Exception ignored) {
            }
        }
        File f = new File(newPath);
        try {
            if (!f.exists())
                f.createNewFile();
            FileOutputStream fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fOut);
            fOut.flush();
            fOut.close();
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }
}
