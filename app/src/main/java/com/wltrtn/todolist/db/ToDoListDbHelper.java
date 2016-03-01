package com.wltrtn.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ToDoListDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ToDoList.db";

    public ToDoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ToDoListContract.ToDoListEntry.TABLE_NAME + " (" +
                ToDoListContract.ToDoListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ToDoListContract.ToDoListEntry.COLUMN_NAME_TASK + " TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ToDoListContract.ToDoListEntry.TABLE_NAME);
    }
}
