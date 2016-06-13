package com.puxtech.weipan.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.puxtech.weipan.MyApplication;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mac on 15/11/4.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    protected Context context;
    protected MyApplication myapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        myapp = (MyApplication)getApplication();
    }

    protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

    protected void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    protected void clearAsyncTask() {
        Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
                .iterator();
        while (iterator.hasNext()) {
            AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
            if (asyncTask != null && !asyncTask.isCancelled()) {
                asyncTask.cancel(true);
            }
        }
        mAsyncTasks.clear();
    }



    @Override
    protected void onDestroy() {
        clearAsyncTask();
        super.onDestroy();
    }
}

