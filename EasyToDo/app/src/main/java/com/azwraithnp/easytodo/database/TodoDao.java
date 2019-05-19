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

    @Query("select * from todos where completed =0 order by priority desc")
    LiveData<List<Todo>> getAllTodos();

    @Query("select * from todos where completed =0 order by updated_at")
    LiveData<List<Todo>> getAllTodosByDate();

    @Query("select * from todos where completed =0")
    LiveData<List<Todo>> getAllTodosByOrder();

    @Query("DELETE FROM todos")
    public void deleteAll();

    @Query("UPDATE todos set completed=1")
    public void allComplete();

    @Query("select * from todos where completed=1")
    public LiveData<List<Todo>> getAllCompletedTodos();

    @Insert
    void insertTodo(Todo todo);

    @Delete
    void deleteTodo(Todo todo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Todo todo);

    @Query("select * from todos where id = :id")
    LiveData<Todo> loadTodoById(int id);

}
