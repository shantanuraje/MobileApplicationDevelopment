package mad.nil.taskmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Homework 1
 * File name: AddEditTasks.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class AddEditTask extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_edit_task);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Integer requestCode = getIntent().getExtras().getInt(RequestCodes.REQUEST_CODE.getValue().toString());

        Calendar myCalendar = Calendar.getInstance();
        Calendar myCalendar1 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        if(requestCode == RequestCodes.EDIT_CODE.getValue()) {
            Task task = (Task) getIntent().getSerializableExtra(RequestCodes.TASK.getValue().toString());
            populateValues(task);
            myCalendar.setTime(task.getDate());
            myCalendar1.setTime(task.getTime());
            TextView textView = findViewById(R.id.titleHeading);
            textView.setText(getResources().getText(R.string.edit_task_name));
        } else {
            RadioGroup radioGroup = findViewById(R.id.priority_value);
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            TextView textView = findViewById(R.id.titleHeading);
            textView.setText(getResources().getText(R.string.add_task_name));
        }

        Button addButton = findViewById(R.id.save_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        Button cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        final EditText dateView = findViewById(R.id.date_value);
        final EditText timeView = findViewById(R.id.time_value);
        dateView.setKeyListener(null);
        timeView.setKeyListener(null);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                String monthString = month<10 ? "0"+month : ""+month;
                String dayString = dayOfMonth<10 ? "0"+dayOfMonth : ""+dayOfMonth;
                String date = monthString + "/" +dayString+"/"+year;
                dateView.setText(date);
            }
        }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String isAmPm = getResources().getString(R.string.am);
                if(hourOfDay >= 12) {
                    hourOfDay -= 12;
                    isAmPm = getResources().getString(R.string.pm);
                }
                String hourString = hourOfDay<10 ? "0"+hourOfDay : ""+hourOfDay;
                String timeString = minute<10 ? "0"+minute : ""+minute;
                String time = hourString + ":" +timeString + " " + isAmPm;

                timeView.setText(time);
            }
        }, myCalendar1.getTime().getHours(), myCalendar1.getTime().getMinutes(), false);

        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });
    }

    public void populateValues(Task task) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        EditText titleView = findViewById(R.id.title_value);
        EditText dateView = findViewById(R.id.date_value);
        EditText timeView = findViewById(R.id.time_value);
        RadioGroup radioGroup = findViewById(R.id.priority_value);

        titleView.setText(task.getTitle());
        dateView.setText(dateFormat.format(task.getDate()));
        timeView.setText(timeFormat.format(task.getTime()));

        for(int i=0; i<radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if(radioButton.getText().equals(task.getPriority().getValue())) {
                ((RadioButton) radioGroup.getChildAt(i)).setChecked(true);
                break;
            }
        }
    }

    public void save() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        EditText titleView = findViewById(R.id.title_value);
        EditText dateView = findViewById(R.id.date_value);
        EditText timeView = findViewById(R.id.time_value);

        RadioGroup radioGroup = findViewById(R.id.priority_value);

        String title = titleView.getText().toString();
        String dateValue = dateView.getText().toString();
        String timeValue = timeView.getText().toString();
        Date date = null;
        Date time = null;
        String priority = null;
        boolean failed = false;

        for(int i=0; i<radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            if(radioButton.isChecked()) {
                priority = radioButton.getText().toString();
            }
        }

        if(title == null || title.length() == 0) {
            titleView.setError(getResources().getString(R.string.title_error));
            failed = true;
        }
        try {
            date = dateFormat.parse(dateValue);
        } catch (ParseException e) {
            dateView.setError(getResources().getString(R.string.date_error));
            failed = true;
        }
        try {
            time = timeFormat.parse(timeValue);
        } catch (ParseException e) {
            timeView.setError(getResources().getString(R.string.time_error));
            failed = true;
        }

        if(failed) {
            return;
        }

        Task task = new Task();
        task.setTitle(title);
        task.setDate(date);
        task.setTime(time);
        task.setPriority(Priority.getPriorityByValue(priority));

        Integer requestCode = getIntent().getExtras().getInt(RequestCodes.REQUEST_CODE.getValue().toString());
        Integer index = getIntent().getExtras().getInt(RequestCodes.INDEX.getValue().toString());
        Intent intent = new Intent();
        intent.putExtra(RequestCodes.TASK.getValue().toString(), task);
        intent.putExtra(RequestCodes.REQUEST_CODE.getValue().toString(), requestCode);
        intent.putExtra(RequestCodes.INDEX.getValue().toString(), index);
        setResult(new Integer(requestCode), intent);
        finish();
    }

    public void cancel() {
        Intent intent = new Intent();
        setResult(new Integer(RequestCodes.CANCEL_CODE.getValue().toString()), intent);
        finish();
    }
}
