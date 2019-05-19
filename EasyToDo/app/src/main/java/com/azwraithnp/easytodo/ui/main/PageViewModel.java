package com.azwraithnp.easytodo.ui.main;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.azwraithnp.easytodo.database.Todo;

import java.util.List;

public class PageViewModel extends AndroidViewModel {

    private LiveData<List<Todo>> mTodos;
    TodoRepository mRepository;

    public PageViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);
        mTodos = mRepository.getAllTodos();
    }

    public LiveData<List<Todo>> getTodos(){
        return mTodos;
    }

    public LiveData<List<Todo>> getTodosByDate() { return mRepository.getAllTodosByDate();}

    public LiveData<List<Todo>> getmTodosByOrder() {return mRepository.getAllTodosByOrder();}

    public LiveData<List<Todo>> getCompletedTodos() {return mRepository.getAllCompletedTodos();}

    public void insert(Todo todo) { mRepository.insert(todo); }

    public void delete(Todo todo) { mRepository.delete(todo); }

    public void setComplete() {mRepository.setComplete(); }

    public void setTaskAsComplete(Todo todo) {mRepository.setTaskAsComplete(todo);}

    public void deleteAll() {mRepository.deleteAll();}

    public void update(Todo todo) { mRepository.update(todo); }
}