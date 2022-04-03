package com.example.wink;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "LOCAL DB: ";
    private Context context;
    public static final String DATABASE_NAME = "WinksDB.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_winks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "img_title";
    public static final String COLUMN_LINK = "img_link";
    public static final String COLUMN_SHOWED = "was_showed"; // will be 0 for no and 1 for yes.
    public static final String IMG  = "img";

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " TEXT, " +
                        COLUMN_TITLE +  " TEXT, " +
                        COLUMN_LINK +  " TEXT, " +
                        IMG + " BLOB, " +
                        COLUMN_SHOWED + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }


    String addImg(String key, UploadImage uploadImage){

        //using this API https://github.com/nostra13/Android-Universal-Image-Loader
        //TODO:
        //look at public url here: https://www.sentinelstand.com/article/guide-to-firebase-storage-download-urls-tokens
        //creating byte[] form url
        ImageLoader imageLoader = ImageLoader.getInstance();

        // Load image, decode it to Bitmap and return Bitmap to callback
         /*
        imageLoader.loadImage(uploadImage.getImageUrl(), new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                // Do whatever you want with Bitmap
                byte[] img = getBytesFromBitmap(loadedImage);

            }
        });



         */


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, key);
        cv.put(COLUMN_TITLE, uploadImage.getImageName());
        cv.put(COLUMN_LINK, uploadImage.getImageUrl());
        cv.put(COLUMN_SHOWED, uploadImage.getWasShown());
        //cv.put(IMG, img);

        long result = db.insert(TABLE_NAME,null, cv);
        if (result == -1){
            Log.e(TAG, "addImg: FAILED TO SAVE IMG");
            return "0";
        }else{
            Log.i(TAG, "addImg: Img was saved to local db");
            return key;
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            return stream.toByteArray();
        }
        return null;
    }

}
