package me.kamikid.storage.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by kidfu on 2018/2/22.
 */

public class StorageProvider extends ContentProvider {
    public static final String LOG_TAG = StorageProvider.class.getSimpleName();

    private static final int STORAGE = 100;

    private static final int STORAGE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(StorageContract.CONTENT_AUTHORITY, StorageContract.PATH_STORAGE, STORAGE);
        sUriMatcher.addURI(StorageContract.CONTENT_AUTHORITY, StorageContract.PATH_STORAGE+ "/#", STORAGE_ID);
    }

    private StorageDbHelper storageDbHelper;

    @Override
    public boolean onCreate() {
        storageDbHelper = new StorageDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase sqLiteDatabase = storageDbHelper.getReadableDatabase();

        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case STORAGE:
                cursor = sqLiteDatabase.query(
                        StorageContract.StorageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case STORAGE_ID:
                selection = StorageContract.StorageEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(
                        StorageContract.StorageEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STORAGE:
                return insertStorage(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STORAGE:
                return updateStorage(uri, contentValues, selection, selectionArgs);
            case STORAGE_ID:
                selection = StorageContract.StorageEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateStorage(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = storageDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STORAGE:
                rowsDeleted = sqLiteDatabase.delete(StorageContract.StorageEntry.TABLE_NAME,
                        s, strings);
                break;
            case STORAGE_ID:
                s = StorageContract.StorageEntry._ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDatabase.delete(StorageContract.StorageEntry.TABLE_NAME,
                        s, strings);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STORAGE:
                return StorageContract.StorageEntry.CONTENT_LIST_TYPE;
            case STORAGE_ID:
                return StorageContract.StorageEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    private Uri insertStorage(Uri uri, ContentValues values) {
        SQLiteDatabase sqLiteDatabase = storageDbHelper.getWritableDatabase();

        String name = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Storage requires an item name");
        }

        Double price = values.getAsDouble(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE);
        if (price == null) {
            throw new IllegalArgumentException("Storage requires an item price");
        }else if(price < 0.0d){
            throw new IllegalArgumentException("Storage requires a current item price");
        }

        Integer amount = values.getAsInteger(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT);
        if (amount == null) {
            throw new IllegalArgumentException("Storage requires an item amount");
        }else if(amount < 0){
            throw new IllegalArgumentException("Storage requires a current item amount");
        }

        String supplierNamer = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER);
        if (supplierNamer == null) {
            throw new IllegalArgumentException("Storage requires a supplier name");
        }

        String supplierEmail = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL);
        if (supplierEmail == null) {
            throw new IllegalArgumentException("Storage requires a supplier email");
        }

        String supplierPhone = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE);
        if(supplierPhone == null){
            throw new IllegalArgumentException("Storage requires a supplier phone");
        }

        long id = sqLiteDatabase.insert(StorageContract.StorageEntry.TABLE_NAME,null,values);

        if(id==-1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private int updateStorage(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = storageDbHelper.getWritableDatabase();

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME)){
            String name = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Storage requires an item name");
            }
        }

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE)){
            Double price = values.getAsDouble(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE);
            if (price == null) {
                throw new IllegalArgumentException("Storage requires an item price");
            }else if(price < 0.0d){
                throw new IllegalArgumentException("Storage requires a current item price");
            }
        }

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT)){
            Integer amount = values.getAsInteger(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT);
            if (amount == null) {
                throw new IllegalArgumentException("Storage requires an item amount");
            }else if(amount < 0){
                throw new IllegalArgumentException("Storage requires a current item amount");
            }
        }

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER)){
            String supplierNamer = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER);
            if (supplierNamer == null) {
                throw new IllegalArgumentException("Storage requires a supplier name");
            }
        }

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL)){
            String supplierEmail = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL);
            if (supplierEmail == null) {
                throw new IllegalArgumentException("Storage requires a supplier email");
            }
        }

        if(values.containsKey(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE)){
            String supplierPhone = values.getAsString(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE);
            if(supplierPhone == null){
                throw new IllegalArgumentException("Storage requires a supplier phone");
            }
        }

        if(values.size()==0){
            return 0;
        }

        int rowsUpdate = sqLiteDatabase.update(StorageContract.StorageEntry.TABLE_NAME,values,selection,selectionArgs);

        if(rowsUpdate!=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdate;
    }
}
