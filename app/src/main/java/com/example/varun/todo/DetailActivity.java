package com.example.varun.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ${Varun} on ${3/27/2016}.
 */
public class DetailActivity extends Activity {

    private EditText tDetails;
    private String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tDetails = (EditText) findViewById(R.id.details);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            detail = extras.getString("Task Detail");
            tDetails.setText(detail);
            try { // not sure if this is right
                tDetails.setSelection(detail.length());
            } catch (NullPointerException n) {
                Log.d("TODO: ", "Exception", n);
            }
        }
    }

    @Override
    public void onBackPressed() {
        String compare = tDetails.getText().toString();
        if (!compare.equals(detail)) {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Detail", tDetails.getText().toString());
            setResult(RESULT_OK, returnIntent);
            Toast.makeText(getApplicationContext(), "Detail saved", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
