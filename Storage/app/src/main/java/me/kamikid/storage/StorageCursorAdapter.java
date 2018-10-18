package me.kamikid.storage;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import me.kamikid.storage.data.StorageContract;
import me.kamikid.storage.data.StorageProvider;
import me.kamikid.storage.view.StorageEditor;

/**
 * Created by kidfu on 2018/2/23.
 */

public class StorageCursorAdapter extends CursorAdapter {

    private StorageProvider storageProvider;

    public StorageCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        storageProvider = new StorageProvider();
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        TextView textViewName = (TextView) view.findViewById(R.id.storageItemName);
        TextView textViewAmount = (TextView) view.findViewById(R.id.storageItemAmount);
        TextView textViewPrice = (TextView) view.findViewById(R.id.storageItemPrice);
        Button buttonSale = view.findViewById(R.id.storageItemSaleButton);

        int nameIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME);
        int priceIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE);
        int amountIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT);

        int amountInt = cursor.getInt(amountIndex);

        String name = cursor.getString(nameIndex);
        String price = context.getString(R.string.money_marker) + String.valueOf(cursor.getDouble(priceIndex));
        String amount = context.getString(R.string.amount) + amountInt;

        textViewName.setText(name);
        if (amountInt != 0) {
            textViewAmount.setText(amount);
            buttonSale.setEnabled(true);
        } else {
            textViewAmount.setText(R.string.out_of_stock);
            buttonSale.setEnabled(false);
        }
        textViewPrice.setText(price);


        buttonSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View parent = (View) view.getParent();
                ListView listView = (ListView) parent.getParent().getParent();
                final int position = listView.getPositionForView(parent);
                Uri tempUri = ContentUris.withAppendedId(StorageContract.StorageEntry.CONTENT_URI, getItemId(position));
                String[] projection = {
                        StorageContract.StorageEntry._ID,
                        StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME,
                        StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE,
                        StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT,
                        StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER,
                        StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL,
                        StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE
                };
                Cursor cursor = view.getContext().getContentResolver().query(
                        tempUri,
                        projection,
                        null,
                        null,
                        null
                );

                int idColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME);
                int priceColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE);
                int amountColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT);
                int supplierNameColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER);
                int supplierEmailColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL);
                int supplierPhoneColumnIndex = cursor.getColumnIndex(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE);
                cursor.moveToFirst();
                String currentName = cursor.getString(nameColumnIndex);
                Double currentPrice = cursor.getDouble(priceColumnIndex);
                int currentAmount = cursor.getInt(amountColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierEmail = cursor.getString(supplierEmailColumnIndex);
                String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

                if(--currentAmount<0){
                    Toast.makeText(view.getContext(), "Sorry, no negative amount!", Toast.LENGTH_SHORT).show();
                }else{
                    ContentValues values = new ContentValues();
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_NAME, currentName);
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_PRICE, currentPrice);
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT, currentAmount);
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER, currentSupplierName);
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL, currentSupplierEmail);
                    values.put(StorageContract.StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE, currentSupplierPhone);
                    int rowsUpdate = view.getContext().getContentResolver().update(
                            tempUri,
                            values,
                            null,
                            null
                    );
                    if(rowsUpdate == 0){
                        Toast.makeText(view.getContext(), "Updating Error!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(view.getContext(), "SALE SUCCESSFUL!", Toast.LENGTH_SHORT).show();
                    }
                    cursor.setNotificationUri(view.getContext().getContentResolver(), tempUri);
                }
            }
        });
    }
}

