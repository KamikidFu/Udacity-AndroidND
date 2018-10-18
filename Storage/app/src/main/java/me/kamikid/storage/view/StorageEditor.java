package me.kamikid.storage.view;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import me.kamikid.storage.R;
import me.kamikid.storage.data.StorageContract.StorageEntry;

public class StorageEditor extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = StorageEditor.class.getSimpleName();

    public EditText editTextItemName;
    public EditText editTextItemPrice;
    public EditText editTextItemAmount;
    public EditText editTextSupplierName;
    public EditText editTextSupplierEmail;
    public EditText editTextSupplierPhone;

    public ImageButton ibOrderByEmail;
    public ImageButton ibOrderByPhone;

    public Button buttonUpdateAddStorage;
    public Button buttonDeleteStorage;
    public Button buttonAddAmount;
    public Button buttonMinusAmount;

    private static final int EXISTING_STORAGE_LOADER = 0;

    private Uri mCurrentStorageUri;

    private boolean mStorageHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mStorageHasChanged = true;
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        if (!mStorageHasChanged) {
            super.onBackPressed();
            return;
        }
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_editor);

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemAmount = findViewById(R.id.editTextItemAmount);
        editTextSupplierName = findViewById(R.id.editTextSupplierName);
        editTextSupplierEmail = findViewById(R.id.editTextSupplierEmail);
        editTextSupplierPhone = findViewById(R.id.editTextSupplierPhone);

        ibOrderByEmail = findViewById(R.id.imageButtonEmailOrder);
        ibOrderByPhone = findViewById(R.id.imageButtonPhoneOrder);

        buttonUpdateAddStorage = findViewById(R.id.buttonUpdateAddStorage);
        buttonDeleteStorage = findViewById(R.id.buttonDeleteStorage);
        buttonAddAmount = findViewById(R.id.buttonAmountAdd);
        buttonMinusAmount = findViewById(R.id.buttonAmountMinus);


        editTextItemName.setOnTouchListener(mTouchListener);
        editTextItemAmount.setOnTouchListener(mTouchListener);
        editTextItemPrice.setOnTouchListener(mTouchListener);
        editTextSupplierName.setOnTouchListener(mTouchListener);
        editTextSupplierEmail.setOnTouchListener(mTouchListener);
        editTextSupplierPhone.setOnTouchListener(mTouchListener);
        buttonMinusAmount.setOnTouchListener(mTouchListener);
        buttonAddAmount.setOnTouchListener(mTouchListener);

        Intent intent = getIntent();
        mCurrentStorageUri = intent.getData();

        if (mCurrentStorageUri == null) {
            setTitle(getString(R.string.editor_activity_title_new_item));
            ibOrderByEmail.setVisibility(View.GONE);
            ibOrderByPhone.setVisibility(View.GONE);
            buttonDeleteStorage.setVisibility(View.GONE);
        } else {
            setTitle(getString(R.string.editor_activity_title_edit_item));
            getLoaderManager().initLoader(EXISTING_STORAGE_LOADER, null, this);
        }

        buttonUpdateAddStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem();
            }
        });

        buttonDeleteStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        buttonAddAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = editTextItemAmount.getText().toString().trim();
                if (amount.isEmpty()) {
                    return;
                }
                int amountInt = Integer.parseInt(amount);
                editTextItemAmount.setText(String.valueOf(++amountInt));
            }
        });

        buttonMinusAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = editTextItemAmount.getText().toString().trim();
                if (amount.isEmpty()) {
                    return;
                }
                int amountInt = Integer.parseInt(amount);
                if (--amountInt < 0) {
                    Toast.makeText(StorageEditor.this, "Sorry, no negative amount!", Toast.LENGTH_SHORT).show();
                } else {
                    editTextItemAmount.setText(String.valueOf(amountInt));
                }
            }
        });
    }

    private void deleteItem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_the_storage);
        builder.setPositiveButton(R.string.delete_item, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (mCurrentStorageUri != null) {
                    int rowsDeleted = getContentResolver().delete(
                            mCurrentStorageUri,
                            null,
                            null
                    );
                    if (rowsDeleted == 0) {
                        Toast.makeText(StorageEditor.this, "Deleting Error!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(StorageEditor.this, "Storage Deleted!", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void updateItem() {
        try {
            String itemName = editTextItemName.getText().toString().trim();
            Double itemPrice = 0.0d;
            if (doubleTryParse(editTextItemPrice.getText().toString())) {
                itemPrice = Double.parseDouble(editTextItemPrice.getText().toString());
            } else {
                Toast.makeText(this, "Is your input of number right?", Toast.LENGTH_SHORT).show();
                return;
            }
            int itemAmount = Integer.parseInt(editTextItemAmount.getText().toString());
            String supplierName = editTextSupplierName.getText().toString().trim();
            String supplierEmail = editTextSupplierEmail.getText().toString().trim();
            String supplierPhone = editTextSupplierPhone.getText().toString().trim();

            /*Email check regex is retried from http://emailregex.com/*/
            String emailCheck = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

            if (itemName.isEmpty() ||
                    itemPrice < 0 ||
                    itemAmount < 0 ||
                    supplierName.isEmpty() ||
                    supplierEmail.isEmpty() ||
                    supplierPhone.isEmpty()) {
                Toast.makeText(this, "Please input all the information!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!supplierEmail.matches(emailCheck)) {
                Toast.makeText(this, "Invalid Email address!", Toast.LENGTH_SHORT).show();
                return;
            }


            ContentValues values = new ContentValues();
            values.put(StorageEntry.COLUMN_STORAGE_ITEM_NAME, itemName);
            values.put(StorageEntry.COLUMN_STORAGE_ITEM_PRICE, itemPrice);
            values.put(StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT, itemAmount);
            values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER, supplierName);
            values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL, supplierEmail);
            values.put(StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE, supplierPhone);

            if (mCurrentStorageUri == null) {
                Uri newUri = getContentResolver().insert(
                        StorageEntry.CONTENT_URI,
                        values
                );
                if (newUri == null) {
                    Toast.makeText(this, "Saving Error!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Storage Saved!", Toast.LENGTH_SHORT).show();
                }
            } else {
                int rowsUpdate = getContentResolver().update(
                        mCurrentStorageUri,
                        values,
                        null,
                        null
                );
                if (rowsUpdate == 0) {
                    Toast.makeText(this, "Updating Error!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Storage Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            Log.e(LOG_TAG, "ERROR of Information Parsing!");
        } catch (Exception e) {
            Log.e(LOG_TAG, "ERROR!");
        }
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                StorageEntry._ID,
                StorageEntry.COLUMN_STORAGE_ITEM_NAME,
                StorageEntry.COLUMN_STORAGE_ITEM_PRICE,
                StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT,
                StorageEntry.COLUMN_STORAGE_SUPPLIER,
                StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL,
                StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE
        };

        return new CursorLoader(
                this,
                mCurrentStorageUri,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(StorageEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_ITEM_NAME);
            int priceColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_ITEM_PRICE);
            int amountColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_ITEM_AMOUNT);
            int supplierNameColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_SUPPLIER);
            int supplierEmailColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_SUPPLIER_EMAIL);
            int supplierPhoneColumnIndex = cursor.getColumnIndex(StorageEntry.COLUMN_STORAGE_SUPPLIER_PHONE);

            //int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            Double currentPrice = cursor.getDouble(priceColumnIndex);
            int currentAmount = cursor.getInt(amountColumnIndex);
            String currentSupplierName = cursor.getString(supplierNameColumnIndex);
            String currentSupplierEmail = cursor.getString(supplierEmailColumnIndex);
            String currentSupplierPhone = cursor.getString(supplierPhoneColumnIndex);

            editTextItemName.setText(currentName);
            editTextItemPrice.setText(String.valueOf(currentPrice));
            editTextItemAmount.setText(String.valueOf(currentAmount));
            editTextSupplierName.setText(currentSupplierName);
            editTextSupplierEmail.setText(currentSupplierEmail);
            editTextSupplierPhone.setText(currentSupplierPhone);
        }

        ibOrderByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToDial = new Intent(Intent.ACTION_DIAL);
                intentToDial.setData(Uri.parse("tel:" + editTextSupplierPhone.getText().toString().trim()));
                startActivity(intentToDial);
            }
        });

        ibOrderByEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToEmail = new Intent(Intent.ACTION_SENDTO);
                intentToEmail.setData(Uri.parse("mailto:" + editTextSupplierEmail.getText().toString().trim()));
                intentToEmail.putExtra(Intent.EXTRA_SUBJECT, "[ORDER]" + editTextItemName.getText().toString().trim());
                startActivity(intentToEmail);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        editTextItemName.setText("");
        editTextItemPrice.setText(String.valueOf(""));
        editTextItemAmount.setText(String.valueOf(""));
        editTextSupplierName.setText("");
        editTextSupplierEmail.setText("");
        editTextSupplierPhone.setText("");

    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard your changes and quit editing?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private boolean doubleTryParse(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
