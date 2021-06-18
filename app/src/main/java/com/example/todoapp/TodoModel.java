package com.example.todoapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.io.Serializable;


/**
 * A single data item
 *
 * @author Joern Kreutel
 * @author Martin Schafföner
 *
 */
@Entity

public class TodoModel implements Serializable {

    /**
     * some static id assignment
     */
    private static int ID = 0;

    private static final long serialVersionUID = -7481912314472891511L;



    /**
     * the fields
     */
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String task;
    private String description;
    private int isFinished;
    private int isFavorite;
    private long dueDate;


    public void setTask(String task) {
        this.task = task;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsFinished(int isFinished) { this.isFinished = isFinished; }

    public void setIsFavorite(int isFavorite) { this.isFavorite = isFavorite; }

    public void setDueDate(long dueDate) { this.dueDate = dueDate; }


    public TodoModel() {
        // TODO Auto-generated constructor stub
    }

    public String getTask() {
        return this.task;
    }

    public String getDescription() {
        return this.description;
    }

    public int getIsFinished() { return this.isFinished; }

    public int getIsFavorite() { return this.isFavorite; }

    public long getDueDate() { return this.dueDate; }


    public void updateFrom(TodoModel item) {
        this.setTask(item.getTask());
        this.setDescription(item.getDescription());
        this.setIsFinished(item.getIsFinished());
        this.setIsFavorite(item.getIsFavorite());
        this.setDueDate(item.getDueDate());
    }

    public String toString() {
        return "{DataItem " + this.getId() + " " + this.getTask() + " " + this.getDescription() + " " + this.getDueDate() + "}";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}

