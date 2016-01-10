package com.lawgimenez.kontaks.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class KontaksDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "com.lawgimenez.kontaks";

    // Table name for contacts
    private static final String TABLE_CONTACTS = "contacts";

    // Column names for contacts
    private static final String COL_ID = "id";
    private static final String COL_FULLNAME = "fullname";
    private static final String COL_HAS_PHONE_NUMBER = "has_phone_number";
    private static final String COL_PHOTO_ID = "photo_id";
    private static final String COL_PHOTO_URI = "photo_uri";
    private static final String COL_PHOTO_THUMBNAIL_URI = "photo_thumbnail_uri";
    private static final String COL_TIMES_CONTACTED = "times_contacted";
    private static final String COL_LAST_TIME_CONTACTED = "last_time_contacted";
    private static final String COL_FAVORITED = "favorited";

    // SQL statement for creating contacts table
    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FULLNAME + " TEXT, "
            + COL_HAS_PHONE_NUMBER + " INTEGER, "
            + COL_PHOTO_ID + " TEXT, "
            + COL_PHOTO_URI + " TEXT, "
            + COL_PHOTO_THUMBNAIL_URI + " TEXT, "
            + COL_TIMES_CONTACTED + " INTEGER, "
            + COL_LAST_TIME_CONTACTED + " TEXT, "
            + COL_FAVORITED + " INTEGER)";

    public KontaksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        onCreate(database);
    }

}
