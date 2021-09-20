package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapp.R;
import com.example.todoapp.Todo;
import com.example.todoapp.util.Utils;
import com.google.android.material.chip.Chip;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Todo> taskList;
    private final OnTodoClickListener todoClickListener;

    public RecyclerViewAdapter(List<Todo> taskList, OnTodoClickListener todoClickListener) {
        this.taskList = taskList;
        this.todoClickListener = todoClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = taskList.get(position);
        String formatted = Utils.formatDate(todo.getDueDate());
        holder.task.setText(todo.getTask());
        holder.todayChip.setText(formatted);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public AppCompatImageButton todoDeleteButton;
        public AppCompatTextView task;
        public Chip todayChip;

        OnTodoClickListener onTodoClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            todoDeleteButton = itemView.findViewById(R.id.todo_delete_button);
            task = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            this.onTodoClickListener = todoClickListener;
            itemView.setOnClickListener(this);
            todoDeleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Todo currTodo = taskList.get(getAdapterPosition());
            int id = view.getId();
            if (id == R.id.todo_row_layout) {
                onTodoClickListener.onTodoClick(currTodo);
            } else if (id == R.id.todo_delete_button) {
                currTodo = taskList.get(getAdapterPosition());
                onTodoClickListener.onTodoDeleteButtonClick(currTodo);
            }
        }
    }
}