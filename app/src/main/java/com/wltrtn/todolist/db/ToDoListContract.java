package com.wltrtn.todolist.db;

import android.provider.BaseColumns;

public final class ToDoListContract {

    public ToDoListContract() {}

    public abstract class ToDoListEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_TASK = "task";
    }
}
