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
    private LiveData<Todo> currentToDo;

    TodoRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        todoDao = db.todoDao();
        mAllTodos = todoDao.getAllTodos();
    }

    LiveData<List<Todo>> getAllTodos() {
        return mAllTodos;
    }

    public LiveData<List<Todo>> getAllTodosByDate(){
        return todoDao.getAllTodos();
    }

    public LiveData<List<Todo>> getAllTodosByOrder(){
        return todoDao.getAllTodosByOrder();
    }

    public void insert (final Todo todo) {
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.insertTodo(todo));
    }

    public LiveData<Todo> getToDo(int id)
    {
        currentToDo = todoDao.loadTodoById(id);
        return currentToDo;
    }

    public void delete(final Todo todo)  {
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.deleteTodo(todo));
    }

    public void deleteAll(){
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.deleteAll());
    }

    public LiveData<List<Todo>> getAllCompletedTodos(){
        return todoDao.getAllCompletedTodos();
    }

    public void setComplete(){
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.allComplete());
    }

    public void setTaskAsComplete(Todo todo)
    {
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.update(todo));
    }

    public void update(final Todo todo)  {
        AppExecutors.getInstance().diskIO().execute(() -> todoDao.update(todo));
    }

}
