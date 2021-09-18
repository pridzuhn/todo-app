package com.example.todoapp;

import java.util.Date;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class Todo {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "task")
    public String task;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "finished")
    public boolean isFinished;

    @ColumnInfo(name = "favorite")
    public boolean isFavorite;

    @ColumnInfo(name = "due_date")
    public Date dueDate;

    public Todo(String task, String description, boolean isFinished, boolean isFavorite, Date dueDate) {
        this.task = task;
        this.description = description;
        this.isFinished = isFinished;
        this.isFavorite = isFavorite;
        this.dueDate = dueDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", task='" + task + '\'' +
                ", description='" + description + '\'' +
                ", isFinished=" + isFinished +
                ", isFavorite=" + isFavorite +
                ", dueDate=" + dueDate +
                '}';
    }
}
