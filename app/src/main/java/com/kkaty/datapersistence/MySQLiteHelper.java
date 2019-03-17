package com.kkaty.datapersistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.kkaty.datapersistence.Constants.DATABASE_NAME;
import static com.kkaty.datapersistence.Constants.PASSWORD;
import static com.kkaty.datapersistence.Constants.TABLE_NAME;
import static com.kkaty.datapersistence.Constants.USER_NAME;
import static com.kkaty.datapersistence.Constants.USER_NAME_KEY;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public MySQLiteHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + Constants.TABLE_NAME + "(" + USER_NAME + " TEXT PRIMARY KEY, " + PASSWORD
                + "TEXT)";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        onCreate(db);

    }

    public boolean insertUser(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.USER_NAME, name);
        contentValues.put(Constants.PASSWORD, password);
        db.insert(TABLE_NAME, null, contentValues);
        return true;

    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getUsersByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE " + USER_NAME_KEY + " = " + name, null);
        return res;
    }

    public Cursor getAllUsersNames() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select" + USER_NAME_KEY + " from" + TABLE_NAME, null);
        return res;
    }

}
