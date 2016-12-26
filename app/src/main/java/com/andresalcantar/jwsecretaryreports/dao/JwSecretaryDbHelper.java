package com.andresalcantar.jwsecretaryreports.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.andresalcantar.jwsecretaryreports.models.ServiceGroup;

import static android.content.ContentValues.TAG;

/**
 * Created by andres.alcantar on 25/12/2016.
 */

public class JwSecretaryDbHelper extends SQLiteOpenHelper {

    private static JwSecretaryDbHelper sInstance;

    //Database Info
    private static final String DATABASE_NAME = "jwSecretaryDb";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_PUBLISHERS = "publishers";
    private static final String TABLE_GROUPS = "groups";
    private static final String TABLE_REPORTS = "reports";

    //Publishers Table Columns
    private static final String KEY_PUBLISHER_ID = "id";
    private static final String KEY_PUBLISHER_GROUP_ID_FK = "pubGroupId";
    private static final String KEY_PUBLISHER_NAME = "pubName";
    private static final String KEY_PUBLISHER_EMAIL = "pubEmail";
    private static final String KEY_PUBLISHER_PHONE = "pubPhone";

    //Groups Table Columns
    private static final String KEY_GROUP_ID = "id";
    private static final String KEY_GROUP_NAME = "groupName";

    //Reports Table Columns
    private static final String KEY_REPORTS_ID = "id";
    private static final String KEY_REPORTS_PUBLISHER_ID_FK = "repPubId";
    private static final String KEY_REPORTS_PUBLISHER_TYPE = "repPubType";
    private static final String KEY_REPORTS_YEAR = "repYear";
    private static final String KEY_REPORTS_MOTHS = "repMonth";
    private static final String KEY_REPORTS_COUNT_PUBLICATIONS = "repPublications";
    private static final String KEY_REPORTS_COUNT_VIDEOS = "repVideos";
    private static final String KEY_REPORTS_COUNT_HOURS = "repHours";
    private static final String KEY_REPORTS_COUNT_RETURN_VISITS = "repReturnVisits";
    private static final String KEY_REPORTS_COUNT_STUDIES = "repStudies";

    private JwSecretaryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized JwSecretaryDbHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new JwSecretaryDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_GROUPS_TABLE = "CREATE TABLE " + TABLE_GROUPS +
                "(" +
                KEY_GROUP_ID + "INTEGER PRIMARY KEY, " + //Define Primary Key
                KEY_GROUP_NAME + "TEXT" +
                ")";

        String CREATE_PUBLISHERS_TABLE = "CREATE TABLE " + TABLE_PUBLISHERS +
                "(" +
                KEY_PUBLISHER_ID + "INTEGER PRIMARY KEY, " +
                KEY_PUBLISHER_GROUP_ID_FK + "INTEGER REFERENCES " + TABLE_GROUPS + ", " + //Define Foreign Key
                KEY_PUBLISHER_NAME + "TEXT, " +
                KEY_PUBLISHER_EMAIL + "TEXT, " +
                KEY_PUBLISHER_PHONE + "TEXT " +
                ")";

        String CREATE_REPORTS_TABLE = "CREATE TABLE " + TABLE_REPORTS +
                "(" +
                KEY_REPORTS_ID + "INTEGER PRIMARY KEY, " +
                KEY_REPORTS_PUBLISHER_ID_FK + "INTEGER REFERENCES " + TABLE_PUBLISHERS + ", " +
                KEY_REPORTS_PUBLISHER_TYPE + "TEXT, " +
                KEY_REPORTS_COUNT_PUBLICATIONS + "INTEGER, " +
                KEY_REPORTS_COUNT_VIDEOS + "INTEGER, " +
                KEY_REPORTS_COUNT_HOURS + "REAL, " +
                KEY_REPORTS_COUNT_RETURN_VISITS + "INTEGER, " +
                KEY_REPORTS_COUNT_STUDIES + "INTEGER" +
                ")";

        db.execSQL(CREATE_GROUPS_TABLE);
        db.execSQL(CREATE_PUBLISHERS_TABLE);
        db.execSQL(CREATE_REPORTS_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String dropTable = "DROP TABLE IF EXISTS ";

            db.execSQL(dropTable + TABLE_REPORTS);
            db.execSQL(dropTable + TABLE_PUBLISHERS);
            db.execSQL(dropTable + TABLE_GROUPS);

            onCreate(db);
        }
    }

    public long addGroup(ServiceGroup group) {
        SQLiteDatabase db = getWritableDatabase();
        long groupId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_GROUP_NAME, group.getGroupName());

            // First try to update the group in case the group already exists in the database
            // This assumes groupNames are unique
            int rows = db.update(TABLE_GROUPS, values, KEY_GROUP_NAME + "= ?", new String[]{group.getGroupName()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String groupSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_GROUP_ID, TABLE_GROUPS, KEY_GROUP_NAME);
                Cursor cursor = db.rawQuery(groupSelectQuery, new String[]{String.valueOf(group.getGroupName())});

                try {
                    if (cursor.moveToFirst()) {
                        groupId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // group with this groupName did not already exist, so insert new group
                groupId = db.insertOrThrow(TABLE_GROUPS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update group");
        } finally {
            db.endTransaction();
        }
        return groupId;
    }
}