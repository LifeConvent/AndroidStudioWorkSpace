package com.connectionpractice.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nnit on 5/18/16.
 */
public class PICTOOL {


    /**
     * Take Picture
     *
     * @param filePath pic path
     */
    public static Bitmap getSmallBitmap(String filePath) {

        Bitmap bm = compressBySize(filePath,320,480);
        if(bm == null){
            return  null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm,degree) ;
        ByteArrayOutputStream baos = null ;
        try{
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        }finally{
            try {
                if(baos != null)
                    baos.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm ;

    }
    public static Bitmap getSmallBitmap(Uri uri,Context ctx) {

        Bitmap bm = compressBySize(uri,320,480,ctx);
        if(bm == null){
            return  null;
        }
        int degree = readPictureDegree(uri.getPath());
        bm = rotateBitmap(bm,degree) ;
        ByteArrayOutputStream baos = null ;
        try{
            baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        }finally{
            try {
                if(baos != null)
                    baos.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm ;

    }
    /**
     * Read Picture Rotation
     *
     * @param filePath pic path
     */
    private static int readPictureDegree(String filePath) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * Rotate Picture
     *
     * @param bitmap pic
     * @param rotate rotate degree
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate){
        if(bitmap == null)
            return null ;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }


    /**
     * 按大小设置图片
     *
     * @param pathName
     *        file path
     * @param targetWidth
     *        width
     * @param targetHeight
     *        height
     * @return
     */
    public static Bitmap compressBySize(String pathName, int targetWidth,
                                        int targetHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile(pathName, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || heightRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        //设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }


    /**
     * 按大小设置图片
     *
     * @param uri
     *        file path
     * @param targetWidth
     *        width
     * @param targetHeight
     *        height
     * @return
     */
    public static Bitmap compressBySize(Uri uri, int targetWidth,
                                        int targetHeight, Context ctx) {


        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = getBitmapFromUri(ctx, uri, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil(imgHeight / (float) targetHeight);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        //设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;

        bitmap = getBitmapFromUri(ctx, uri, opts);

        return bitmap;
    }
    private static Bitmap getBitmapFromUri(Context ctx,Uri uri,BitmapFactory.Options opts) {
        InputStream input = null;
        Bitmap bitmap = null;
        try {
            input = ctx.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input,null,opts);
            input.close();
        } catch (Exception e) {
            Log.e("BitmapFactory", "Unable to decode stream: " + e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // do nothing here
                }
            }
        }
        return bitmap;
    }
    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        Bitmap bitmap = null;
        try {
            byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * 压缩图片并转码
     *
     * @param bitmap
     *        origin pic
     * @param maxSize
     *        compressed size
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap,double maxSize) {
//		public static String bitmapToBase64(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                int options = 100;
                while ( baos.toByteArray().length/1024 >maxSize) {  //循环判断如果压缩后图片是否大于maxSize kb,大于继续压缩
                    baos.reset();//重置baos即清空baos
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                    options -= 5;//每次都减少5
                }

                ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//	            Bitmap bitmapC = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
