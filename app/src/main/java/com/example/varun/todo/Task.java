package com.example.varun.todo;

/**
 * Created by Varun on 3/24/2016.
 */
class Task {
    private String taskToDo;
    private int taskID;
    private String detail;

    public Task(String detail, String taskToDo) {
        this.detail = detail;
        this.taskToDo = taskToDo;
        this.taskID = 0; // Need this for searching through the database
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTaskToDo() {
        return taskToDo;
    }

    public void setTaskToDo(String taskToDo) {
        this.taskToDo = taskToDo;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
