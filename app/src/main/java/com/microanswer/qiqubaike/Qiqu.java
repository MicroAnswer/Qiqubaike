package com.microanswer.qiqubaike;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Microanswer on 2017/10/16.
 */

public class Qiqu extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
