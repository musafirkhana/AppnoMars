package com.appnometry.appnomars.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Vector;

public class AppnomarsDBHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "APPNOMARSDB";

    // Contacts table name
    private static final String TABLE_CONTACTS = "APPNOMARS_CART_TABLE";
    // Contacts Table Columns names

    private static final String ITEM_ID = "item_id";
    private static final String ITEM_PRICE = "item_price";
    private static final String ITEM_IMAGE_URL = "item_image_url";
    private static final String ITEM_TITLE = "item_title";
    private static final String ITEM_SHORT_DESC = "item_short_desc";

    public AppnomarsDBHandler(Context context) {
        super(context, AppnomarsDBHandler.DATABASE_NAME, null,
                AppnomarsDBHandler.DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                + AppnomarsDBHandler.TABLE_CONTACTS + "("
                + AppnomarsDBHandler.ITEM_ID + " TEXT ,"
                + AppnomarsDBHandler.ITEM_PRICE + " TEXT ,"
                + AppnomarsDBHandler.ITEM_IMAGE_URL + " TEXT ,"
                + AppnomarsDBHandler.ITEM_TITLE + " TEXT ,"
                + AppnomarsDBHandler.ITEM_SHORT_DESC + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS "
                + AppnomarsDBHandler.TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * Set eaceh vector;
     *
     * @param kudoroBDModel
     */
    public void addNotifyNumber(KudoroBDModel kudoroBDModel) {
        final SQLiteDatabase db = getWritableDatabase();
        final ContentValues values = new ContentValues();

        values.put(AppnomarsDBHandler.ITEM_ID, kudoroBDModel.getId());
        values.put(AppnomarsDBHandler.ITEM_PRICE, kudoroBDModel.getPrice());
        values.put(AppnomarsDBHandler.ITEM_IMAGE_URL, kudoroBDModel.getImage_url());
        values.put(AppnomarsDBHandler.ITEM_TITLE, kudoroBDModel.getTitle());
        values.put(AppnomarsDBHandler.ITEM_SHORT_DESC, kudoroBDModel.getShort_desc());

        // Inserting Row
        db.insert(AppnomarsDBHandler.TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Get all database
     *
     * @return
     */
    public Vector<KudoroBDModel> getAllNotifyNumber() {

        final Vector<KudoroBDModel> contactList = new Vector<KudoroBDModel>();
        // Select All Query
        final String selectQuery = "SELECT  * FROM "
                + AppnomarsDBHandler.TABLE_CONTACTS;

        final SQLiteDatabase db = getWritableDatabase();
        final Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                KudoroBDModel kudoroBDModel = new KudoroBDModel();
                kudoroBDModel.setId(cursor.getString(0));
                kudoroBDModel.setPrice(cursor.getString(1));
                kudoroBDModel.setImage_url(cursor.getString(2));
                kudoroBDModel.setTitle(cursor.getString(3));
                kudoroBDModel.setShort_desc(cursor.getString(4));
                contactList.addElement(kudoroBDModel);
                kudoroBDModel = null;
            } while (cursor.moveToNext());
        }

        db.close(); // Closing database connection
        return contactList;
    }

    /*
     * Deleted signal vecore;
     */
    public void deleteContact(KudoroBDModel kudoroBDModel) {
        final SQLiteDatabase db = getWritableDatabase();
        db.delete(AppnomarsDBHandler.TABLE_CONTACTS,
                AppnomarsDBHandler.ITEM_ID + " = ?",
                new String[]{String.valueOf(kudoroBDModel.getId())});
        db.close();
    }

    /**
     * Edit
     */

    public int updateNotifyNumber(KudoroBDModel kudoroBDModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppnomarsDBHandler.ITEM_ID, kudoroBDModel.getId());
        values.put(AppnomarsDBHandler.ITEM_PRICE, kudoroBDModel.getPrice());
        values.put(AppnomarsDBHandler.ITEM_IMAGE_URL, kudoroBDModel.getImage_url());
        values.put(AppnomarsDBHandler.ITEM_TITLE, kudoroBDModel.getTitle());
        values.put(AppnomarsDBHandler.ITEM_SHORT_DESC, kudoroBDModel.getShort_desc());


        // updating row
        return db.update(AppnomarsDBHandler.TABLE_CONTACTS, values,
                AppnomarsDBHandler.ITEM_ID + " = ?",
                new String[]{String.valueOf(kudoroBDModel.getId())});
    }

    /**
     * Database Delected
     */
    public void delectedNotifyNumber(Context context) {
        context.deleteDatabase(AppnomarsDBHandler.DATABASE_NAME);
    }

    public String ifExist(String videoName) {
        final SQLiteDatabase mDb = getReadableDatabase();
        Cursor cursor = mDb.query(AppnomarsDBHandler.TABLE_CONTACTS,
                new String[]{AppnomarsDBHandler.ITEM_ID},
                AppnomarsDBHandler.ITEM_ID + "=?",
                new String[]{videoName},
                null, null, null, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "not found";
        }
    }

}
