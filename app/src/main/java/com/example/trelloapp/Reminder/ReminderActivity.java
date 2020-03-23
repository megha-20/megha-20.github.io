package com.example.trelloapp.Reminder;

import android.os.Bundle;
;

import androidx.annotation.NonNull;

import com.example.trelloapp.AppDefault.AppDefaultActivity;
import com.example.trelloapp.R;


public class ReminderActivity extends AppDefaultActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int contentViewLayoutRes() {
        return R.layout.reminder_layout;
    }

    @NonNull
    @Override
    protected ReminderFragment createInitialFragment()
    {
        return ReminderFragment.newInstance();
    }


}



