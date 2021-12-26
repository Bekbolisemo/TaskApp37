package com.example.taskapp37.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskapp37.modals.News;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("SELECT*FROM news order by createAt DESC")
    List<News> getAll();

    @Query("SELECT*FROM news order by title ASC")
    List<News> sort();

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);

    @Update
    void update(News news);
}
