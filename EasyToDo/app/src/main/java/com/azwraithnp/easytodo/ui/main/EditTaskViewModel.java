package com.azwraithnp.easytodo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.azwraithnp.easytodo.database.Todo;

public class EditTaskViewModel extends AndroidViewModel {

    private LiveData<Todo> currentToDo;
    TodoRepository mRepository;

    public EditTaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TodoRepository(application);

    }

    public LiveData<Todo> getCurrentToDo(int id) {
        currentToDo = mRepository.getToDo(id);
        return currentToDo;}

    public void updateTodo(Todo todo){
            mRepository.update(todo);
        }

    public void delete(Todo todo)
    {
        mRepository.delete(todo);
    }

}
