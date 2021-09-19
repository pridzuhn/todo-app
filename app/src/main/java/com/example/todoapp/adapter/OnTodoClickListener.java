package com.example.todoapp.adapter;

import com.example.todoapp.Todo;

public interface OnTodoClickListener {
    void onTodoClick(Todo todo);

    void onTodoDeleteButtonClick(Todo todo);
}
