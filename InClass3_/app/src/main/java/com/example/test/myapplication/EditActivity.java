package com.example.test.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

        /*LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(20, 20, 20, 20);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.VERTICAL);*/

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout layout = findViewById(R.id.edit_layout);

        TextView textView = new TextView(getBaseContext());
        textView.setLayoutParams(params);
        textView.setText(getResources().getText(R.string.edit_information));

        layout.addView(textView);

        if(requestCode == DisplayActivity.editCode[0]) {
            EditText name = new EditText(getBaseContext());
            layout.addView(name);
        } else if(requestCode == DisplayActivity.editCode[1]) {
            EditText name = new EditText(getBaseContext());
            layout.addView(name);
        } else if(requestCode == DisplayActivity.editCode[2]) {

        } else if(requestCode == DisplayActivity.editCode[3]) {

        }

        Button button = new Button(getBaseContext());
        button.setLayoutParams(params);
        button.setText(getResources().getText(R.string.save));



        layout.addView(button);


    }
}
