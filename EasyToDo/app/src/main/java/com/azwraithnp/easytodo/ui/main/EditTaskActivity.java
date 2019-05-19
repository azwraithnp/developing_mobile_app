package com.azwraithnp.easytodo.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.azwraithnp.easytodo.AppExecutors;
import com.azwraithnp.easytodo.MainActivity;
import com.azwraithnp.easytodo.R;
import com.azwraithnp.easytodo.database.AppDatabase;
import com.azwraithnp.easytodo.database.Todo;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditTaskViewModel editTaskViewModel;
    Todo todo;

    @BindView(R.id.titleEnter)
    EditText titleEnter;

    @BindView(R.id.descEnter)
    EditText descEnter;

    @BindView(R.id.dateEnter)
    TextView dateEnter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.updatePicture)
    Button updatePicture;

    @BindView(R.id.deleteTask)
    Button deleteTask;

    @BindView(R.id.task_image)
    CircleImageView taskImage;

    @BindView(R.id.confirmChanges)
            Button confirmChanges;

    Uri filePath;
    String downloadURL;
    StorageReference imageRef;

    String dateString="";
    boolean dateChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ButterKnife.bind(this);

        titleEnter.setText("");
        descEnter.setText("");
        dateEnter.setText("");

        dateEnter.setOnClickListener(v -> showDate());

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editTaskViewModel = ViewModelProviders.of(this).get(EditTaskViewModel.class);
        editTaskViewModel.getCurrentToDo(getIntent().getIntExtra("todo_id", 0)).observe(this, todo ->
        {
            if (todo != null)
            {
                this.todo = todo;
                titleEnter.setText(todo.getTitle());
                descEnter.setText(todo.getDescription());
                dateEnter.setText(todo.getDueDate().toString());
                dateString=todo.getDueDate().toString();

                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a reference to "mountains.jpg"
                imageRef = storageRef.child(todo.getId() + ".jpg");

                // Create a reference to 'images/mountains.jpg'
                StorageReference imagesRef = storageRef.child("images/" + todo.getId() +  ".jpg");

                // While the file names are the same, the references point to different files
                imageRef.getName().equals(imagesRef.getName());    // true
                imageRef.getPath().equals(imagesRef.getPath());    // false

                updatePicture.setOnClickListener(v ->
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 71);
                });

                deleteTask.setOnClickListener(v -> {
                    editTaskViewModel.delete(todo);
                    onBackPressed();
                });


                confirmChanges.setOnClickListener(v -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = new Date();
                    try {
                        d = sdf.parse(dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, dateChanged + "", Toast.LENGTH_SHORT).show();

                    Todo updatedTodo = todo;
                    updatedTodo.setTitle(titleEnter.getText().toString());
                    updatedTodo.setDescription(descEnter.getText().toString());
                    if(dateChanged)
                    {
                        updatedTodo.setDueDate(d);
                    }
                    updatedTodo.setImageLink(downloadURL);
                    editTaskViewModel.updateTodo(updatedTodo);
                });
            }
        });
    }

    private void showDate()
    {
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(EditTaskActivity.this, this::onDateSet, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==71 && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                taskImage.setImageBitmap(bitmap);

                uploadImage();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage()
    {

        UploadTask uploadTask = imageRef.putFile(filePath);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                Log.d("Download URL: ", imageRef.getDownloadUrl().toString());
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.d("Download URL: ", downloadUri.toString());
                    downloadURL = downloadUri.toString();
                    Toast.makeText(EditTaskActivity.this, "Picture uploaded", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle failures
                    // ...
                    Toast.makeText(EditTaskActivity.this, "Failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dateChanged = true;
        dateString = dayOfMonth + "/" + (month+1) + "/" + year;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = new Date();
        try {
            d = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        dateEnter.setText(d.toString());
    }
}
