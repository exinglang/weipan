package com.puxtech.weipan.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.puxtech.weipan.R;
import com.puxtech.weipan.service.PriceService;

public class TabActivity extends BaseAppCompatActivity {

    private FragmentManager fragmentManager;
    private Context context;
    public static int fragment_home, fragment_shipan, fragment_monipan, fragment_huodong, fragment_geren;//
    Handler handler;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_tab);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        handler = new Handler();
        fragmentManager = getSupportFragmentManager();
        setFragmentNameByRadioId();
        // 设置默认显示的fragment
        changeFragment(fragment_home, null);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeFragment(checkedId, null);
            }
        });

//        RadioButton rb1 = (RadioButton) findViewById(R.id.rb_1);
//        RadioButton rb2 = (RadioButton) findViewById(R.id.rb_2);
//
//      //  rb.setBackground(new IconDrawable(this, FontAwesomeIcons.fa_bluetooth).colorRes());
//        StateListDrawable drawable = new StateListDrawable();
//        //Non focused states
//        drawable.addState(new int[]{-android.R.attr.state_focused, -android.R.attr.state_selected, -android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_amazon));
//        drawable.addState(new int[]{-android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_bluetooth));
//        //Focused states
//        drawable.addState(new int[]{android.R.attr.state_focused,-android.R.attr.state_selected, -android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_bluetooth));
//        drawable.addState(new int[]{android.R.attr.state_focused, android.R.attr.state_selected, -android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_bluetooth));
//        //Pressed
//        drawable.addState(new int[]{android.R.attr.state_selected, android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_bluetooth));
//        drawable.addState(new int[]{android.R.attr.state_pressed},
//                new IconDrawable(this, FontAwesomeIcons.fa_bluetooth));
//
//        drawable.setBounds(0,  drawable.getMinimumWidth(), 0, 0);
//
//        rb1.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
//        rb2.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
    }


    public void setCheck() {
        RadioButton rb = (RadioButton) findViewById(R.id.rb_5);
        // radioGroup.check(R.id.rb_5);
        rb.setChecked(true);

    }

    /**
     * 将RADIO ID转为本地变量.方便使用
     */
    private void setFragmentNameByRadioId() {
        fragment_home = R.id.rb_1;
        fragment_shipan = R.id.rb_2;
        fragment_monipan = R.id.rb_3;
        fragment_huodong = R.id.rb_4;
        fragment_geren = R.id.rb_5;
    }

    /**
     * 根据radioButton的id设置fragment
     *
     * @param name 跳转的fragment
     * @param bd   可能有需要传递的数据
     */
    public void changeFragment(int name, Bundle bd) {
//        RadioButton rb = (RadioButton) findViewById(name);
//        rb.setChecked(true);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment fragment = getInstanceById(context, name);
        //switchContent(fragment);
        transaction.replace(R.id.content, fragment);
//        // transaction.add
        try {
            fragment.setArguments(bd);
        } catch (Exception l) {
            l.printStackTrace();

        }
        //  RadioButton rb=(RadioButton)findViewById(name);
        //  radioGroup.check(name);


        transaction.commit();
    }


    /**
     * 根据radioButton的id选择对应的fragment
     *
     * @param Id radioButton的id
     * @return
     */
    public Fragment getInstanceById(Context context, int Id) {
        Fragment fragment = null;
        switch (Id) {
            case R.id.rb_1:
                myapp.setShipanTradeEntity();
                fragment = TabHomeFragment.getInstance(context);
                break;
            case R.id.rb_2:
                myapp.setShipanTradeEntity();
                fragment = TabShiPanFragment.getInstance(context);
                break;
            case R.id.rb_3:
                myapp.setMoniTradeEntity();
                fragment = TabMoNiPanFragment.getInstance(context);
                break;
            case R.id.rb_4:
                myapp.setShipanTradeEntity();
                fragment = new TabHuoDongFragment(context);
                break;
            case R.id.rb_5:
                myapp.setShipanTradeEntity();
                fragment = new TabGeRenFragment(context);
                break;
        }
        return fragment;
    }

//    //接收数据并设置转换到相应fragment
//    public void onFragmentInteraction(int name,Bundle bd) {
//        setFragmentByName( name, bd);
//
//    }


    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(context, PriceService.class));
    }

    protected int logout_click_count = 0;
    private Runnable r;

    @Override
    public void onBackPressed() {
        if (logout_click_count++ == 0) {
            Toast.makeText(this, "再点一次退出", Toast.LENGTH_SHORT).show();
            r = new Runnable() {
                @Override
                public void run() {
                    r = null;
                    logout_click_count = 0;
                }
            };
            handler.postDelayed(r, 10000);
        } else {
            finish();
            System.exit(0);
        }
    }
}

