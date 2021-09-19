package com.example.todoapp.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todoapp.Todo;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Todo> selectedItem = new MutableLiveData<>();
    private boolean isEdit;

    public void selectItem(Todo todo) {
        selectedItem.setValue(todo);
    }

    public LiveData<Todo> getSelectedItem() {
        return selectedItem;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }
}
