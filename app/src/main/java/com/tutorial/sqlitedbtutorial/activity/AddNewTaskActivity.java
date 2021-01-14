package com.tutorial.sqlitedbtutorial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tutorial.sqlitedbtutorial.R;
import com.tutorial.sqlitedbtutorial.entity.Task;
import com.tutorial.sqlitedbtutorial.helper.DatabaseClient;

public class AddNewTaskActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etPhone;
    private Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btSave = findViewById(R.id.bt_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTask();
            }
        });

    }

    void addNewTask() {
        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                //creating a task
                Task task = new Task();
                task.setName(etName.getText().toString());
                task.setPhone(etPhone.getText().toString());
                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
//                startActivity(new Intent(getApplicationContext(), TaskActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();

    }
}