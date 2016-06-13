package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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
import com.puxtech.weipan.data.BaseResponseData;
import com.puxtech.weipan.data.OpenAccountResponseData;
import com.puxtech.weipan.helper.WidgetHelper;
import com.puxtech.weipan.network.HttpManagerMoney;
import com.puxtech.weipan.network.HttpManagerOpenAccountContract;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 15/11/4.
 */
public class GeRenChongZhiInOutChangePasswordActivity extends BaseAppCompatActivity {
    Toolbar toolbar;
    TextInputLayout til_oldpwd, til_newpwd, til_copypwd;
    EditText et_oldpwd, et_newpwd, et_copypwd;
    Button bt_commit;
    private static final String ONLYSIX = "密码长度只能为6位";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geren_chongzhi_inout_changpassword);
        InitWidget();
        WidgetHelper.initToolBarWithSubTitleAndFinishIcon(this, "修改资金密码", toolbar);
    }

    private void InitWidget() {
        til_oldpwd = (TextInputLayout) findViewById(R.id.til_oldpwd);
        til_newpwd = (TextInputLayout) findViewById(R.id.til_newpwd);
        til_copypwd = (TextInputLayout) findViewById(R.id.til_copypwd);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        et_oldpwd = (EditText) findViewById(R.id.et_oldpwd);
        et_newpwd = (EditText) findViewById(R.id.et_newpwd);
        et_copypwd = (EditText) findViewById(R.id.et_copypwd);

        bt_commit= (Button) findViewById(R.id.bt_commit);


        addTextChangedListener();
        til_oldpwd.setErrorEnabled(true);
        til_newpwd.setErrorEnabled(true);
        til_copypwd.setErrorEnabled(true);
        // 两次输入的新密码不一致
        bt_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!til_oldpwd.isErrorEnabled() && !til_newpwd.isErrorEnabled() && !til_copypwd.isErrorEnabled()) {
                    request();
                } else {
                    ActivityUtils.showCenterToast(context, "请检查输入的数据", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 请求
     *
     */
    private void request() {
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
                    //获取开户信息
                    responseData = httpManager.requestChangeMoneyPassword(et_oldpwd.getText().toString(),et_newpwd.getText().toString());
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


                    ActivityUtils.showAlert_onlyOk(context, "密码修改成功", new Runnable() {
                        @Override
                        public void run() {

                            finish();
                        }
                    });

                } else {
                    ActivityUtils.showAlert(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");

                }
            }
        });
    }

    /**
     * 添加对输入字符串的判断
     */
    private void addTextChangedListener() {
        et_oldpwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    til_oldpwd.setError("请输入原密码");
                } else if (s.length() > 6) {
                    til_oldpwd.setError(ONLYSIX);
                } else {
                    til_oldpwd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_newpwd.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    til_newpwd.setError("请输入新密码");

                } else if (s.toString().equals(et_oldpwd.getText().toString())) {

                    til_newpwd.setError("请输入与旧密码不同的新密码");

                } else if (s.length() > 6) {

                    til_newpwd.setError(ONLYSIX);

                } else {
                    til_newpwd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_copypwd.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    til_copypwd.setError("请重复新密码");

                } else if (!s.toString().equals(et_newpwd.getText().toString())) {

                    til_copypwd.setError("两次输入的新密码不一致");

                } else if (s.length() > 6) {

                    til_copypwd.setError(ONLYSIX);

                } else {
                    til_copypwd.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


}
