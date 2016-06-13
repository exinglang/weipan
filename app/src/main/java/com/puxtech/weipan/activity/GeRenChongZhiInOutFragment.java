package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.AccountInfoData;
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.OpenAccountResponseData;
import com.puxtech.weipan.helper.OtherHelper;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerMoney;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.network.HttpManagerTrade;
import com.puxtech.weipan.util.ActivityUtils;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;


public class GeRenChongZhiInOutFragment extends BaseFragment {
    private TextView tv_tixianzijin, tv_prompt;
    private EditText et_amount, et_password, et_code;
    Button bt_changepassword, bt_commit, bt_getcode;
    private RadioButton rb_in, rb_out;
    private Context context;
    private View view;
    private final int REQUEST_TYPE_INOUT = 1;
    private final int REQUEST_TYPE_CHECK_CODE = 2;
    private Timer timer;
    private TimerTask task;
    LinearLayout ll_checkcode, ll_password;

    public GeRenChongZhiInOutFragment(Context context) {
        super();
        this.context = context;

    }

    public GeRenChongZhiInOutFragment() {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            try {
                view = inflater.inflate(R.layout.geren_chongzhi_inout_fragment, container, false);
                initWidget(view);
                setClickListener();
                setInView();
                startRefeshTimer();

            } catch (InflateException e) {
            }

        }
        return view;
    }

    private void setClickListener() {
        bt_changepassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(context, GeRenChongZhiInOutChangePasswordActivity.class));

            }
        });
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (et_amount.getText().toString().equals("") || et_password.getText().toString().equals("")) {
//                    ActivityUtils.showCenterToast(context, "请检查数据", Toast.LENGTH_SHORT);
//                    return;
//                }
                request(REQUEST_TYPE_INOUT);

            }
        });

        rb_in.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setInView();
                } else {
                    setOutView();
                }
            }
        });
//        bt_getcode_code.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                request(REQUEST_TYPE_CHECK_CODE);
//
//
//            }
//        });


        bt_getcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setCodeButtonUnavailable();
                timer = new Timer();
                task = new TimerTask() {
                    public void run() {
                        new Thread(new Runnable() { // UI thread
                            public void run() {
                                MyApplication.recLen_in--;
                                if (MyApplication.recLen_in < 1) {
                                    timer.cancel();
                                    task.cancel();
                                    MyApplication.recLen_in = 60;
                                }
                            }
                        });
                    }
                };
                timer.schedule(task, 0, 1000);
                request(REQUEST_TYPE_CHECK_CODE);
            }

        });
    }
    private void setCodeButtonUnavailable() {
        WidgetHelper.setButtonGreyUnavailable(bt_getcode, "" + MyApplication.recLen_in + "秒后可重新获取");
    }
    private void setCodeButtonAvailable() {
        WidgetHelper.setButtonOrangeAvailable(bt_getcode, "获取验证码");
    }
//        rb_out.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//
//
//            }
//        });


    /**
     * 出入金请求
     */
    private void request(final int reqeusttype) {
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
                    HttpManagerMoney httpManager = new HttpManagerMoney(context);
                    if (reqeusttype == REQUEST_TYPE_INOUT) {
                        if (rb_in.isChecked()) {

                            responseData = httpManager.requestIn(et_code.getText().toString(),et_amount.getText().toString());
                        } else {
                            responseData = httpManager.requestInOut("O",
                                    et_amount.getText().toString(), et_password.getText().toString());


                        }
                        if(responseData.getRetCode()!=0){
                            return false;
                        }
                        HttpManagerTrade httpManagerTrade = new HttpManagerTrade(context,myapp.getShipanTradeEntity());

                        responseData = httpManagerTrade.accountInfoRequest();

                    } else if (reqeusttype == REQUEST_TYPE_CHECK_CODE) {

                        responseData = httpManager.requestInCheckCode();
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


                    if (reqeusttype == REQUEST_TYPE_INOUT) {


                        ActivityUtils.showAlert(context, rb_in.isChecked() ? "入金成功" : "出金成功");
                    } else if (reqeusttype == REQUEST_TYPE_CHECK_CODE) {
                        ActivityUtils.showAlert(context, "短信发送成功,请注意查收");

                    }
                } else {
                    ActivityUtils.showAlert(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");

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
              new Thread(new Runnable() { // UI thread
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
    private void initWidget(View view) {
        tv_tixianzijin = (TextView) view.findViewById(R.id.tv_tixianzijin);
        tv_prompt = (TextView) view.findViewById(R.id.tv_prompt);
        et_amount = (EditText) view.findViewById(R.id.et_amount);
        et_password = (EditText) view.findViewById(R.id.et_password);
        bt_changepassword = (Button) view.findViewById(R.id.bt_changepassword);
        bt_commit = (Button) view.findViewById(R.id.bt_commit);
        rb_in = (RadioButton) view.findViewById(R.id.rb_in);
        rb_out = (RadioButton) view.findViewById(R.id.rb_out);
        ll_checkcode = (LinearLayout) view.findViewById(R.id.ll_checkcode);
        ll_password = (LinearLayout) view.findViewById(R.id.ll_password);
        et_code = (EditText) view.findViewById(R.id.et_code);
        bt_getcode = (Button) view.findViewById(R.id.bt_getcode);
    }

    private void setInView() {
        ll_checkcode.setVisibility(View.VISIBLE);
        ll_password.setVisibility(View.GONE);
    }

    private void setOutView() {
        ll_checkcode.setVisibility(View.GONE);
        ll_password.setVisibility(View.VISIBLE);

    }
}
