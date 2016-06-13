package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.BankListResponseData;
import com.puxtech.weipan.data.BaseResponseDataOpenAccountContract;
import com.puxtech.weipan.data.ContractChooseItemResponseData;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 15/11/4.
 */
public class GeRenActInfoContractInfoInputActivity extends BaseAppCompatActivity {
    public static final int BANK = 1;
    public static final int PROVINCE = 2;
    public static final int CITY = 3;
    public static final int BRANCH = 4;
//    public static final int SEND_VERIFY_CODE = 5;
//    public static final int CHECK_VERIFY_CODE = 6;
//    public static final int CONTRACT = 7;

    public enum RequestEnum {
        BANK, PROVINCE, CITY, BRANCH, SEND_VERIFY_CODE, CONTRACT;
    }


    public static final String CONTRACT_CHOOSE_ITEM_DATA = "CONTRACT_CHOOSE_ITEM_DATA";


    //    public static final int SUCESS_OPEN_TO_KAIHU = 1002;//开户成功,返回开户页面
    RelativeLayout rl_bank, rl_province, rl_city, rl_branch;
    TextView tv_bank, tv_province, tv_city, tv_branch;
    Toolbar toolbar;
    Button bt_getCode, bt_contract;
    EditText et_banknumber, et_code;
    private Timer timer;
    private TimerTask task;
    TextInputLayout til_banknumber;
    String bankId = "", provinceId = "", cityId = "", branchId = "";//选择的对应ID

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        InitWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "填写签约信息", toolbar);
        setVerifyAndContractButtonClickListener();
        startRefeshTimer();

    }


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rl_bank:
                    requestChooseItem(RequestEnum.BANK);
                    break;
                case R.id.rl_province:
                    requestChooseItem(RequestEnum.PROVINCE);
                    break;
                case R.id.rl_city:
                    if (provinceId == null)
                        ActivityUtils.showCenterToast(context, "请先选择省份", Toast.LENGTH_SHORT);
                    requestChooseItem(RequestEnum.CITY);
                    break;
                case R.id.rl_branch:
                    if (bankId == null)
                        ActivityUtils.showCenterToast(context, "请先选择银行", Toast.LENGTH_SHORT);
                    if (provinceId == null)
                        ActivityUtils.showCenterToast(context, "请先选择省份", Toast.LENGTH_SHORT);
                    if (cityId == null)
                        ActivityUtils.showCenterToast(context, "请先选择城市", Toast.LENGTH_SHORT);

                    requestChooseItem(RequestEnum.BRANCH);

                    break;
            }

        }
    };

    private void requestChooseItem(final RequestEnum requestEnum) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            ContractChooseItemResponseData responseData;
            Dialog dialog;
            HttpManagerOpenAccountContract httpManger = new HttpManagerOpenAccountContract(context);
            String title;//选择页面要显示的题目
            int requestCode;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ActivityUtils.showLoadingProgressDialog(context, "");
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {

                    if (requestEnum == RequestEnum.BANK) {
                        responseData = httpManger.requestBankList();

                        title = "选择银行";
                        requestCode = BANK;
                    } else if (requestEnum == RequestEnum.PROVINCE) {
                        responseData = httpManger.requestProvinceList("");
                        title = "选择省份";
                        requestCode = PROVINCE;

                    } else if (requestEnum == RequestEnum.CITY) {

                        responseData = httpManger.requestCityList(provinceId);
                        title = "选择城市";
                        requestCode = CITY;

                    } else if (requestEnum == RequestEnum.BRANCH) {
                        responseData = httpManger.requestBranchBankList(cityId, bankId);
                        title = "选择分支银行";
                        requestCode = BRANCH;

                    }
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
                dialog.dismiss();
                if (0 == responseData.getRetCode()) {
                    Intent intent = new Intent();
                    intent.setClass(context, ChooseItemActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CONTRACT_CHOOSE_ITEM_DATA, responseData.getChooseItemDataArrayList());
                    intent.putExtras(bundle);
                    intent.putExtra(ChooseItemActivity.TITLE, title);
                    startActivityForResult(intent, requestCode);

                } else {
                    ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_LONG);
                }

            }

        });
    }

    /**
     * 发送验证码,检查验证码,签约
     *
     * @param requestEnum 请求的类型
     */
    private void requestFunction(final RequestEnum requestEnum) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            BaseResponseDataOpenAccountContract responseData;
            Dialog dialog;
            HttpManagerOpenAccountContract httpManager = new HttpManagerOpenAccountContract(context);

            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ActivityUtils.showLoadingProgressDialog(context, "");
            }

            @SuppressWarnings("ResourceType")
            protected Boolean doInBackground(Void... params) {
                try {

                    if (requestEnum == RequestEnum.SEND_VERIFY_CODE) {
                        responseData = httpManager.requestContractCode(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getPhone());

                    } else if (requestEnum == RequestEnum.CONTRACT) {
                        //先检查验证码


//                        responseData = httpManager.requestContractCode(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getPhone(), et_code.getText().toString());
//                        responseData.setRetCode(0);

//                        if (responseData.getRetCode() == 0) {
                            //请求签约

//                            "bank_account":"4392260020173026",
//                                    "bank_name":"工商",
//                                    "bank_province":"110",
//                                    "bank_id":"900",
//                                    "ip":"",
//                                    "branch_id":"102100029679"

                            responseData = httpManager.requestContract(et_banknumber.getText().toString(), tv_bank.getText().toString(), provinceId, branchId,et_code.getText().toString());
//                        }

                        if(responseData.getRetCode()!=0){
                            return false;
                        }

                        HttpManagerOpenAccountContract httpManager = new HttpManagerOpenAccountContract(context);

                        if (responseData.getRetCode() == 0) {
                            //获取开户信息
                            responseData = httpManager.requestOpenAccountInfo(myapp.getShipanTradeEntity().getUserId());
                        }
                        HttpManagerOpenAccountContract httpManger = new HttpManagerOpenAccountContract(context);

                        ContractChooseItemResponseData mResponseData = httpManger.requestProvinceList(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).getProvince_id());
                        myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).setProvince_name(mResponseData.getChooseItemDataArrayList().get(0).getName());
                        mResponseData = httpManger.requestBranchInfo(myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).getBranch_id());
                        try {
                            //未签约账户,服务器返回其他签约信息不为空的问题.待验证
                            myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().get(0).setBranch_name(mResponseData.getChooseItemDataArrayList().get(0).getName());
                        } catch (Exception e) {

                        }
                    }
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
                dialog.dismiss();
                if (0 == responseData.getRetCode()) {
                    if (requestEnum == RequestEnum.SEND_VERIFY_CODE) {

                    } else if (requestEnum == RequestEnum.CONTRACT) {
                                                   ActivityUtils.showAlert_onlyOk(context, "签约成功", new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           finish();
                                                       }
                                                   });
                    }

                } else {
                   // ActivityUtils.showCenterToast(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Toast.LENGTH_LONG);
              ActivityUtils.showAlert(context,responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");
//                    Snackbar.make(ll_main, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")", Snackbar.LENGTH_LONG)
//                            .setAction("重试", new View.OnClickListener() {
//                                public void onClick(View v) {
//                                    request(requestType);
//                                }
//                            }).show();
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
                        if (MyApplication.recLen_contract != 60) {
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
        rl_bank = (RelativeLayout) findViewById(R.id.rl_bank);
        rl_province = (RelativeLayout) findViewById(R.id.rl_province);
        rl_city = (RelativeLayout) findViewById(R.id.rl_city);
        rl_branch = (RelativeLayout) findViewById(R.id.rl_branch);
        tv_bank = (TextView) findViewById(R.id.tv_bank);
        tv_province = (TextView) findViewById(R.id.tv_province);
        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_branch = (TextView) findViewById(R.id.tv_branch);
        til_banknumber = (TextInputLayout) findViewById(R.id.til_banknumber);
        et_banknumber = til_banknumber.getEditText();
        et_code = (EditText) findViewById(R.id.et_code);
        bt_getCode = (Button) findViewById(R.id.bt_getcode);
        bt_contract = (Button) findViewById(R.id.bt_contract);


        rl_bank.setOnClickListener(mClickListener);
        rl_province.setOnClickListener(mClickListener);
        rl_city.setOnClickListener(mClickListener);
        rl_branch.setOnClickListener(mClickListener);
    }


    /**
     * 设置验证码 和 开户按钮的点击事件
     */
    private void setVerifyAndContractButtonClickListener() {
        // 获取验证码
        bt_getCode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setCodeButtonUnavailable();
                timer = new Timer();
                task = new TimerTask() {
                    public void run() {
                        runOnUiThread(new Runnable() { // UI thread
                            public void run() {
                                MyApplication.recLen_contract--;
                                if (MyApplication.recLen_contract < 1) {
                                    timer.cancel();
                                    task.cancel();
                                    MyApplication.recLen_contract = 60;
                                }
                            }
                        });
                    }
                };
                timer.schedule(task, 0, 1000);
                requestFunction(RequestEnum.SEND_VERIFY_CODE);
            }

        });

        bt_contract.setOnClickListener(new View.OnClickListener() {
                                           public void onClick(View v) {
                                               if (et_banknumber.getText() != null && !et_banknumber.getText().toString().equals("")
                                                       && !bankId.equals("")
                                                       && !provinceId.equals("")
                                                       && !cityId.equals("")
                                                       && !branchId.equals("")) {
                                                   requestFunction(RequestEnum.CONTRACT);
                                               } else {
                                                   Snackbar.make(v, "请检查填写内容", Snackbar.LENGTH_SHORT).show();
                                               }
                                           }
                                       }
        );


    }

    private void setCodeButtonUnavailable() {
        WidgetHelper.setButtonGreyUnavailable(bt_getCode, "" + MyApplication.recLen_contract + "秒后可重新获取");
    }

    private void setCodeButtonAvailable() {
        WidgetHelper.setButtonOrangeAvailable(bt_getCode, "获取验证码");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BANK:
                if (resultCode == Constant.CODE_SUCCESS) {
                    tv_bank.setText(data.getStringExtra(ChooseItemActivity.KEY_CODENAME));
                    bankId = data.getStringExtra(ChooseItemActivity.KEY_CODEID);
                }
                break;
            case PROVINCE:
                if (resultCode == Constant.CODE_SUCCESS) {
                    tv_province.setText(data.getStringExtra(ChooseItemActivity.KEY_CODENAME));
                    provinceId = data.getStringExtra(ChooseItemActivity.KEY_CODEID);
                }
                break;
            case CITY:
                if (resultCode == Constant.CODE_SUCCESS) {
                    tv_city.setText(data.getStringExtra(ChooseItemActivity.KEY_CODENAME));
                    cityId = data.getStringExtra(ChooseItemActivity.KEY_CODEID);
                }
                break;
            case BRANCH:
                if (resultCode == Constant.CODE_SUCCESS) {
                    tv_branch.setText(data.getStringExtra(ChooseItemActivity.KEY_CODENAME));
                    branchId = data.getStringExtra(ChooseItemActivity.KEY_CODEID);
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);

    }
}
