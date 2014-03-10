package com.github.hubreader.data;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class PostTable implements BaseColumns {

    public static final String TABLE_NAME = "posts";

    public static final String ID = "id";
    public static final String LINK_PREVIEW = "link_preview";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String DATE_PUBLISH = "date_publish";


    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + TABLE_NAME + " (" +
                    _ID + " integer PRIMARY KEY," +
                    ID + " integer, " +
                    LINK_PREVIEW + "," +
                    DESCRIPTION + "," +
                    DATE_PUBLISH + "," +
                    TITLE +
                    ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
