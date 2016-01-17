package com.lawgimenez.kontaks.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lawgimenez.kontaks.models.Contact;
import com.lawgimenez.kontaks.models.Group;

import java.util.ArrayList;

/**
 * Created by lawrencegimenez on 1/10/16.
 */
public class KontaksDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    private static final String DATABASE_NAME = "com.lawgimenez.kontaks";

    // Table name for contacts
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_GROUPS = "groups";

    // Column names for contacts
    private static final String COL_ID = "id";
    private static final String COL_FULLNAME = "fullname";
    private static final String COL_GIVEN_NAME = "given_name";
    private static final String COL_FAMILY_NAME = "family_name";
    private static final String COL_HAS_PHONE_NUMBER = "has_phone_number";
    private static final String COL_PHOTO_ID = "photo_id";
    private static final String COL_PHOTO_URI = "photo_uri";
    private static final String COL_PHOTO_THUMBNAIL_URI = "photo_thumbnail_uri";
    private static final String COL_TIMES_CONTACTED = "times_contacted";
    private static final String COL_LAST_TIME_CONTACTED = "last_time_contacted";
    private static final String COL_FAVORITED = "favorited";

    // Column names for groups
    private static final String COL_GROUP_NAME = "group_name";
    private static final String COL_GROUP_DESC = "group_desc";
    private static final String COL_GROUP_FOREIGN_KEY = "group_contacts_id";

    // SQL statement for creating contacts table
    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_FULLNAME + " TEXT, "
            + COL_GIVEN_NAME + " TEXT, "
            + COL_FAMILY_NAME + " TEXT, "
            + COL_HAS_PHONE_NUMBER + " INTEGER, "
            + COL_PHOTO_ID + " TEXT, "
            + COL_PHOTO_URI + " TEXT, "
            + COL_PHOTO_THUMBNAIL_URI + " TEXT, "
            + COL_TIMES_CONTACTED + " INTEGER, "
            + COL_LAST_TIME_CONTACTED + " TEXT, "
            + COL_FAVORITED + " INTEGER)";

    // SQL statement for creating groups table
    private static final String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_GROUP_FOREIGN_KEY + " INTEGER, "
            + COL_GROUP_NAME + " TEXT, "
            + COL_GROUP_DESC + " TEXT)";


    public KontaksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_CONTACTS_TABLE);
        database.execSQL(CREATE_GROUPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPS);

        onCreate(database);
    }

    public void addContacts(Contact contacts) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_FULLNAME, contacts.getDisplayName());
        cv.put(COL_GIVEN_NAME, contacts.getGivenName());
        cv.put(COL_FAMILY_NAME, contacts.getFamilyName());
        cv.put(COL_HAS_PHONE_NUMBER, contacts.getHasPhoneNumber());
        cv.put(COL_PHOTO_ID, contacts.getPhotoId());
        cv.put(COL_PHOTO_URI, contacts.getPhotoUri());
        cv.put(COL_PHOTO_THUMBNAIL_URI, contacts.getPhotoThumbnailUri());
        cv.put(COL_TIMES_CONTACTED, contacts.getTimesContacted());
        cv.put(COL_LAST_TIME_CONTACTED, contacts.getLastTimeContacted());
        cv.put(COL_FAVORITED, contacts.getIsFavorited());

        db.insert(TABLE_CONTACTS, null, cv);
        db.close();
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> listContacts = new ArrayList<>();

        String sqlSelectAll = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelectAll, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setDisplayName(cursor.getString(cursor.getColumnIndex(COL_FULLNAME)));

                listContacts.add(contact);
            } while (cursor.moveToNext());
        }

        return listContacts;
    }

    public void addGroup(Group group) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_GROUP_NAME, group.getGroupName());
        cv.put(COL_GROUP_DESC, group.getGroupDescription());

        db.insert(TABLE_GROUPS, null, cv);
        db.close();
    }

    public int getContactsCount() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, null, null, null, null, null, null);
        return cursor.getCount();
    }

    public int getGroupsCount() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_GROUPS, null, null, null, null, null, null);
        return cursor.getCount();
    }

}
