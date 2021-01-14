package com.tutorial.sqlitedbtutorial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tutorial.sqlitedbtutorial.R;
import com.tutorial.sqlitedbtutorial.db.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private MyDatabaseHelper myDatabaseHelper;
    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btReadData = findViewById(R.id.bt_read_data);
        Button btWriteData = findViewById(R.id.bt_write_data);
        EditText etName = findViewById(R.id.et_name);
        EditText etPhone = findViewById(R.id.et_phone);


        myDatabaseHelper = new MyDatabaseHelper(this);

        btWriteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                phone = etPhone.getText().toString();
                insertIntoDb(name, phone);
            }
        });


        btReadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });

    }


    private void insertIntoDb(String name, String phone) {

        SQLiteDatabase writableDb = myDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.NAME, name);
        values.put(MyDatabaseHelper.PHONE, phone);
        writableDb.insert(MyDatabaseHelper.TABLE_NAME, null, values);
        Toast.makeText(this, "Database written successfully", Toast.LENGTH_LONG).show();
    }

    private void readData() {
        String selectQuery = "SELECT  * FROM " + MyDatabaseHelper.TABLE_NAME;
        SQLiteDatabase db = myDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                Log.d("MyDatabaseData", "name-> " + name + ", " + "Phone-> " + phone);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}