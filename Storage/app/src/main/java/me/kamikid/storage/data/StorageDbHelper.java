package me.kamikid.storage.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.kamikid.storage.data.StorageContract.StorageEntry;

/**
 * Created by KAMIKID-Shinelon on 2018/2/20.
 */

public class StorageDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = StorageDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "storage.db";

    private static final int DATABASE_VERSION = 1;

    /**
     * Constuctor for {@link StorageDbHelper}
     */
    public StorageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STORAGE_TABLE = "CREATE TABLE " + StorageEntry.TABLE_NAME + " ("
                + StorageEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StorageEntry.COLUMN_STORAGE_ITEM_NAME + " TEXT NOT NULL, "
                + StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT + " INTEGER NOT NULL DEFAULT 0, "
                + StorageEntry.COLUMN_STORAGE_ITEM_PRICE + " REAL NOT NULL, "
                + StorageEntry.COLUMN_STORAGE_SUPPLIER + " TEXT NOT NULL, "
                + StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL + " TEXT NOT NULL,"
                + StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_STORAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
