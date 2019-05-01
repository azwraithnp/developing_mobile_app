package com.azwraithnp.easytodo.ui.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.azwraithnp.easytodo.AppExecutors;
import com.azwraithnp.easytodo.R;
import com.azwraithnp.easytodo.database.Todo;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements TodoAdapter.ItemClickListener{

    @BindView(R.id.recyclerViewTasks)
    RecyclerView recyclerView;

    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;

    @BindColor(R.color.colorPrimary)
    int colorPrimary;

    TodoAdapter mAdapter;
    PageViewModel pageViewModel;


    public static PlaceholderFragment newInstance() {
        PlaceholderFragment fragment = new PlaceholderFragment();
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

        slidingUpPanelLayout.setAnchorPoint(0.75f);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new TodoAdapter(getActivity(), this);
        recyclerView.setAdapter(mAdapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);

        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        List<Todo> todoList = mAdapter.getTodos();
                        pageViewModel.delete(todoList.get(position));

                    }
                });
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                if(dX > 0)
                {
                    final ColorDrawable background = new ColorDrawable(colorPrimary);
                    background.setBounds(0, viewHolder.itemView.getTop(),   viewHolder.itemView.getLeft() + (int)dX, viewHolder.itemView.getBottom());

                    background.draw(c);
                }
                else
                {
                    final ColorDrawable background = new ColorDrawable(Color.RED);
                    background.setBounds(viewHolder.itemView.getRight() + (int)dX, viewHolder.itemView.getTop(),   viewHolder.itemView.getRight(),  viewHolder.itemView.getBottom());

                    background.draw(c);
                }


            }
        }).attachToRecyclerView(recyclerView);

        setUpViewModel();

        return root;
    }

    private void setUpViewModel() {
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        pageViewModel.getTodos().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(@Nullable List<Todo> todos) {
                mAdapter.setTodoList(todos);
            }
        });
    }

    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
//        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
//        intent.putExtra(AddTodoActivity.EXTRA_TASK_ID, itemId);
//        startActivity(intent);
    }


}