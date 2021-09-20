package com.example.todoapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.adapter.OnTodoClickListener;
import com.example.todoapp.adapter.RecyclerViewAdapter;
import com.example.todoapp.model.SharedViewModel;
import com.example.todoapp.model.TodoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


// for maps reinsert implements OnMapReadyCallback
public class TodoListViewActivity extends AppCompatActivity implements OnTodoClickListener{

    private static final String TAG = "ITEM";
    private TodoViewModel todoViewModel;
    private SharedViewModel sharedViewModel;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private int counter;
    BottomSheetFragment bottomSheetFragment;

    private ListView lstNames;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolistview);
        counter = 0;

        bottomSheetFragment = new BottomSheetFragment();
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

        sharedViewModel = new ViewModelProvider(this)
                .get(SharedViewModel.class);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
          /*Todo todo = new Todo("Todo " + counter++, "Description", false, false,
                  Calendar.getInstance().getTime());
          TodoViewModel.insert(todo);*/
          showBottomSheetDialog();
        });

    }

    private void showBottomSheetDialog() {
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
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
    private void showContacts() {
        // Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            List<String> contacts = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contacts);
            lstNames.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the cursor
        cursor.close();

        return contacts;
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        googleMap.addMarker(new MarkerOptions()
//                .position(new LatLng(0, 0))
//                .title("Marker"));
//    }

}