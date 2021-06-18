package com.example.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TodoAdapter extends ArrayAdapter<TodoModel> {
    public TodoAdapter(Context context, List<TodoModel> todos)
    {
        super(context, 0, todos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TodoModel todo = getItem(position);
//        if(convertView == null)
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout)
        return super.getView(position, convertView, parent);
    }
}
