package com.wltrtn.todolist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.wltrtn.todolist.db.ToDoListContract.ToDoListEntry;
import com.wltrtn.todolist.db.ToDoListDbHelper;

public class MainActivity extends ListActivity {

    private ToDoListDbHelper helper;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Enter task to add");
                final EditText input = new EditText(view.getContext());
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newTask = input.getText().toString();

                        helper = new ToDoListDbHelper(MainActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(ToDoListEntry.COLUMN_NAME_TASK, newTask);
                        db.insertWithOnConflict(ToDoListEntry.TABLE_NAME, null,
                                values, SQLiteDatabase.CONFLICT_IGNORE);
                        updateListView();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
    }

    private void updateListView() {
        helper = new ToDoListDbHelper(MainActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(ToDoListEntry.TABLE_NAME,
                new String[] {ToDoListEntry._ID, ToDoListEntry.COLUMN_NAME_TASK},
                null, null, null, null, null);

        adapter = new SimpleCursorAdapter(this, R.layout.task_view, cursor,
                new String[] {ToDoListEntry.COLUMN_NAME_TASK},
                new int[] {R.id.taskTextView}, 0);

        this.setListAdapter(adapter);
    }

    public void onFinishedButtonClick(View view) {
        TextView taskTextView = (TextView)((View)view.getParent()).findViewById(R.id.taskTextView);
        String taskString = taskTextView.getText().toString();
        helper = new ToDoListDbHelper(MainActivity.this);
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(
                "DELETE FROM " + ToDoListEntry.TABLE_NAME + " WHERE " +
                        ToDoListEntry.COLUMN_NAME_TASK + " = '" + taskString + "'"
        );
        updateListView();
    }


}
