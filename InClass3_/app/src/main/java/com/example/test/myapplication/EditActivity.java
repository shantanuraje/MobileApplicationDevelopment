/**
 * Assignment 3
 * File name: EditActivity.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
package com.example.test.myapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private int id;
    private int requestCodeNumber;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //get request code of field requested to be edited
        final Integer requestCode = getIntent().getExtras().getInt("request_code");
        requestCodeNumber = requestCode;
        String value = getIntent().getExtras().getString("value");
        //get defined linear layout to dynamically insert views into it.
        LinearLayout edit_layout = (LinearLayout) findViewById(R.id.edit_layout);

        // insert respective view based on request
        if(requestCode == DisplayActivity.editCode[0]) {
            TextView textView = new TextView(getBaseContext());
            textView.setText(R.string.student_name);
            edit_layout.addView(textView);
            EditText name = new EditText(getBaseContext());
            view = name;
            name.setText(value);
            edit_layout.addView(name);
        } else if(requestCode == DisplayActivity.editCode[1]) {
            TextView textView = new TextView(getBaseContext());
            textView.setText(R.string.student_email);
            edit_layout.addView(textView);
            EditText email = new EditText(getBaseContext());
            view = email;
            email.setText(value);
            edit_layout.addView(email);
        } else if (requestCode == DisplayActivity.editCode[2]){
            TextView textView = new TextView(getBaseContext());
            textView.setText(R.string.student_department);
            edit_layout.addView(textView);
            RadioGroup rg_department =  new RadioGroup(getBaseContext());
            view = rg_department;
            RadioButton rb_edit_1 = new RadioButton(rg_department.getContext());
            rb_edit_1.setText(getResources().getText(R.string.department_1));
            RadioButton rb_edit_2 = new RadioButton(rg_department.getContext());
            rb_edit_2.setText(getResources().getText(R.string.department_2));
            RadioButton rb_edit_3 = new RadioButton(rg_department.getContext());
            rb_edit_3.setText(getResources().getText(R.string.department_3));
            RadioButton rb_edit_4 = new RadioButton(rg_department.getContext());
            rb_edit_4.setText(getResources().getText(R.string.department_4));
            rg_department.addView(rb_edit_1);
            rg_department.addView(rb_edit_2);
            rg_department.addView(rb_edit_3);
            rg_department.addView(rb_edit_4);
            if(value.equals(getResources().getText(R.string.department_4))) {
                ((RadioButton)rg_department.getChildAt(3)).setChecked(true);
            }
            if(value.equals(getResources().getText(R.string.department_3))) {
                ((RadioButton)rg_department.getChildAt(2)).setChecked(true);
            }
            if(value.equals(getResources().getText(R.string.department_2))) {
                ((RadioButton)rg_department.getChildAt(1)).setChecked(true);
            }
            if(value.equals(getResources().getText(R.string.department_1))) {
                ((RadioButton)rg_department.getChildAt(0)).setChecked(true);
            }
            edit_layout.addView(rg_department);
        } else if (requestCode == DisplayActivity.editCode[3]){
            TextView textView = new TextView(getBaseContext());
            textView.setText(R.string.mood);
            edit_layout.addView(textView);
            TextView moodValue = new TextView(getBaseContext());
            moodValue.setId(R.id.tv_mood_value);
            moodValue.setText(value);
            edit_layout.addView(moodValue);
            SeekBar sb_mood = new SeekBar(getBaseContext());
            view = sb_mood;
            sb_mood.setProgress(Integer.parseInt(value));
            edit_layout.addView(sb_mood);

            sb_mood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // TODO Auto-generated method stub
                    TextView moodValue = findViewById(R.id.tv_mood_value);
                    moodValue.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }
            });
        }

        Button button = new Button(getBaseContext());
        //button.setLayoutParams(params);
        button.setText(getResources().getText(R.string.save));

        edit_layout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(requestCodeNumber == DisplayActivity.editCode[2]) {
                    Integer deparment_id = ((RadioGroup) view).getCheckedRadioButtonId();
                    RadioButton button = findViewById(deparment_id);
                    intent.putExtra("value", button.getText());
                } else if(requestCodeNumber == DisplayActivity.editCode[3]) {
                    intent.putExtra("value", ((SeekBar) view).getProgress());
                } else if(requestCodeNumber == DisplayActivity.editCode[1])  {
                    String value = ((EditText) view).getText().toString();
                    if(value == null || "".equals(value) || value.length() < 3 || !value.contains("@") || !Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
                        ((EditText) view).setError("Enter a valid email");
                        Toast.makeText(getBaseContext(), "Enter a valid email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent.putExtra("value", value);
                } else {
                    String value = ((EditText) view).getText().toString();
                    if(value == null || "".equals(value)) {
                        ((EditText)view).setError("Enter a valid name");
                        Toast.makeText(getBaseContext(), "Enter a valid name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    intent.putExtra("value", ((EditText) view).getText());
                }
                setResult(requestCodeNumber, intent); //return result to intent
                finish();
            }
        });

    }
}
