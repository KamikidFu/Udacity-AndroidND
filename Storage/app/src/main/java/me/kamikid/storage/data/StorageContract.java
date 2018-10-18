package me.kamikid.storage.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by KAMIKID-Shinelon on 2018/2/20.
 */

public final class StorageContract {
    private StorageContract() {
    }


    public static final String CONTENT_AUTHORITY = "me.kamikid.storage";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STORAGE = "storage";


    public static final class StorageEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STORAGE);

        public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_STORAGE;

        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                "/" + CONTENT_AUTHORITY + "/" + PATH_STORAGE;

        public static final String TABLE_NAME = "storage";

        /**
         * Unique ID number for the storage
         * <p>
         * Type: INTEGER
         */
        public static final String _ID = BaseColumns._ID;

        /**
         * Storage Item name
         * <p>
         * Type: STRING
         */
        public static final String COLUMN_STORAGE_ITEM_NAME = "Name";

        /**
         * Storage item price
         * <p>
         * Type: REAL
         */
        public static final String COLUMN_STORAGE_ITEM_PRICE = "Price";

        /**
         * Storage item amount
         * <p>
         * Type: INTEGER
         */
        public static final String COLUMN_STORAGE_ITEM_AMOUNT = "Amount";

        /**
         * Storage supplier name
         * <p>
         * Type: STRING
         */
        public static final String COLUMN_STORAGE_SUPPLIER = "Supplier";

        /**
         * Storage supplier email
         * <p>
         * Type: STRING
         */
        public static final String COLUMN_STORAGE_SUPPLIER_EMAIL = "Email";

        /**
         * Storage supplier phone
         * <p>
         * Type: STRING
         */
        public static final String COLUMN_STORAGE_SUPPLIER_PHONE = "Phone";

    }
}
