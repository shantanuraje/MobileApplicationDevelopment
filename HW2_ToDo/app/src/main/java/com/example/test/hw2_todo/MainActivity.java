package com.example.test.hw2_todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ArrayList<Task> tasksArrayList = new ArrayList<Task>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Todo: ", Integer.toString(tasksArrayList.size()));
        LinearLayout taskDetails = (LinearLayout) findViewById(R.id.taskDetails);
        if (tasksArrayList.size() == 0){
            TextView tasksView = new TextView(this);
            TextView tasksPointer = new TextView(this);
            tasksView.setText("No Tasks");
            tasksPointer.setText("Task 0 of " + tasksArrayList.size());
            taskDetails.addView(tasksView);
            taskDetails.addView(tasksPointer);
        }
        final ImageButton addTask = (ImageButton) findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask();
            }
        });
    }

    public void addTask(){
        Intent addTaskIntent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(addTaskIntent);
    }
}
