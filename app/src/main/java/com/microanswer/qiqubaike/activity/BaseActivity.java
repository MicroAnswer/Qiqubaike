package com.microanswer.qiqubaike.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Micro on 2017-10-3.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(this);
    }

}
