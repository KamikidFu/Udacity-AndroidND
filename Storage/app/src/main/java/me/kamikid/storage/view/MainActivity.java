package me.kamikid.storage.view;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import me.kamikid.storage.R;
import me.kamikid.storage.StorageCursorAdapter;
import me.kamikid.storage.data.StorageContract.StorageEntry;
import me.kamikid.storage.data.StorageDbHelper;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton fab;

    private static final int STORAGE_LOADER = 0;

    StorageCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.addStorageItemFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StorageEditor.class);
                startActivity(intent);
            }
        });

        final ListView itemList = (ListView) findViewById(R.id.storageItemsListView);
        View emptyView = (View) findViewById(R.id.emptyView);
        itemList.setEmptyView(emptyView);
        mCursorAdapter = new StorageCursorAdapter(this, null);
        itemList.setAdapter(mCursorAdapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, StorageEditor.class);
                Uri currentStorageUri = ContentUris.withAppendedId(StorageEntry.CONTENT_URI, id);
                intent.setData(currentStorageUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(STORAGE_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertDummyData:
                insertData("Toy", 1.99d, 1, "Taobao", "taobao@com.com", "188888888888");
                return true;
            case R.id.deleteAllStorage:
                deleteAllStorage();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertData(String name, Double price, int amount, String supplierName, String supplierEmail, String supplierPhone) {

        ContentValues values = new ContentValues();
        values.put(StorageEntry.COLUMN_STORAGE_ITEM_NAME, name);
        values.put(StorageEntry.COLUMN_STORAGE_ITEM_PRICE, price);
        values.put(StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT, amount);
        values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER, supplierName);
        values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL, supplierEmail);
        values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE, supplierPhone);

        getContentResolver().insert(StorageEntry.CONTENT_URI, values);
    }

    private void deleteAllStorage() {
        getContentResolver().delete(StorageEntry.CONTENT_URI, null, null);
        Log.d(LOG_TAG, "deleteAllStorage: ALL STORAGE DELETE");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                StorageEntry._ID,
                StorageEntry.COLUMN_STORAGE_ITEM_NAME,
                StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT,
                StorageEntry.COLUMN_STORAGE_ITEM_PRICE,
                StorageEntry.COLUMN_STORAGE_SUPPLIER,
                StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL,
                StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE
        };
        return new CursorLoader(
                this,
                StorageEntry.CONTENT_URI,   /*URI*/
                projection,                 /*Projection*/
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
