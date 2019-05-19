package com.azwraithnp.easytodo.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.azwraithnp.easytodo.R;
import com.azwraithnp.easytodo.database.Todo;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    //date format
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;
    // Class variables for the List that holds task data and the Context
    private List<Todo> mTodoList;
    private Context mContext;


    /**
     * Constructor for the TodoAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public TodoAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @NonNull
    @Override
    public TodoAdapter.TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.todo_list_layout, parent, false);

        return new TodoViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.TodoViewHolder holder, int position) {
        // Determine the values of the wanted data
        Todo taskEntry = mTodoList.get(position);
        String description = taskEntry.getDescription();
        int priority = taskEntry.getPriority();
        String title = taskEntry.getTitle();
//        String dueDate = dateFormat.format(taskEntry.getDueDate());
        Date dueDate = taskEntry.getDueDate();

        //Set values
        holder.taskTitle.setText(title);
        holder.taskDescription.setText(description);

        Glide.with(mContext).load(taskEntry.getImageLink()).placeholder(R.drawable.logo_round).into(holder.taskImage);

        // Programmatically set the text and color for the priority TextView

        int priorityColor = getPriorityColor(priority);

        Date todayDate = new Date();

        Log.d("Date check", "" + dueDate + ":" + todayDate);

        String dayDue = (String) DateFormat.format("dd",   dueDate);
        String monthDue = (String) DateFormat.format("MMM",  dueDate);
        String yearDue = (String) DateFormat.format("yyyy", dueDate);

        String todayDay = (String) DateFormat.format("dd",   todayDate);
        String todayMonth = (String) DateFormat.format("MMM",  todayDate);
        String todayYear = (String) DateFormat.format("yyyy", todayDate);

        Log.d("Date check: ", "" + (dayDue.equals(todayDay) && monthDue.equals(todayMonth) && yearDue.equals(todayYear)));

        if(dayDue.equals(todayDay) && monthDue.equals(todayMonth) && yearDue.equals(todayYear))
        {
            Glide.with(mContext).load(R.drawable.ic_date_range_yellow_24dp).placeholder(R.drawable.ic_date_range_green_24dp).into(holder.dateImage);
        }
        else if(todayDate.after(dueDate))
        {
            Glide.with(mContext).load(R.drawable.ic_date_range_red_24dp).placeholder(R.drawable.ic_date_range_green_24dp).into(holder.dateImage);
        }
        else
        {
            Glide.with(mContext).load(R.drawable.ic_date_range_green_24dp).placeholder(R.drawable.ic_date_range_green_24dp).into(holder.dateImage);
        }

        Glide.with(mContext).load(priorityColor).placeholder(R.drawable.ic_notifications_green_24dp).into(holder.priorityImage);

    }

    /*
    Helper method for selecting the correct priority circle color.
    P1 = red, P2 = orange, P3 = yellow
    */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch (priority) {
            case 0:
                priorityColor = R.drawable.ic_notifications_green_24dp;
                break;
            case 1:
                priorityColor = R.drawable.ic_notifications_yellow_24dp;
                break;
            case 2:
                priorityColor = R.drawable.ic_notifications_red_24dp;
                break;
            default:
                break;
        }
        return priorityColor;
    }

    @Override
    public int getItemCount() {
        if (mTodoList == null) {
            return 0;
        }
        return mTodoList.size();
    }


    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTodoList(List<Todo> todos) {
        mTodoList = todos;
        notifyDataSetChanged();
    }

    public List<Todo> getTodos(){
        return mTodoList;
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId, String toastMessage);
    }


    // Inner class for creating ViewHolders
    class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Class variables for the task description and priority TextViews
        @BindView(R.id.taskTitle)
        TextView taskTitle;

        @BindView(R.id.task_image)
        ImageView taskImage;

        @BindView(R.id.taskDesc)
        TextView taskDescription;

        @BindView(R.id.date_image)
        ImageView dateImage;

        @BindView(R.id.priority_image)
        ImageView priorityImage;

        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public TodoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            dateImage.setOnClickListener(v -> mItemClickListener.onItemClickListener(200, "" + mTodoList.get(getAdapterPosition()).getDueDate()));
            priorityImage.setOnClickListener(v -> {
                int priority = mTodoList.get(getAdapterPosition()).getPriority();
                if(priority == 0)
                {
                    mItemClickListener.onItemClickListener(300, "Normal priority");
                }
                else if(priority == 1)
                {
                    mItemClickListener.onItemClickListener(300, "Medium priority");
                }
                else
                {
                    mItemClickListener.onItemClickListener(300, "High priority");
                }
            });
        }

        @Override
        public void onClick(View view) {
            int elementId = mTodoList.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId, "");
        }
    }
}
