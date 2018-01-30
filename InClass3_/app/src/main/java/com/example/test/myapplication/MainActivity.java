package com.example.test.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button submit = (Button) findViewById(R.id.button_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    public void submit(){
        TextView tv_name = findViewById(R.id.et_name);
        TextView tv_email = findViewById(R.id.et_email);
        RadioGroup rg_department = findViewById(R.id.rg_department);
        SeekBar sb_mood = findViewById(R.id.sb_mood);

        String name = tv_name.getText().toString();
        String email = tv_email.getText().toString();
        Integer department = rg_department.getCheckedRadioButtonId(); // [1: SIS, 2: CS, 3:BIO, 4: Others]
        Integer mood = sb_mood.getProgress();

        Student student = new Student(name, email, department.toString(), mood);

        Log.d("demo", student.toString());



    }
}
