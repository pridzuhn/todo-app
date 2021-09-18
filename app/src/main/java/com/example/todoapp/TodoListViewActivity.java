package com.example.todoapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.OnTodoClickListener;
import com.example.todoapp.adapter.RecyclerViewAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class TodoListViewActivity extends AppCompatActivity implements OnTodoClickListener {

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
            recyclerViewAdapter = new RecyclerViewAdapter(todos, this);
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


    @Override
    public void onTodoClick(int adapterPosition, Todo todo) {
        Log.d("Click", "onTodoClick: " + todo);
    }

    @Override
    public void onTodoRadioButtonClick(Todo todo) {
        Log.d("Click", "onRadioButtonClick: " + todo);
        TodoViewModel.delete(todo);
        recyclerViewAdapter.notifyDataSetChanged();
    }
}