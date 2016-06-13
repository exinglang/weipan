package com.puxtech.weipan.viewHelp;

import android.content.Context;
import android.os.AsyncTask;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.network.HttpManagerPrice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mac on 15/11/19.
 */
public class BaseStockChartsView {

    protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();
    protected MyApplication myapp;
    protected Context context;
    HttpManagerPrice httpManagerPrice;

    public BaseStockChartsView(Context context) {
        myapp = (MyApplication) context.getApplicationContext();
        this.context = context;
        httpManagerPrice= new HttpManagerPrice(context);
    }

    public BaseStockChartsView() {

    }

    public void putAsyncTask(AsyncTask<Void, Void, Boolean> asyncTask) {
        mAsyncTasks.add(asyncTask.execute());
    }

    protected void clearAsyncTask() {

        Iterator<AsyncTask<Void, Void, Boolean>> iterator = mAsyncTasks
                .iterator();
        while (iterator.hasNext()) {
            AsyncTask<Void, Void, Boolean> asyncTask = iterator.next();
            if (asyncTask != null) {
                boolean flag = asyncTask.cancel(true);
            }
        }
        mAsyncTasks.clear();
    }
}
