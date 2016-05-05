package com.example.varun.todo;

/**
 * Created by Varun on 3/24/2016.
 */
public class Task {
    private String taskToDo;
    private String detail;

    public Task(String detail, String taskToDo) {
        this.detail = detail;
        this.taskToDo = taskToDo;
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
}
