package com.example.wink;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String DATABASE_NAME = "WinksDB.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "my_winks";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "img_title";
    public static final String COLUMN_LINK = "img_link";
    public static final String COLUMN_SHOWED = "was_showed"; // will be 0 for no and 1 for yes.

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
                        COLUMN_SHOWED + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
}
