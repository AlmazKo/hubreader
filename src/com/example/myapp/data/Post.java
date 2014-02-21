package com.example.myapp.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class Post implements BaseColumns {

    public static final String TABLE_NAME = "posts";

    public static final String LINK = "link";
    public static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String DATE_CREATE = "date_create";


    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " (" +
                    _ID + " integer PRIMARY KEY autoincrement," +
                    LINK + "," +
                    DESCRIPTION + "," +
                    DATE_CREATE + "," +
                    TITLE + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS posts");
        onCreate(db);
    }

}
