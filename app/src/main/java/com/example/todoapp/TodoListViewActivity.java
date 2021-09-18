package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class TodoListViewActivity extends AppCompatActivity {

    private static final String TAG = "ITEM";
    private TodoViewModel todoViewModel;
    private FloatingActionButton fab;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistview);
        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(
                TodoListViewActivity.this.getApplication())
                .create(TodoViewModel.class);

        todoViewModel.getAllTodos().observe(this, todos -> {
            for(Todo todo : todos){
                Log.d(TAG, "onCreate: " + todo.getId());
            }
        });
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Todo todo = new Todo("Todo", "Description", false, false,
                    Calendar.getInstance().getTime());

            TodoViewModel.insert(todo);
        });
    }


}