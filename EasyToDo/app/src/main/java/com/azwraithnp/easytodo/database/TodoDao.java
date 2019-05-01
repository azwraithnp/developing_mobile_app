package com.azwraithnp.easytodo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public  interface TodoDao {

    @Query("select * from todos order by priority")
    LiveData<List<Todo>> getAllTodos();

    @Insert
    void insertTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo todo);

    @Query("select * from todos where id = :id")
    LiveData<Todo> loadTodoById(int id);

}
