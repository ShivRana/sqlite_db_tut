package com.tutorial.sqlitedbtutorial.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.tutorial.sqlitedbtutorial.dao.TaskDao;
import com.tutorial.sqlitedbtutorial.entity.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
