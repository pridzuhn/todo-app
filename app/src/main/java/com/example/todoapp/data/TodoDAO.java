package com.example.todoapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.todoapp.Todo;

import java.util.List;

@Dao
public interface TodoDAO {

    @Insert
    void insertTodo(Todo todo);

    @Query("DELETE FROM todo_table")
    void deleteAll();

    @Query("SELECT * FROM todo_table")
    LiveData<List<Todo>> getTodos();

    @Query("SELECT * FROM todo_table WHERE todo_table.id == :id")
    LiveData<Todo> get(int id);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

}

