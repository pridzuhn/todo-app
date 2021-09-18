package com.example.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.RecyclerViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;


public class TodoListViewActivity extends AppCompatActivity {

    private static final String TAG = "ITEM";
    private TodoViewModel todoViewModel;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int counter;
    BottomSheet bottomSheet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistview);
        counter = 0;

        bottomSheet = new BottomSheet();
        ConstraintLayout constraintLayout = findViewById(R.id.bottomSheet);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(
                TodoListViewActivity.this.getApplication())
                .create(TodoViewModel.class);

        todoViewModel.getAllTodos().observe(this, todos -> {
            recyclerViewAdapter = new RecyclerViewAdapter(todos);
            recyclerView.setAdapter(recyclerViewAdapter);
        });


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
//            Todo todo = new Todo("Todo " + counter++, "Description", false, false,
//                    Calendar.getInstance().getTime());
//
//            TodoViewModel.insert(todo);
            showBottomSheetDialog();
        });

    }

    private void showBottomSheetDialog() {
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }


}