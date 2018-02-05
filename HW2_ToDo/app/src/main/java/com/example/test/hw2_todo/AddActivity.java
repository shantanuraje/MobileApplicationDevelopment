package com.example.test.hw2_todo;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        EditText ed_taskDate = (EditText) findViewById(R.id.ed_taskDate);
        ed_taskDate.setKeyListener(null);
//        ed_taskDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DialogFragment df_date = new DatePickerDialog();
//
//            }
//        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        FragmentManager fm = new FragmentManager();
//        newFragment.show(getSupportFragmentManager(), "datePicker");
        newFragment.show(newFragment.getFragmentManager(), "cvxv");
    }
}
