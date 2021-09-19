package com.example.todoapp.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.*;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.todoapp.Converter;
import com.example.todoapp.Todo;
import com.example.todoapp.data.TodoDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Todo.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class TodoRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "todo_database";
    public static volatile TodoRoomDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    databaseWriteExecutor.execute(() -> {
                        TodoDAO todoDAO = INSTANCE.todoDAO();
                        todoDAO.deleteAll();
                    });
                }
            };

    public static TodoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDatabase.class, DATABASE_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TodoDAO todoDAO();
}
