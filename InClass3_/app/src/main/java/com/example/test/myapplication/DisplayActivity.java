package com.example.test.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    static int editCode[] = {1, 2, 3, 4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        setTitle("Display Activity");
    }

    @Override
    protected void onStart() {
        super.onStart();

        ImageView imageView = findViewById(R.id.edit_image_1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.name_value);
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("request_code", editCode[0]);
                intent.putExtra("value", textView.getText());
                startActivityForResult(intent, editCode[0]);
            }
        });

        imageView = findViewById(R.id.edit_image_2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.email_value);
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("request_code", editCode[1]);
                intent.putExtra("value", textView.getText());
                startActivityForResult(intent, editCode[1]);
            }
        });

        imageView = findViewById(R.id.edit_image_3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.department_value);
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("request_code", editCode[2]);
                intent.putExtra("value", textView.getText());
                startActivityForResult(intent, editCode[2]);
            }
        });

        imageView = findViewById(R.id.edit_image_4);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.mood_value);
                Intent intent = new Intent(getBaseContext(), EditActivity.class);
                intent.putExtra("request_code", editCode[3]);
                intent.putExtra("value", textView.getText());
                startActivityForResult(intent, editCode[3]);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String value = data.getExtras().getString("value");
        if(requestCode == editCode[0]) {
            TextView textView = findViewById(R.id.name_value);
            textView.setText(value);
        } else if(requestCode == editCode[1]) {
            TextView textView = findViewById(R.id.email_value);
            textView.setText(value);
        } else if(requestCode == editCode[2]) {
            TextView textView = findViewById(R.id.department_value);
            textView.setText(value);
        } else if(requestCode == editCode[3]) {
            TextView textView = findViewById(R.id.mood_value);
            textView.setText(value);
        }
    }
}
