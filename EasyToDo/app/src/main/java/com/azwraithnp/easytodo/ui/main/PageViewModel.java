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
        mTodos = mRepository.getAllWords();
    }

    public LiveData<List<Todo>> getTodos(){
        return mTodos;
    }

    public void insert(Todo todo) { mRepository.insert(todo); }

    public void delete(Todo todo) { mRepository.delete(todo); }

    public void update(Todo todo) { mRepository.update(todo); }
}