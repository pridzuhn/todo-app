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
import com.example.todoapp.model.SharedViewModel;
import com.example.todoapp.model.TodoViewModel;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

// for maps reinsert implements OnMapReadyCallback
public class TodoListViewActivity extends AppCompatActivity implements OnTodoClickListener{

    private static final String TAG = "ITEM";
    private TodoViewModel todoViewModel;
    private SharedViewModel sharedViewModel;
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

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);


        todoViewModel = new ViewModelProvider.AndroidViewModelFactory(
                TodoListViewActivity.this.getApplication())
                .create(TodoViewModel.class);

        todoViewModel.getAllTodos().observe(this, todos -> {
            recyclerViewAdapter = new RecyclerViewAdapter(todos, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);


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
    public void onTodoClick(Todo todo) {
        sharedViewModel.selectItem(todo);
        sharedViewModel.setIsEdit(true);
        // Log.d("Click", "onTodoClick: " + todo);
        showBottomSheetDialog();
    }

    @Override
    public void onTodoDeleteButtonClick(Todo todo) {
        Log.d("Click", "onDeleteButtonClick: " + todo);
        TodoViewModel.delete(todo);
        recyclerViewAdapter.notifyDataSetChanged();
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }
}