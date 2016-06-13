package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.ResponseData.HeartBeatResponseData;
import com.puxtech.weipan.ResponseData.SysInfoResponseData;
import com.puxtech.weipan.data.AlertUrlResponseData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.DealResponseData;
import com.puxtech.weipan.data.OpenAccountResponseData;
import com.puxtech.weipan.data.ReportDealResponseData;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.service.HeartBeatService;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 15/11/4.
 */
public class GeRenActInfoOpenActInputActivity extends BaseAppCompatActivity {
    public static final int SUCESS_OPEN_TO_CONTRACT = 2002;//开户成功,并跳转至签约
    public static final int OPEN_ACCOUNT = 2;//开户
    public static final int ALERT_INFO = 3;;//风险提示


    public static final int SEND_VERIFY_CODE = 1;//获取验证码
    public static final int CHECK_VERIFY_CODE = 1;//获取验证码


//    public static final int SUCESS_OPEN_TO_KAIHU = 1002;//开户成功,返回开户页面

    Toolbar toolbar;
    Button bt_getCode, bt_open;
    CheckBox cb_alert;


    EditText et_name, et_id, et_phonenumber, et_verifycode;
    TextView tv_alert;
    private Timer timer;
    private TimerTask task;
    TextInputLayout til_name, til_id, til_phonenumber, til_verifycode;
    LinearLayout ll_main;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaihu);
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        InitWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "填写开户信息", toolbar);
        addEditChangedListener();
        setVerifyAndOpenButtonClickListener();
        startRefeshTimer();

        //如果不同意风险提示,开户按钮置灰
        cb_alert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WidgetHelper.setButtonOrangeAvailable(bt_open);
                } else {
                    WidgetHelper.setButtonGreyUnavailable(bt_open);
                }
            }
        });
    }


    /**
     * 确保验证码倒计时一直在刷新,即使退出页面,重进后.
     */
    private void startRefeshTimer() {
        Timer timer;
        TimerTask task;
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        if (MyApplication.recLen != 60) {
                            setCodeButtonUnavailable();
                        } else {
                            setCodeButtonAvailable();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 1000);

    }

    private void InitWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        til_name = (TextInputLayout) findViewById(R.id.til_name);
        til_id = (TextInputLayout) findViewById(R.id.til_id);
        til_phonenumber = (TextInputLayout) findViewById(R.id.til_phonenumber);
        til_verifycode = (TextInputLayout) findViewById(R.id.til_verifycode);
        et_name = til_name.getEditText();
        et_id = til_id.getEditText();
        et_phonenumber = til_phonenumber.getEditText();
        et_verifycode = til_verifycode.getEditText();
        cb_alert = (CheckBox) findViewById(R.id.cb_alert);
        tv_alert = (TextView) findViewById(R.id.tv_alert);
        bt_getCode = (Button) findViewById(R.id.bt_getcode);
        bt_open = (Button) findViewById(R.id.bt_open);
    }

    /**
     * 添加文本验证
     */
    private void addEditChangedListener() {
        et_name.addTextChangedListener(WidgetHelper.checkTextInputLayoutName(til_name));
        et_id.addTextChangedListener(WidgetHelper.checkTextInputLayoutId(til_id));
        et_phonenumber.addTextChangedListener(WidgetHelper.checkTextInputLayoutPhoneNumber(til_phonenumber));
    }

    /**
     * 设置验证码 和 开户按钮的点击事件
     */
    private void setVerifyAndOpenButtonClickListener() {
        // 获取验证码
        bt_getCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (til_phonenumber.isErrorEnabled()) {
                    Toast.makeText(context, "请检查电话号码是否正确",
                            Toast.LENGTH_SHORT).show();
                } else {
                    setCodeButtonUnavailable();
                    timer = new Timer();
                    task = new TimerTask() {
                        public void run() {
                            runOnUiThread(new Runnable() { // UI thread
                                public void run() {
                                    MyApplication.recLen--;
                                    if (MyApplication.recLen < 1) {
                                        timer.cancel();
                                        task.cancel();
                                        MyApplication.recLen = 60;
                                    }
                                }
                            });
                        }
                    };
                    timer.schedule(task, 0, 1000);
                    request(SEND_VERIFY_CODE);
                }
            }
        });

        bt_open.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           if (!til_id.isErrorEnabled() && !til_phonenumber.isErrorEnabled()) {

                                               request(OPEN_ACCOUNT);


                                           } else {
                                               Snackbar.make(v, "请检查填写内容", Snackbar.LENGTH_SHORT).show();
                                           }
                                       }
                                   }
        );
        tv_alert.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            request(ALERT_INFO);



                                        }
                                    }
        );
    }

    /**
     * 请求
     *
     * @param requestType 请求的类型
     */
    private void request(final int requestType) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseData responseData;
            Dialog dialog;

            protected void onPreExecute() {
                dialog = ActivityUtils.showLoadingProgressDialog(context);
                super.onPreExecute();
            }

            @SuppressWarnings("ResourceType")
            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerOpenAccountContract httpManager = new HttpManagerOpenAccountContract(context);

                    if (requestType == OPEN_ACCOUNT) {
                        //先检查验证码
                        responseData = httpManager.requestCheckVerifyCode(et_phonenumber.getText().toString(), et_verifycode.getText().toString());
                        responseData.setRetCode(0);
                        String customer_no=null;
                        if (responseData.getRetCode() == 0) {
                            //请求开户
                            responseData = httpManager.requestOpenAccount(et_phonenumber.getText().toString(), et_id.getText().toString(), et_name.getText().toString());
                            customer_no = ((OpenAccountResponseData) responseData).getCustomerNo();
                        }


                        if (responseData.getRetCode() == 0) {
                            responseData = httpManager.tradeAccountListRequest();
//                            //绑定交易账户
//                            responseData = httpManager.requestBindTrade(true,customer_no);
                        }
                        //获取绑定的交易账号


                        if (responseData.getRetCode() != 0) {
                            return false;
                        }
//                        myapp.getCurrentTradeEntity().setUserId(tradeAccountListResponseData.getEntityList().get(i).getTrade_account());
//                        myapp.getCurrentTradeEntity().getPriceData().setPRICE_URL(Constant.PRICE_URL_SHIPAN);
//                        myapp.getCurrentTradeEntity().setHasLogin(true);
                        if (responseData.getRetCode() == 0) {
                            //获取开户信息
                            responseData = httpManager.requestOpenAccountInfo(customer_no);
                        }
                        HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context,myapp.getShipanTradeEntity());
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManager.tradeLogonRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            myapp.getShipanTradeEntity().setHasLogin(true);
                            responseData = httpManagerTrade.commodityRequest("");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.holdDetailRequest(1, "1000", "");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.holdTotalRequest(1, "");
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.accountInfoRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.otherFirmRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.trustRequest();
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.dealRequest(1, 5);
                            myapp.getCurrentTradeEntity().getTradeData().setDealDataList(((DealResponseData) responseData).dealDataArrayList);
                        }
                        if (responseData.getRetCode() == 0) {
                            responseData = httpManagerTrade.reportDealRequest("0", ActivityUtils.getYesterday235959(), 1, 5);
                            myapp.getCurrentTradeEntity().getTradeData().setReportDealDataList(((ReportDealResponseData) responseData).dealDataArrayList);
                        }
                        responseData = httpManagerTrade.requestSysInfo("1","5");
                        myapp.getCurrentTradeEntity().setInfoList(((SysInfoResponseData)responseData).dataList);
                        if (responseData.getRetCode() == 0) {
                            //心跳信号
                            SharedPreferenceManager spf = new SharedPreferenceManager(context, HeartBeatService.HEART_BEAT + myapp.getCurrentTradeEntity().getUserId());
                            long broadId = spf.getLong(context, SharedPreferenceManager.BROAD_ID, -1);
                            long dealCount = spf.getLong(context, SharedPreferenceManager.DEAL_COUNT, 0);//
                            long lastId = spf.getLong(context, SharedPreferenceManager.LAST_ID, -1);
                            // String td = spf.getString(context, SharedPreferenceManager.TD, "");
                            responseData = httpManagerTrade.heartBeatRequest(myapp.getCurrentTradeEntity(), 1, broadId, dealCount, lastId);
                            HeartBeatResponseData heartBeatResponseData = (HeartBeatResponseData) responseData;
                            new TradeHelper(context).saveSpf(spf, heartBeatResponseData.getLID(), heartBeatResponseData.getTTC(), heartBeatResponseData.getTLID(), heartBeatResponseData.getTD());
                        }


                    } else if (requestType == SEND_VERIFY_CODE) {
                        responseData = httpManager.requestSendVerifyCode(et_phonenumber.getText().toString());
                        //hetresponseData = httpManagerTrade.trustRequest();
                    }else if (requestType == ALERT_INFO) {
                        responseData = httpManager.requestAlertUrl();
                        //hetresponseData = httpManagerTrade.trustRequest();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }

            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                dialog.dismiss();

                if (0 == responseData.getRetCode()) {
                    if (requestType == OPEN_ACCOUNT) {
                        ActivityUtils.showAlertWithConfirmTextCancelText(context, "开户成功,请进行签约绑定", "去签约", "确定", new Runnable() {
                            @Override
                            public void run() {
                                intentContract();
                            }
                        }, new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                ;
                            }
                        });
                    } else if (requestType == SEND_VERIFY_CODE) {
                    }
                    else if (requestType == ALERT_INFO) {
                        Intent intent=new Intent(context,WebViewActivity.class);
                        intent.setAction( ( (AlertUrlResponseData) responseData).getUrl());
                        startActivity(intent);
//                        Uri uri = Uri.parse( ( (AlertUrlResponseData) responseData).getUrl());
//                        Intent it = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(it);
                    }
                } else {
                    Snackbar.make(ll_main, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Snackbar.LENGTH_LONG)
                            .setAction("重试", new View.OnClickListener() {
                                public void onClick(View v) {
                                    request(requestType);
                                }
                            }).show();
                }
            }
        });
    }

    /**
     * 去签约
     */
    private void intentContract() {
        setResult(SUCESS_OPEN_TO_CONTRACT);
        finish();

    }


    private void setCodeButtonUnavailable() {
        WidgetHelper.setButtonGreyUnavailable(bt_getCode, "" + MyApplication.recLen + "秒后可重新获取");
    }

    private void setCodeButtonAvailable() {
        WidgetHelper.setButtonOrangeAvailable(bt_getCode, "获取验证码");
    }

}
