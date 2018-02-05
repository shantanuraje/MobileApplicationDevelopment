package mad.nil.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.ListIterator;

public class ViewTasks extends AppCompatActivity {

    public LinkedList<Task> taskList;
    public Integer currentIndex;
    public Task currentTask;

    public ViewTasks() {
        taskList = new LinkedList<>();
        currentIndex = 0;
        currentTask = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        if(taskList != null && taskList.size() > 0) {
            currentTask = taskList.getFirst();
            currentIndex = 1;
        }

        ImageButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddEditTask.class);
                intent.putExtra(RequestCodes.REQUEST_CODE.getValue().toString(), (Integer) RequestCodes.ADD_CODE.getValue());
                //startActivity(intent);
                startActivityForResult(intent, (Integer) RequestCodes.ADD_CODE.getValue());
            }
        });

        ImageButton editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddEditTask.class);
                intent.putExtra(RequestCodes.TASK.getValue().toString(), currentTask);
                intent.putExtra(RequestCodes.REQUEST_CODE.getValue().toString(), (Integer) RequestCodes.EDIT_CODE.getValue());
                startActivityForResult(intent, (Integer) RequestCodes.EDIT_CODE.getValue());
            }
        });

        ImageButton deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        ImageButton firstTaskButton = findViewById(R.id.first_task_button);
        firstTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0) {
                    currentTask = taskList.getFirst();
                    currentIndex = 1;
                    displayOnActivity();
                }
            }
        });

        ImageButton lastTaskButton = findViewById(R.id.last_task_button);
        lastTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0) {
                    currentTask = taskList.getLast();
                    currentIndex = taskList.size();
                    displayOnActivity();
                }
            }
        });

        ImageButton previousTaskButton = findViewById(R.id.previous_task_button);
        previousTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0 && currentIndex > 1) {
                    currentTask = taskList.get(currentIndex - 2);
                    currentIndex--;
                    displayOnActivity();
                }
            }
        });

        ImageButton nextTaskButton = findViewById(R.id.next_task_button);
        nextTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskList != null && taskList.size() > 0 && currentIndex < taskList.size()) {
                    currentTask = taskList.get(currentIndex);
                    currentIndex++;
                    displayOnActivity();
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
        if(currentIndex == taskList.size()) {
            currentIndex--;
        }
        if(currentIndex == 1) {
            currentTask = taskList.get(currentIndex);
        } else {
            currentTask = taskList.get(currentIndex - 2);
        }
        taskList.remove(currentIndex - 1);
        displayOnActivity();
    }

    public void displayOnActivity() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");

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
        }

        if(currentIndex == 0) {
            taskStatus.setText(getResources().getString(R.string.no_tasks_label));
        } else {
            taskStatus.setText("Task " + currentIndex + " of " + taskList.size());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Task task = (Task) data.getExtras().getSerializable(RequestCodes.TASK.getValue().toString());
        if(resultCode == ((Integer) RequestCodes.ADD_CODE.getValue()).intValue()) {
            add(task);
        } else if(resultCode == ((Integer) RequestCodes.EDIT_CODE.getValue()).intValue()) {
            Integer index = data.getExtras().getInt(RequestCodes.INDEX.getValue().toString());
            edit(task);
        }
    }

    public void add(Task task) {
        ListIterator<Task> iterator = taskList.listIterator();
        Task tempTask = null;
        Integer index = 1;
        while(iterator.hasNext()) {
            tempTask = iterator.next();
            if(tempTask.getDate().after(task.getDate())
                    || (tempTask.getDate().equals(task.getDate()) && tempTask.getTime().after(task.getTime()))) {
                break;
            }
            index++;
        }
        iterator.add(task);
        currentIndex = index;
        currentTask = task;
    }

    public void edit(Task task) {
        delete();
        add(task);
    }
}
