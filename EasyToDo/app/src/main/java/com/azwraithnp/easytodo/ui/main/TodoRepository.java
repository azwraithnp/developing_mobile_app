package com.azwraithnp.easytodo.ui.main;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.azwraithnp.easytodo.AppExecutors;
import com.azwraithnp.easytodo.database.AppDatabase;
import com.azwraithnp.easytodo.database.Todo;
import com.azwraithnp.easytodo.database.TodoDao;

import java.util.List;

public class TodoRepository {

    private TodoDao todoDao;
    private LiveData<List<Todo>> mAllTodos;

    TodoRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        todoDao = db.todoDao();
        mAllTodos = todoDao.getAllTodos();
    }

    LiveData<List<Todo>> getAllWords() {
        return mAllTodos;
    }

    public void insert (final Todo todo) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.insertTodo(todo);
            }
        });
    }

    public void delete(final Todo todo)  {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.deleteTodo(todo);
            }
        });
    }

    public void update(final Todo todo)  {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                todoDao.update(todo);
            }
        });
    }

}
