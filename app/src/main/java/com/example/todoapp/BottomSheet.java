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
import com.example.todoapp.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import java.util.Calendar;
import java.util.Date;

public class BottomSheet extends BottomSheetDialogFragment {

    private EditText enterTodo;
    private EditText descTodo;
    private ImageButton calendarButton;
    private ImageButton priorityButton;
    private ImageButton saveButton;
    private CalendarView calendarView;
    Calendar calendar = Calendar.getInstance();
    private SharedViewModel sharedViewModel;
    private boolean isEdit;

    private Date dueDate;
    private String description = "Todo Description Text";
    private boolean isFinished = false;
    private boolean isFavourite = false;

    public BottomSheet() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        calendarView = view.findViewById(R.id.calendar_view);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        descTodo = view.findViewById(R.id.desc_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Todo todo = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(todo.getTask());
            descTodo.setText(todo.getDescription());
            Log.d("MY", "onViewCreated" + todo.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        priorityButton.setOnClickListener(view14 -> {
            Utils.hideSoftKeyboard(view14);

            if (priorityButton.getVisibility() == View.VISIBLE) {
                priorityButton.setVisibility(View.GONE);
                isFavourite = true;
            } else {
                priorityButton.setVisibility(View.VISIBLE);
                isFavourite = false;
            }


        });

        calendarButton.setOnClickListener(view12 -> {
            calendarView.setVisibility(
                    calendarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideSoftKeyboard(view12);
        });

        calendarView.setOnDateChangeListener((view13, year, month, dayOfMonth) -> {
            Utils.hideSoftKeyboard(view13);
            calendar.clear();
            calendar.set(year, month, dayOfMonth);
            dueDate = calendar.getTime();
        });

        saveButton.setOnClickListener(view1 -> {
            String todo = enterTodo.getText().toString().trim();
            String description = descTodo.getText().toString().trim();

            if (!TextUtils.isEmpty(todo) && dueDate != null) {
                Todo myTodo = new Todo(todo, description, false, isFavourite,
                        dueDate);

                if (isEdit) {
                    Todo updateTodo = sharedViewModel.getSelectedItem().getValue();
                    updateTodo.setTask(todo);
                    updateTodo.setDescription(todo);
                    TodoViewModel.update(updateTodo);
                    sharedViewModel.setIsEdit(false);
                } else {
                    TodoViewModel.insert(myTodo);
                }

                enterTodo.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }
            }
            //    else {Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG).show();}

        });
    }


}