package com.example.varun.todo;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int N; // global position from list
    private Button delete;
    private Button cancel;
    private boolean dcIsVisible = false;
    private AutoCompleteTextView text;
    private Task tempObject = new Task(null, null);
    private static final int DETAIL_REQUEST = 1;
    private final TaskDatabase taskInfo = new TaskDatabase(this); // only refer to this name
    private TaskAdapter taskAdapter;
    private List<Task> myTaskList = new ArrayList<>();
    private final List<View> selectedViews = new ArrayList<>(); // save view
    private final List<Task> selectedTasks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addTask = (Button) findViewById(R.id.addTask);
        delete = (Button) findViewById(R.id.delete);
        cancel = (Button) findViewById(R.id.cancel_action);
        text = (AutoCompleteTextView) findViewById(R.id.taskFill);
        myTaskList = taskInfo.getTD(); // get data from table
        String[] suggestions = getResources().getStringArray(R.array.task_suggestions);
        ArrayAdapter<String> autoTextAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestions);
        ListView taskList = (ListView) findViewById(R.id.taskList);
        taskAdapter = new TaskAdapter(this, myTaskList);
        text.setAdapter(autoTextAdapter); // set adapters for list view and text view
        taskList.setAdapter(taskAdapter);

        // set on click for add task button
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task object = new Task(null, null);
                object.setTaskToDo(text.getText().toString());
                if (object.getTaskToDo().equals("")) {
                    Toast.makeText(getApplicationContext(), "No task specified!", Toast.LENGTH_SHORT).show();
                } else {
                    text.setText("");
                    myTaskList.add(object);
                    new DbTasks().execute(); // store in database (AsyncTask)
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // on item selected from list
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dcIsVisible) { //
                    delete.setEnabled(true);
                    selectedViews.add(view); // save the view
                    selectedTasks.add(myTaskList.get(position));
                    view.setBackgroundColor(Color.MAGENTA);
                    taskAdapter.notifyDataSetChanged();
                } else {
                    N = position;
                    tempObject = myTaskList.get(position);
                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    i.putExtra("Task Detail", tempObject.getDetail());
                    startActivityForResult(i, DETAIL_REQUEST);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (View view: selectedViews) { // change the color back to the original color
                    view.setBackgroundColor(Color.parseColor("#404040"));
                }
                delete.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                dcIsVisible = false; // delete and cancel buttons not visible
                selectedViews.clear(); // make the two lists empty
                selectedTasks.clear();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Task Deleted";
                for (Task t : selectedTasks) {
                    taskInfo.delete(Integer.toString(t.getTaskID())); // permanently delete from database
                    myTaskList.remove(t);
                    taskAdapter.notifyDataSetChanged();
                }
                for (View view: selectedViews) { // change color of view back to the original color
                    view.setBackgroundColor(Color.parseColor("#404040"));
                }
                delete.setVisibility(View.GONE);
                delete.setEnabled(false);
                cancel.setVisibility(View.GONE);
                dcIsVisible = false;
                if (selectedTasks.size() > 1) {
                    message = "Task's Deleted";
                }
                selectedViews.clear(); // clear out the two lists
                selectedTasks.clear();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
        // just for practice
        private class DbTasks extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) { // delete, retrieve, store
                taskInfo.storeTD(myTaskList);
                return null;
            }
        }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.navigation, menu);
            return super.onCreateOptionsMenu(menu);
        }
        // passing corresponding detail for task to detail activity
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (resultCode == RESULT_OK && requestCode == DETAIL_REQUEST) {
                if (data.hasExtra("Detail")) {
                    myTaskList.get(N).setDetail(data.
                            getExtras().getString("Detail")); // train wreck here I think
                    new DbTasks().execute(); // store in database (AsyncTask)
                }
            }
        }
        // when garbage can is selected
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case R.id.trashcan:
                    delete.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    dcIsVisible = true;
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }
    }
