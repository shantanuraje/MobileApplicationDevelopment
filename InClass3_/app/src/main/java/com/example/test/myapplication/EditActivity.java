package com.example.test.myapplication;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Integer requestCode = getIntent().getExtras().getInt("request_code");
        String value = getIntent().getExtras().getString("value");

        LinearLayout edit_layout = (LinearLayout) findViewById(R.id.edit_layout);

        if(requestCode == DisplayActivity.editCode[0]) {
            EditText name = new EditText(getBaseContext());
            edit_layout.addView(name);
        } else if(requestCode == DisplayActivity.editCode[1]) {
            EditText email = new EditText(getBaseContext());
            edit_layout.addView(email);
        } else if (requestCode == DisplayActivity.editCode[2]){
            RadioGroup rg_department =  new RadioGroup(getBaseContext());
            RadioButton rb_edit_1 = new RadioButton(getBaseContext());
            RadioButton rb_edit_2 = new RadioButton(getBaseContext());
            RadioButton rb_edit_3 = new RadioButton(getBaseContext());
            RadioButton rb_edit_4 = new RadioButton(getBaseContext());
            rg_department.addView(rb_edit_1);
            rg_department.addView(rb_edit_2);
            rg_department.addView(rb_edit_3);
            rg_department.addView(rb_edit_4);
            edit_layout.addView(rg_department);
        } else if (requestCode == DisplayActivity.editCode[3]){
            SeekBar sb_mood = new SeekBar(getBaseContext());
            edit_layout.addView(sb_mood);

        }
        
    }
}
