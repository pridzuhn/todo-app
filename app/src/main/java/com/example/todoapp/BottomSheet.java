package com.example.todoapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.todoapp.model.SharedViewModel;
import com.example.todoapp.model.TodoViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class BottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private ImageButton saveButton;
    // private RadioGroup priorityRadioGroup;
    // private RadioButton selectedRadioButton;
    // private int selectedButtonId;
    private CalendarView calendarView;
    private Date dueDate;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    // private Group calendarGroup;

    public BottomSheet() {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        // calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        // Chip todayChip = view.findViewById(R.id.todo_row_chip); // TODO delete or extend
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedViewModel.getSelectedItem().getValue() != null){
            Todo todo = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(todo.getTask());
            Log.d("MY", "onViewCreated" + todo.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);



        calendarButton.setOnClickListener(view12 -> {
            calendarView.setVisibility(
                     calendarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        calendarView.setOnDateChangeListener((view13, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
        });

        saveButton.setOnClickListener(view1 -> {
            String todo = enterTodo.getText().toString().trim();
            if(!TextUtils.isEmpty(todo) && dueDate != null){
                Todo myTodo = new Todo(todo, "lore ipsum", false, true,
                        dueDate);
                TodoViewModel.insert(myTodo);
            }

        });
    }

    @Override
    public void onClick(View view) {
        // TODO duedates for today tomorrow next week, if wanted (2:10)
    }
}