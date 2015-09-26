package hackerstolz.de.instact.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hackerstolz.de.instact.helper.ContactDbHelper;

/**
 * Created by floatec on 26/09/15.
 */
public class Contact {
    private String name;
    private long id;
    private String xing;
    private String p2pId;
    static Context context;
    static ContactDbHelper mDbHelper = new ContactDbHelper(context);
    private List<Label> labels=new ArrayList<Label>();
    Contact(String name,String xing,String p2pId,List <Label> labels) throws Exception {
        this.name=name;
        this.xing=xing;
        this.p2pId=p2pId;
        this.labels=labels;
        this.save();
    }
    Contact(long id) throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected mdHelper");
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,
                ContactContract.ContactEntry.COLUMN_NAME_P2P_ID,

        };

// How you want the results sorted in the resulting Cursor
        String sortOrder ="";


        Cursor cursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        cursor.moveToFirst();
        this.name = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_NAME)
        );
        this.p2pId = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID)
        );
        this.xing = cursor.getString(
                cursor.getColumnIndexOrThrow(ContactContract.ContactEntry.COLUMN_NAME_XING)
        );
        this.id=id;
    }

    private void save() throws Exception {
        if (mDbHelper==null){
            throw new Exception("Database not connected Mdhelper");
        }
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_ID, id);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_NAME, name);
        values.put(ContactContract.ContactEntry.COLUMN_NAME_P2P_ID, p2pId);

        values.put(ContactContract.ContactEntry.COLUMN_NAME_XING, xing);


// Insert the new row, returning the primary key value of the new row
        id = db.insert(
                ContactContract.ContactEntry.TABLE_NAME,
                null,
                values);
    }
}