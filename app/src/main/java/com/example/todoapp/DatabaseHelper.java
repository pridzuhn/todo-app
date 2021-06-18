package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODO_TABLE";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TASK";
    private static final String COL_3 = "DESCRIPTION";
    private static final String COL_4 = "IS_FINISHED";
    private static final String COL_5 = "IS_FAVORITE";
    private static final String COL_6 = "DUE_DATE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT, " +
                "DESCRIPTION TEXT, IS_FINISHED INTEGER, IS_FAVORITE INTEGER, DUE_DATE LONG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert a new task
     */
    public void insertTask(TodoModel model){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, model.getTask());
        values.put(COL_3, model.getDescription());
        values.put(COL_4, model.getIsFinished());
        values.put(COL_5, model.getIsFavorite());
        values.put(COL_6, model.getDueDate());
        db.insert(TABLE_NAME, null, values);
    }

    /**
     * update task name
     */
    public void updateTask(int id, String task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateDescription(int id, String description){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, description);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    /**
     * TODO
     * implement update and delete isFinished
     */

    /**
     * TODO
     * implement update and delete isFavorite
     */

    /**
     * TODO
     * implement update and delete dueDate
     */

    public void deleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME,"ID=?", new String[]{String.valueOf(id)});
    }

    public List<TodoModel> getAllTasks(){
        db = this.getWritableDatabase();
        Cursor cursor = null;
        List<TodoModel> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME , null , null , null , null , null , null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        TodoModel task = new TodoModel();
                        task.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COL_2)));
                        task.setDescription(cursor.getString(cursor.getColumnIndex(COL_3)));
                        task.setIsFinished(cursor.getInt(cursor.getColumnIndex(COL_4)));
                        task.setIsFavorite(cursor.getInt(cursor.getColumnIndex(COL_5)));
                        task.setDueDate(cursor.getLong(cursor.getColumnIndex(COL_6)));
                        modelList.add(task);

                    }while (cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }
        return modelList;
    }
}
