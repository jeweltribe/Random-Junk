package com.example.varun.todo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public int N; // global size of list
    Button addTask;
    Button delete;
    Button cancel;
    boolean dcIsVisible = false;
    AutoCompleteTextView text;
    ArrayAdapter<String> autoTextAdapter;
    String[] suggestions;
    Task tempObject = new Task(null, null);
    static final int DETAIL_REQUEST = 1;
    private TaskDatabase stupidVille = new TaskDatabase(this); // I am an idiot
    ListView taskList;
    TaskAdapter taskAdapter;
    List<Task> myTaskList = new ArrayList<>();

    //CheckBox checkbox;
    //LayoutInflater inflater;
    //View view2;

    static MainActivity activityA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityA = this;

        addTask = (Button) findViewById(R.id.addTask);
        delete = (Button) findViewById(R.id.delete);
        cancel = (Button) findViewById(R.id.cancel_action);
        text = (AutoCompleteTextView) findViewById(R.id.taskFill);
        myTaskList = stupidVille.getTD();

        suggestions = getResources().getStringArray(R.array.task_suggestions);
        autoTextAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,suggestions);


        taskList = (ListView) findViewById(R.id.taskList);
        taskAdapter = new TaskAdapter(this, R.layout.row, myTaskList);

        //inflater = getLayoutInflater();
        //view2 = inflater.inflate(R.layout.row, null);
        //checkbox = (CheckBox) view2.findViewById(R.id.checkbox_delete);


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
                    stupidVille.storeTD(myTaskList);
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // on item selected from list
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dcIsVisible) {
                    delete.setEnabled(true);
                    view.setBackgroundColor(Color.MAGENTA);
                } else {
                    N = position;
                    tempObject = myTaskList.get(position);
                    Intent i = new Intent(view.getContext(), DetailActivity.class);
                    i.putExtra("Task Detail", tempObject.getDetail());
                    startActivityForResult(i, DETAIL_REQUEST);
                }
            }
        });


        // enable the delete button if you check a box
        /*if (taskAdapter.deleteFlag) {
            delete.setEnabled(true);
        } else {
            delete.setEnabled(false);
        }*/

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                dcIsVisible = false;
                //taskAdapter.checkBoxFlag = false;
                //taskAdapter.notifyDataSetChanged();
            }
        });
    }

    // this is for filling the action bar with the icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    // passing corresponding detail for task to detail activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == DETAIL_REQUEST) {
            if (data.hasExtra("Detail")) {
                myTaskList.get(N).setDetail(data.
                        getExtras().getString("Detail")); // train wreck here I think
                stupidVille.storeTD(myTaskList);

            }
        }
    }


    // when an item from the action bar is selected
   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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




    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }
}

