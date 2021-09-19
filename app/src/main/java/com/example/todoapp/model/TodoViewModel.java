package com.example.todoapp.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.todoapp.Todo;
import com.example.todoapp.data.Repository;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    public static Repository repository;
    public final LiveData<List<Todo>> allTodos;

    public TodoViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTodos = repository.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodos() {
        return allTodos;
    }

    public static void insert(Todo todo) {
        repository.insert(todo);
    }

    public LiveData<Todo> get(int id) {
        return repository.get(id);
    }

    public static void update(Todo todo) {
        repository.update(todo);
    }

    public static void delete(Todo todo) {
        repository.delete(todo);
    }


}
