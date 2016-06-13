package com.puxtech.weipan.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TabActivityHideShow extends BaseAppCompatActivity {

    FragmentManager fragmentManager;
    int currentStepId;
    HashMap<Integer, Fragment> fragmentMap;//key是StepId
    public static final int HOME = 101;
    public static final int SHIPAN = 102;
    public static final int MONIPAN = 103;
    public static final int HUODONG = 104;
    public static final int GEREN = 105;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        fragmentManager = getSupportFragmentManager();
        fragmentMap = new HashMap<>();
        setContentView(R.layout.activity_tab);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                setFragmentByName(checkedId, null);

                if (checkedId == R.id.rb_1) {
                    currentStepId = HOME;
                } else if (checkedId == R.id.rb_2) {
                    currentStepId = SHIPAN;
                } else if (checkedId == R.id.rb_3) {
                    currentStepId = MONIPAN;
                } else if (checkedId == R.id.rb_4) {
                    currentStepId = HUODONG;
                } else if (checkedId == R.id.rb_5) {
                    currentStepId = GEREN;
                }
                changeFragment(currentStepId, null);
            }
        });
        //用返回按钮控制当前该显示哪个fragment，如果前面没有了就结束整个activity
//        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(currentStepId == STEP_OPEN_ACCOUNT_INFO){
//                    finish();
//                    return;
//                }
//                else if(currentStepId == STEP_SIGN_TYPE){
//                    currentStepId = STEP_OPEN_ACCOUNT_INFO;
//                }
//                else if(currentStepId == STEP_READ_AGREEMENT){
//                    currentStepId = STEP_SIGN_TYPE;
//                }
//                else if(currentStepId == STEP_UPDATE_PASSWORD){
//                    currentStepId = STEP_READ_AGREEMENT;
//                }
//                changeFragment();
//            }
//        });
        setupFragments();
        currentStepId = HOME;
        changeFragment(HOME, null);
        // logonnew();
    }


    /**
     * 创建并添加所有的Fragment
     */
    private void setupFragments() {
        TabHomeFragment tabHomeFragment = TabHomeFragment.getInstance(this);
        TabShiPanFragment tabShiPanFragment = TabShiPanFragment.getInstance(this);
        TabMoNiPanFragment tabMoNiPanFragment = TabMoNiPanFragment.getInstance(this);
        TabHuoDongFragment tabHuoDongFragment = TabHuoDongFragment.getInstance(this);
        TabGeRenFragment tabGeRenFragment = TabGeRenFragment.getInstance(this);


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.content, tabHomeFragment);
        fragmentTransaction.add(R.id.content, tabShiPanFragment);
        fragmentTransaction.add(R.id.content, tabMoNiPanFragment);
        fragmentTransaction.add(R.id.content, tabHuoDongFragment);
        fragmentTransaction.add(R.id.content, tabGeRenFragment);
        fragmentTransaction.commit();
        fragmentMap.put(HOME, tabHomeFragment);
        fragmentMap.put(SHIPAN, tabShiPanFragment);
        fragmentMap.put(MONIPAN, tabMoNiPanFragment);
        fragmentMap.put(HUODONG, tabHuoDongFragment);
        fragmentMap.put(GEREN, tabGeRenFragment);

    }

//    @Override
//    public void onNextStepClick(Bundle bundle) {
//        if(currentStepId == STEP_OPEN_ACCOUNT_INFO){
//            currentStepId = STEP_SIGN_TYPE;
//        }
//        else if(currentStepId == AcctTransNextStepListener.STEP_SIGN_TYPE){
//            currentStepId = STEP_READ_AGREEMENT;
//        }
//        else if(currentStepId == AcctTransNextStepListener.STEP_READ_AGREEMENT){
//            currentStepId = STEP_UPDATE_PASSWORD;
//        }
//        changeFragment();
//    }


    /**
     * 根据当前StepId来显示当前Fragment，隐藏非当前Fragment
     */
    public void changeFragment(int mCurrentStepId, Bundle bd) {
        currentStepId = mCurrentStepId;
        int checkedId=0;
        if (currentStepId == HOME) {
            checkedId = R.id.rb_1;
        } else if (currentStepId == SHIPAN) {
            checkedId = R.id.rb_2;
        } else if (currentStepId == MONIPAN) {
            checkedId = R.id.rb_3;
        } else if (currentStepId == HUODONG) {
            checkedId = R.id.rb_4;
        } else if (currentStepId == GEREN) {
            checkedId = R.id.rb_5;
        }

        RadioButton rb = (RadioButton) findViewById(checkedId);
        // radioGroup.check(R.id.rb_5);
        rb.setChecked(true);
        myapp.setShipanTradeEntity();

        if (currentStepId == MONIPAN) {
            myapp.setMoniTradeEntity();

        }
        Iterator<Map.Entry<Integer, Fragment>> iterator = fragmentMap.entrySet().iterator();
        while (iterator.hasNext()) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Map.Entry<Integer, Fragment> entry = iterator.next();
            Integer key = entry.getKey();
            Fragment fragment = entry.getValue();
            // transaction.add
            try {
                fragment.setArguments(bd);
            } catch (Exception l) {
                l.printStackTrace();

            }
            if (key == currentStepId) {
                fragmentTransaction.show(fragment);
                fragmentTransaction.commit();
            } else {
                fragmentTransaction.hide(fragment);
                fragmentTransaction.commit();
            }

        }
    }

    public void setCheck() {
        RadioButton rb = (RadioButton) findViewById(R.id.rb_5);
        // radioGroup.check(R.id.rb_5);
        rb.setChecked(true);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("trans Activity onActivityResult");
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(context, PriceService.class));
    }

    protected int logout_click_count = 0;
    private Runnable r;
    Handler handler;

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
