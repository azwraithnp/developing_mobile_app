package com.azwraithnp.easytodo.ui.main;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.azwraithnp.easytodo.AppExecutors;
import com.azwraithnp.easytodo.R;
import com.azwraithnp.easytodo.database.AppDatabase;
import com.azwraithnp.easytodo.database.Todo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

/**
 * A placeholder fragment containing a simple view.
 */
public class CompletedFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.recyclerViewTasks)
    RecyclerView recyclerView;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindView(R.id.constraintLayout)
    ConstraintLayout mainLayout;

    @BindView(R.id.menuViewLayout)
    LinearLayout menuViewLayout;

    @BindView(R.id.addTaskLayout)
    LinearLayout addTaskLayout;

    @BindView(R.id.sortViewLayout)
    LinearLayout sortViewLayout;

    @BindView(R.id.datePickerButton)
    ImageView datePickerButton;

    @BindView(R.id.showDescButton)
    ImageView showDescButton;

    @BindView(R.id.taskTitleEnter)
    EditText taskTitleEnter;

    @BindView(R.id.taskDescEnter)
    EditText taskDescEnter;

    @BindView(R.id.priorityButton)
    ImageView priorityButton;

    @BindView(R.id.openCalendar)
    LinearLayout openCalendar;

    @BindView(R.id.clearAll)
    LinearLayout clearAll;

    @BindView(R.id.setComplete)
    LinearLayout setComplete;

    @BindView(R.id.first)
    RadioButton first;

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.second)
    RadioButton second;

    @BindView(R.id.third)
    RadioButton third;


    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    @BindView(R.id.saveButton)
    Button saveButton;

    CompletedTodoAdapter mAdapter;
    PageViewModel pageViewModel;

    int chosenPriority = 0;

    String dateString = "";

    public static CompletedFragment newInstance() {
        CompletedFragment fragment = new CompletedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        slidingUpPanelLayout.setAnchorPoint(0.65f);

        slidingUpPanelLayout.setDragView(R.id.draggable);

        addTaskLayout.setVisibility(View.VISIBLE);

        mainLayout.setClickable(true);

        slidingUpPanelLayout.setFadeOnClickListener(v -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new CompletedTodoAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        setUpViewModel();

        return root;
    }

    private void setUpViewModel() {
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        pageViewModel.getCompletedTodos().observe(this, todos -> mAdapter.setTodoList(todos));
    }


    public void showMenu()
    {
        displayAllLayouts();

        slidingUpPanelLayout.setAnchorPoint(0.95f);

        phoneNumber.setText(getActivity().getIntent().getStringExtra("phoneNumber"));

        addTaskLayout.setVisibility(View.GONE);
        sortViewLayout.setVisibility(View.GONE);

        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        openCalendar.setOnClickListener(v -> {
            long startMillis = System.currentTimeMillis();
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, startMillis);
            Intent intent = new Intent(Intent.ACTION_VIEW).setData(builder.build());
            startActivity(intent);
        });

        clearAll.setOnClickListener(v -> {
            pageViewModel.deleteAll();
        });


        setComplete.setOnClickListener(v -> {
            pageViewModel.setComplete();
        });

    }

    public void showSort()
    {
        displayAllLayouts();

        first.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pageViewModel.getmTodosByOrder().observe(this, todos -> mAdapter.setTodoList(todos));
        });

        second.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            pageViewModel.getTodos().observe(this, todos -> mAdapter.setTodoList(todos));
        }));

        third.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pageViewModel.getTodosByDate().observe(this, todos -> mAdapter.setTodoList(todos));
        });

        slidingUpPanelLayout.setAnchorPoint(0.95f);

        menuViewLayout.setVisibility(View.GONE);
        addTaskLayout.setVisibility(View.GONE);

        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
    }

    private void showDate()
    {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(getActivity(), this::onDateSet, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void displayAllLayouts()
    {
        menuViewLayout.setVisibility(View.VISIBLE);
        addTaskLayout.setVisibility(View.VISIBLE);
        sortViewLayout.setVisibility(View.VISIBLE);
    }

    public void showAddTask()
    {
        displayAllLayouts();

        slidingUpPanelLayout.setAnchorPoint(0.95f);

        menuViewLayout.setVisibility(View.GONE);
        sortViewLayout.setVisibility(View.GONE);


        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
        datePickerButton.setOnClickListener(v -> showDate());
        showDescButton.setOnClickListener(v -> taskDescEnter.setVisibility(View.VISIBLE));
        priorityButton.setOnClickListener(v -> {
            chosenPriority = (chosenPriority + 1) % 3;
            if(chosenPriority == 0)
            {
                priorityButton.setImageResource(R.drawable.ic_notifications_green_24dp);
            }
            else if(chosenPriority == 1)
            {
                priorityButton.setImageResource(R.drawable.ic_notifications_yellow_24dp);
            }
            else
            {
                priorityButton.setImageResource(R.drawable.ic_notifications_red_24dp);
            }
        });
        saveButton.setOnClickListener(v -> {
            AppDatabase appDatabase = AppDatabase.getInstance(getActivity().getApplicationContext());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date d = new Date();
            Log.d("Date check again", dateString);
            try {
                d = sdf.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.d("Date string:", "" + d);

            final  Todo todo = new Todo( taskTitleEnter.getText().toString(), taskDescEnter.getText().toString(), chosenPriority, d);

            AppExecutors.getInstance().diskIO().execute(() -> appDatabase.todoDao().insertTodo(todo));

        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateString = dayOfMonth + "/" + (month+1) + "/" + year;
    }
}