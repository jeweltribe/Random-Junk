package com.example.varun.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varun on 3/31/2016.
 */
public class TaskDatabase extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "task";
    private static final String COLUMN_TASK_ID = "taskid";
    private static final String COLUMN_TASK_TEXT = "task";
    private static final String COLUMN_DETAIL_TEXT = "detail";

    public TaskDatabase(Context context) {
        super(context, "taskDetail.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, COLUMN_TASK_ID, COLUMN_TASK_TEXT, COLUMN_DETAIL_TEXT);
        db.execSQL(sql); // create database with this line
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void storeTD(List<Task> todo) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        int id = 0;
        for (Task t: todo) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TASK_ID, id);
            values.put(COLUMN_TASK_TEXT, t.getTaskToDo());
            values.put(COLUMN_DETAIL_TEXT, t.getDetail());
            db.insert(TABLE_NAME, null, values); // insert the ContentValue into the database

            id++; // increment the database
        }

        db.close(); // always close
    }

    public List<Task> getTD() {
        List<Task> taskList = new ArrayList<>(); // pointless just pass the list as an argument
        SQLiteDatabase db = getReadableDatabase();
        String sql = String.format("SELECT * from %s ORDER BY %s", TABLE_NAME, COLUMN_TASK_ID);
        Cursor cursor = db.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                String detail = cursor.getString(1);// wat?
                String task = cursor.getString(2);
                taskList.add(new Task(task, detail));
            }
        } catch (SQLiteException e) { // catch some exception, don't know?
            Log.d("TODO","Exception", e);
        }
        cursor.close();
        db.close();


        return taskList;
    }
}


