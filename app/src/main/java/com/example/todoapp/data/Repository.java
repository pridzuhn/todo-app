package com.example.todoapp.data;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.todoapp.Todo;
import com.example.todoapp.util.TodoRoomDatabase;
import java.util.List;

public class Repository {
    private final TodoDAO todoDAO;
    private final LiveData<List<Todo>> allTodos;

    public Repository(Application application) {
        TodoRoomDatabase database = TodoRoomDatabase.getDatabase(application);
        todoDAO = database.todoDAO();
        allTodos = todoDAO.getTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public void insert(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDAO.insertTodo(todo));
    }

    public LiveData<Todo> get(int id) {
        return todoDAO.get(id);
    }

    public void update(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDAO.update(todo));
    }

    public void delete(Todo todo) {
        TodoRoomDatabase.databaseWriteExecutor.execute(() -> todoDAO.delete(todo));
    }
}
