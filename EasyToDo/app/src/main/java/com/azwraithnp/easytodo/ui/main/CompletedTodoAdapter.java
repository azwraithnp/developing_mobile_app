package com.azwraithnp.easytodo.ui.main;

import android.content.Context;
import android.graphics.Paint;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azwraithnp.easytodo.R;
import com.azwraithnp.easytodo.database.Todo;
import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompletedTodoAdapter extends RecyclerView.Adapter<CompletedTodoAdapter.CompletedTodoViewHolder>{

    private List<Todo> mTodoList;
    private Context mContext;

    public CompletedTodoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public CompletedTodoAdapter.CompletedTodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.completed_todo_list_layout, parent, false);

        return new CompletedTodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTodoAdapter.CompletedTodoViewHolder holder, int position) {
        // Determine the values of the wanted data
        Todo taskEntry = mTodoList.get(position);
        String title = taskEntry.getTitle();

        //Set value
        holder.taskTitle.setText(title);
        holder.taskTitle.setPaintFlags(holder.taskTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    @Override
    public int getItemCount() {
        if (mTodoList == null) {
            return 0;
        }
        return mTodoList.size();
    }

    public void setTodoList(List<Todo> todos) {
        mTodoList = todos;
        notifyDataSetChanged();
    }

    public List<Todo> getTodos(){
        return mTodoList;
    }

    class CompletedTodoViewHolder extends RecyclerView.ViewHolder {

        // Class variables for the task description and priority TextViews
        @BindView(R.id.taskTitle)
        TextView taskTitle;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public CompletedTodoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }

    }
}
