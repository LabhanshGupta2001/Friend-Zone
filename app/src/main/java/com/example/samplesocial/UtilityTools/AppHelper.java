package com.example.samplesocial.UtilityTools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.core.content.ContextCompat;


import com.example.samplesocial.Retrofit.Constant;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Anil on 26/Aug/2020.
 */
public class AppHelper {

    public static byte[] getFileDataFromDrawable(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    public static byte[] getFiletoByteArray(File exportDirFile) {

        int size = (int) exportDirFile.length();

        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(exportDirFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bytes;
    }

    public static byte[] getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        assert drawable != null;
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }
    public static byte[] getFileFromUriByte(Context context, Uri uri,long length) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = (int)length;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     *
     * @param context
     * @param drawable
     * @return
     */
    public static byte[] getFileDataFromDrawableContext(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream);
        Utils.E("byteArrayOutputStream::Length"+byteArrayOutputStream.toByteArray().length);

        return byteArrayOutputStream.toByteArray();
    }
    public static MultipartBody.Part prepareFilePart(Context context, String partName, Uri fileUri) {
        RequestBody requestBody =
                RequestBody.create( getFileDataFromDrawable(context, fileUri), MediaType.parse(Constant.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image", requestBody);
    }

    public static MultipartBody.Part prepareFilePart(String partName, Drawable fileUri) {
        RequestBody requestBody =
                RequestBody.create( getFileDataFromDrawable(fileUri),MediaType.parse(Constant.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image", requestBody);
    }

    public static MultipartBody.Part prepareFilePart(Context context, String partName, int fileUri) {
        RequestBody requestBody =
                RequestBody.create( getBitmapFromVectorDrawable(context, fileUri),MediaType.parse(Constant.CONTENT_IMAGE));
        return MultipartBody.Part.createFormData(partName, "Image", requestBody);
    }

    public static MultipartBody.Part prepareFilePart(String partName) {
        RequestBody requestBody = RequestBody.create ("",MediaType.parse(Constant.CONTENT_IMAGE));

        return MultipartBody.Part.createFormData(partName, "", requestBody);
    }




}
