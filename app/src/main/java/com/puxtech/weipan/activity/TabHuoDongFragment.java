package com.puxtech.weipan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.util.FileUtils;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.io.File;


/**
 * 个人
 */
public class TabHuoDongFragment extends BaseFragment {


    private static TabHuoDongFragment huoDongFragment;

    public static TabHuoDongFragment getInstance(Context context) {
        if (huoDongFragment == null) {

            huoDongFragment = new TabHuoDongFragment(context);
        }
        return huoDongFragment;
    }

    public TabHuoDongFragment(Context context) {
        super();
        this.context = context;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fView = inflater.inflate(R.layout.fragment_huodong, container, false);

        return fView;
    }



}
