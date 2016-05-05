package com.example.varun.todo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Varun on 3/30/2016.
 */
public class TaskAdapter extends ArrayAdapter<Task> {
    TaskHolder holder;
    int mLayoutResourceId;
    List<Task> mData;
    Context context;
    boolean checkBoxFlag; // default false, if true then setVisibility(View.Visible) for checkbox
    boolean deleteFlag;
    boolean isChecked;


    public TaskAdapter(Context context, int resource, List<Task> objects) {
        super(context, resource, objects);
        this.mLayoutResourceId = resource;
        this.mData = objects;
        this.context = context;
        this.checkBoxFlag = false;
        this.deleteFlag = false;
        this.isChecked = false;
    }


    @Override
    public Task getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(mLayoutResourceId, parent, false); // sorta understand this part
            holder = new TaskHolder(); // new instance of holder
            holder.taskView = (TextView) convertView.findViewById(R.id.task); // set task view
            //holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox_delete);
            convertView.setTag(holder);
        } else {
            holder = (TaskHolder) convertView.getTag(); // reuse a listView that isn't on screen
        }
        Task task = mData.get(position);
        holder.taskView.setText(task.getTaskToDo());

       /* // flag for checkBox
        if (checkBoxFlag == true) {
            //holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            //holder.checkBox.setChecked(false);
            //holder.checkBox.setVisibility(View.GONE);
        }

        if (isChecked) {
            holder.checkBox.setChecked(true);
        }*/

        // want to enable the delete button if a check box is checked
        /*if(holder.checkBox.isChecked()) {
            deleteFlag = true;
        }*/

        /*holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFlag = true;
                notifyDataSetChanged();
            }
        });*/

        return convertView; // return the changed view or maybe new view, it depends.
    }

    private static class TaskHolder {
        TextView taskView;
        //CheckBox checkBox;
    };


}
