package com.example.varun.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Varun on 3/30/2016.
 */
class TaskAdapter extends ArrayAdapter<Task> {
    private final int mLayoutResourceId;
    private final List<Task> mData;
    private final Context context;

    public TaskAdapter(Context context, List<Task> objects) {
        super(context, R.layout.row, objects);
        this.mLayoutResourceId = R.layout.row;
        this.mData = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(mLayoutResourceId, parent, false); // sorta understand this part
            holder = new TaskHolder(); // new instance of holder
            holder.taskView = (TextView) convertView.findViewById(R.id.task); // set task view
            convertView.setTag(holder);
        } else {
            holder = (TaskHolder) convertView.getTag(); // reuse a listView that isn't on screen
        }
        Task task = mData.get(position);
        holder.taskView.setText(task.getTaskToDo());
        return convertView; // return the changed view or maybe new view, it depends.
    }

    private static class TaskHolder {
        TextView taskView;
    }

}
