package com.azwraithnp.easytodo;

import android.os.Bundle;

import com.azwraithnp.easytodo.database.AppDatabase;
import com.azwraithnp.easytodo.database.Todo;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.azwraithnp.easytodo.ui.main.SectionsPagerAdapter;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        final  Todo todo = new Todo("Title 1", "Description 1", 1, new Date());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                    appDatabase.todoDao().insertTodo(todo);
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

    }
}