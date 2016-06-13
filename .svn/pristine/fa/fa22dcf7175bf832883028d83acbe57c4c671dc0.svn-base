package com.puxtech.weipan.activity;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected MyApplication myapp;
    protected Context context;

    protected List<AsyncTask<Void, Void, Boolean>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Boolean>>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myapp = (MyApplication) getActivity().getApplicationContext();
        this.context = getActivity();
    }

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


    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
//

    /**
     * 用于和主ACTIVITI通信
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(int fragment_name, Bundle bd);
    }

    @Override
    public void onDestroy() {
        clearAsyncTask();
        super.onDestroy();
    }
}
