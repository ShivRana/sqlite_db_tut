package com.tutorial.sqlitedbtutorial.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tutorial.sqlitedbtutorial.R;
import com.tutorial.sqlitedbtutorial.entity.Task;
import com.tutorial.sqlitedbtutorial.helper.DatabaseClient;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private RecyclerView rcvTask;
    private Button btGetTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        rcvTask = findViewById(R.id.rcv_task);
        btGetTask = findViewById(R.id.bt_get_task);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvTask.setLayoutManager(layoutManager);
        btGetTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTasks();
            }
        });

        FloatingActionButton floatingAddNewTask = findViewById(R.id.float_add_new_task);
        floatingAddNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddNewTaskActivity();
            }
        });
    }

    private void openAddNewTaskActivity() {
        startActivity(new Intent(getApplicationContext(), AddNewTaskActivity.class));
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                TasksAdapter adapter = new TasksAdapter(TaskActivity.this, tasks);
                rcvTask.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


}