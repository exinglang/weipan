package com.puxtech.weipan.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.WeiXinLogin;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
public class WelcomeActivity extends BaseAppCompatActivity {
    long startTime;
//    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startTime = System.currentTimeMillis();


       asyncRequest();
      //  waitingToEnd();
    //    calLineViewHeight();


//        appendRow(stringList,2);
    }

    private void asyncRequest() {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;

            protected void onPreExecute() {
                super.onPreExecute();
            }

            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerOpenAccountContract httpManger = new HttpManagerOpenAccountContract(context);
                    responseData = httpManger.requestCatalog();
                    if (responseData.getRetCode() != 0) {
                        return false;
                    }
                    responseData = httpManger.requestAppKeyProperty();

                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }


            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                if (0 == responseData.getRetCode()) {
                    waitingToEnd();
                } else {
                    faild(responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");
                }

            }

        });
    }


    /**
     * 进入login
     */
    public void waitingToEnd() {
        // 如果本页面显示时间没超过3秒，就等待到3秒再结束
        long endTime = System.currentTimeMillis();
        long deltaTime = endTime - startTime;
        if (deltaTime < 1000 * 3) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    gotoWeiXinLogin();
                }
            }, 1500 - deltaTime);
        } else {
            gotoWeiXinLogin();
        }
    }



    //跳转至微信登陆页面
    private void gotoWeiXinLogin() {

        SharedPreferenceManager sharedPreferenceManager = new SharedPreferenceManager(this, SharedPreferenceManager.FILE_NAME_YIN_DAO_YE);
        Intent intent;
        if (!sharedPreferenceManager.getBoolean(this, sharedPreferenceManager.HAS_SHOW_YIN_DAO_YE, false)) {
            intent = new Intent(this, GuideActivity.class);

        } else {

            intent = new Intent(this, WeiXinLogin.class);

        }
        startActivity(intent);
        //去掉Activity切换动画
        overridePendingTransition(0,0);
        finish();
    }


    private void faild(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("获取失败");
        builder.setMessage(message + "，请重试或者退出");
        builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                asyncRequest();
//                tv_loading_message.setText("...");
//                clearAsyncTask();
//                initTaskList();
//                startTaskQueue();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }


}