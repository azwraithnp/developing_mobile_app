package com.azwraithnp.easytodo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.azwraithnp.easytodo.database.AppDatabase;
import com.azwraithnp.easytodo.database.Todo;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.azwraithnp.easytodo.ui.main.SectionsPagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    AppDatabase appDatabase;

    @BindView(R.id.showMenuButton)
    ImageView showMenuButton;

    @BindView(R.id.addNewTask)
    LinearLayout addNewTaskButton;

    @BindView(R.id.sortButton)
    ImageView sortButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(getApplicationContext());

        ButterKnife.bind(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        showMenuButton.setOnClickListener(v -> sectionsPagerAdapter.showMenuPanel());

        addNewTaskButton.setOnClickListener(v -> sectionsPagerAdapter.showAddTaskPanel());

        sortButton.setOnClickListener(v -> sectionsPagerAdapter.showSortTaskPanel());

    }
}