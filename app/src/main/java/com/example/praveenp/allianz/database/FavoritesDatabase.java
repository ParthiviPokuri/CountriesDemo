package com.example.praveenp.allianz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by praveenpokuri on 02/10/18.
 */
public class FavoritesDatabase {

    private CountryDatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public FavoritesDatabase(Context c) {
        context = c;
    }

    public FavoritesDatabase open() throws SQLException {
        dbHelper = new CountryDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(CountryFav.COLUMN_COUNTRY, name);
        contentValue.put(CountryFav.COLUMN_INFO, desc);
        database.insert(CountryFav.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { CountryFav.COLUMN_ID, CountryFav.COLUMN_COUNTRY, CountryFav.COLUMN_INFO };
        Cursor cursor = database.query(CountryFav.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CountryFav.COLUMN_COUNTRY, name);
        contentValues.put(CountryFav.COLUMN_INFO, desc);
        int i = database.update(CountryFav.TABLE_NAME, contentValues, CountryFav.COLUMN_COUNTRY + " = " + name, null);
        return i;
    }

    public void delete(String name) {
        database.delete(CountryFav.TABLE_NAME, CountryFav.COLUMN_COUNTRY + " = " + name, null);
    }
}
