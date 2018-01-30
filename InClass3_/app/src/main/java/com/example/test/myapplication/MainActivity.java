package com.example.test.myapplication;

import android.content.Intent;
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
    String department; // [1: SIS, 2: CS, 3:BIO, 4: Others]

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
        Integer mood = sb_mood.getProgress();
        Integer deparment_id = rg_department.getCheckedRadioButtonId();
        RadioButton button = findViewById(deparment_id);

        boolean failure = false;

        if(name == null || "".equals(name)) {
            tv_name.setError("Enter a name");
            failure = true;
        }
        if(email == null || "".equals(email) || email.length() < 3 || !email.contains("@")) {
            tv_email.setError("Enter a valid email");
            failure = true;
        }

        if(failure) {
            return;
        }
        String department = button.getText().toString(); // [1: SIS, 2: CS, 3:BIO, 4: Others]

        Student student = new Student(name, email, department, mood);

        Log.d("demo", student.toString());

        Intent intent = new Intent(getBaseContext(), DisplayActivity.class);
        intent.putExtra("student", student);
        startActivity(intent);
    }
}
