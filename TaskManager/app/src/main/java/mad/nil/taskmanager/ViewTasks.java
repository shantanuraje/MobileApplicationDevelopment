package mad.nil.taskmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Homework 1
 * File name: ViewTasks.java
 * Nilanjan Mhatre (Student Id: 801045013)
 * Shantanu Rajenimbalkar (Student Id: 800968033)
 */
public class ViewTasks extends Activity {

    public static final String BLANK = "";
    private LinkedList<Task> taskList;
    private Integer currentIndex;
    private Task currentTask;
    private String toastMessage;

    public ViewTasks() {
        taskList = new LinkedList<>();
        currentIndex = 0;
        currentTask = null;
        toastMessage = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_tasks);
        TextView textView = findViewById(R.id.titleHeading);
        textView.setText(getResources().getText(R.string.view_tasks_name));
//        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_layout);

        if(taskList != null && taskList.size() > 0) {
            currentTask = taskList.getFirst();
            currentIndex = 1;
        }

        ImageButton addButton = findViewById(R.id.add_button);
        ImageButton editButton = findViewById(R.id.edit_button);
        ImageButton deleteButton = findViewById(R.id.delete_button);
        ImageButton firstTaskButton = findViewById(R.id.first_task_button);
        ImageButton lastTaskButton = findViewById(R.id.last_task_button);
        ImageButton previousTaskButton = findViewById(R.id.previous_task_button);
        ImageButton nextTaskButton = findViewById(R.id.next_task_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddEditTask.class);
                intent.putExtra(RequestCodes.REQUEST_CODE.getValue().toString(), (Integer) RequestCodes.ADD_CODE.getValue());
                //startActivity(intent);
                startActivityForResult(intent, (Integer) RequestCodes.ADD_CODE.getValue());
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTask != null) {
                    Intent intent = new Intent(getBaseContext(), AddEditTask.class);
                    intent.putExtra(RequestCodes.TASK.getValue().toString(), currentTask);
                    intent.putExtra(RequestCodes.REQUEST_CODE.getValue().toString(), (Integer) RequestCodes.EDIT_CODE.getValue());
                    startActivityForResult(intent, (Integer) RequestCodes.EDIT_CODE.getValue());
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Do you really want to delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        delete();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0) {
                    alert.show();
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        firstTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0) {
                    currentTask = taskList.getFirst();
                    currentIndex = 1;
                    displayOnActivity();
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lastTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0) {
                    currentTask = taskList.getLast();
                    currentIndex = taskList.size();
                    displayOnActivity();
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        previousTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0 && currentIndex > 1) {
                    currentTask = taskList.get(currentIndex - 2);
                    currentIndex--;
                    displayOnActivity();
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                } else if(currentIndex == 1) {
                    Toast.makeText(getBaseContext(), "The current task is the first task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nextTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0 && currentIndex < taskList.size()) {
                    currentTask = taskList.get(currentIndex);
                    currentIndex++;
                    displayOnActivity();
                } else if(currentIndex == 0) {
                    Toast.makeText(getBaseContext(), "No tasks available", Toast.LENGTH_SHORT).show();
                } else if(currentIndex == taskList.size()) {
                    Toast.makeText(getBaseContext(), "The current task is the last task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayOnActivity();
    }

    public void delete() {
        Integer size = taskList.size();

        if(size <= 0) {
            return;
        } else if(size == 1) {
            currentTask = null;
        } else if(currentIndex == 1) {
            currentTask = taskList.get(currentIndex);
            currentIndex++;
        } else {
            currentTask = taskList.get(currentIndex - 2);
        }

        /*if(currentIndex == taskList.size()) {
            currentIndex--;
        }
        if(currentIndex == 1) {
            currentTask = taskList.get(currentIndex);
        } else {
            currentTask = taskList.get(currentIndex - 2);
        }*/
        currentIndex--;
        taskList.remove(currentIndex.intValue());
        displayOnActivity();
    }

    public void displayOnActivity() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

        /*ImageButton addButton = findViewById(R.id.add_button);
        ImageButton editButton = findViewById(R.id.edit_button);
        ImageButton deleteButton = findViewById(R.id.delete_button);
        ImageButton firstTaskButton = findViewById(R.id.first_task_button);
        ImageButton lastTaskButton = findViewById(R.id.last_task_button);
        ImageButton previousTaskButton = findViewById(R.id.previous_task_button);
        ImageButton nextTaskButton = findViewById(R.id.next_task_button);*/

        TextView titleView = findViewById(R.id.task_title);
        TextView dateTimeView = findViewById(R.id.task_date_time);
        TextView priorityView = findViewById(R.id.task_priority);
        TextView taskStatus = findViewById(R.id.task_status);

        Integer taskLength = 0;
        if(taskList != null) {
            taskLength = taskList.size();
        }

        if(currentTask != null) {
            titleView.setText(currentTask.getTitle());
            dateTimeView.setText(dateFormat.format(currentTask.getDate()) + " " + timeFormat.format(currentTask.getTime()));
            priorityView.setText(currentTask.getPriority().getValue());
        } else {
            titleView.setText(BLANK);
            dateTimeView.setText(BLANK);
            priorityView.setText(BLANK);
        }

        if(currentIndex == 0) {
            taskStatus.setText(getResources().getString(R.string.no_tasks_label));
        } else {
            taskStatus.setText("Task " + currentIndex + " of " + taskList.size());
        }

        if(toastMessage != null && toastMessage.length() > 0) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            toastMessage = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == ((Integer) RequestCodes.CANCEL_CODE.getValue()).intValue()) {
            displayOnActivity();
            return;
        }

        Task task = (Task) data.getExtras().getSerializable(RequestCodes.TASK.getValue().toString());
        if(resultCode == ((Integer) RequestCodes.ADD_CODE.getValue()).intValue()) {
            add(task);
        } else if(resultCode == ((Integer) RequestCodes.EDIT_CODE.getValue()).intValue()) {
            Integer index = data.getExtras().getInt(RequestCodes.INDEX.getValue().toString());
            edit(task);
        }
    }

    public void add(Task task) {
        if(toastMessage == null || toastMessage.length() <1) {
            toastMessage = getString(R.string.add_success_message);
        }
        ListIterator<Task> iterator = taskList.listIterator();
        Task tempTask = null;
        Integer index = 0;
        while(iterator.hasNext()) {
            tempTask = iterator.next();
            if(tempTask.getDate().after(task.getDate())
                    || (tempTask.getDate().equals(task.getDate()) && tempTask.getTime().after(task.getTime()))) {
                break;
            }
            index++;
        }
        taskList.add(index, task);
        currentIndex = index + 1;
        currentTask = task;
    }

    public void edit(Task task) {
        toastMessage = getString(R.string.edit_success_message);
        delete();
        add(task);
    }
}
