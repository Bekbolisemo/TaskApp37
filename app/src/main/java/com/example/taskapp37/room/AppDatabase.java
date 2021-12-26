package com.example.taskapp37.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp37.modals.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NewsDao newsDao();
}
