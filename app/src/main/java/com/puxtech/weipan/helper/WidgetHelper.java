package com.puxtech.weipan.helper;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
/**
 * Created by mac on 15/11/10.
 */
public class WidgetHelper {

    /**
     * 初始化TitleBar 有子标题和后退按钮
     *  @param appCompatActivity  appCompatActivity
     * @param subTitle 子标题
     * @param toolbar titlebar
     */
    public static void initToolBarWithSubTitleAndFinishIcon(final AppCompatActivity appCompatActivity, String subTitle, Toolbar toolbar) {
        toolbar.setTitle(appCompatActivity.getResources().getString(R.string.app_name));
        toolbar.setSubtitle(subTitle);
        toolbar.setNavigationIcon(R.drawable.left_white);
        appCompatActivity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                appCompatActivity.setResult(Constant.CODE_FAIL);
                appCompatActivity.finish();
            }
        });
    }
    /**
     * 初始化TitleBar
     *  @param appCompatActivity
     * @param toolbar
     */
    public static void initToolBar(final AppCompatActivity appCompatActivity, Toolbar toolbar) {
        toolbar.setTitle(appCompatActivity.getResources().getString(R.string.app_name));
        appCompatActivity.setSupportActionBar(toolbar);

    }
    /**
     * 检测输入的姓名是否符合条件
     *
     * @param til
     * @return
     */
    public static TextWatcher checkTextInputLayoutName(final TextInputLayout til) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("[\u4e00-\u9fa5]{2,9}")) {
                    til.setError("只能输入2到9个汉字");

                } else {
                    til.setErrorEnabled(false);
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }

    /**
     * 检测EditText中的数字是否大于指定的数字
     * 大于就 error
     */
    public static TextWatcher checkIfEtDaYuIntNumber(final TextInputLayout til, final int number) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (Integer.valueOf(s.toString()) > number) {
                        til.setError("不能大于本单持仓数:" + number);
                    } else {
                        til.setErrorEnabled(false);
                    }
                }catch (Exception e){
                    til.setError("请输入正确的数值");
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }
    /**
     * 检测EditText中的数字是否大于指定的数字
     * 大于就 error
     */
    public static TextWatcher setEtXiaoYuFloatNumber(final TextInputLayout til, final Float number) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    til.setHint("<"+number);
                    if (Float.valueOf(s.toString()) > number) {
                        til.setError("请输入正确的数值");
                    } else {
                        til.setErrorEnabled(false);
                    }
                }catch (Exception e){
                    til.setError("请输入正确的数值");
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }
    /**
     * 检测EditText中的数字是否大于指定的数字
     * 大于就 error
     */
    public static TextWatcher setEtDaYuFloatNumber(final TextInputLayout til, final float number) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    til.setHint(">"+number);
                    if (Float.valueOf(s.toString()) < number) {
                        til.setError("请输入正确的数值");
                    } else {
                        til.setErrorEnabled(false);
                    }
                }catch (Exception e){
                    til.setError("请输入正确的数值");
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }
    /**
     * 身份证号是否正确
     *
     * @param til
     * @return
     */
    public static TextWatcher checkTextInputLayoutId(final TextInputLayout til) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().matches("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])")) {
                    til.setError("请输入正确的身份证号");
                } else {
                    til.setErrorEnabled(false);
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }

    /**
     * 手机号码是否正确
     *
     * @param til
     * @return
     */
    public static TextWatcher checkTextInputLayoutPhoneNumber(final TextInputLayout til) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 11) {
                    til.setError("请输入正确的手机号");
                } else {
                    til.setErrorEnabled(false);
                }
            }

            public void afterTextChanged(Editable s) {

            }
        };
        return textWatcher;

    }

    public static void setButtonGreyUnavailable(Button button, String text) {
        button.setText(text);
        setButtonUnavailable(button);

    }

    public static void setButtonGreyUnavailable(Button button) {
        setButtonUnavailable(button);
    }

    private static void setButtonUnavailable(Button button) {
        button.setBackgroundResource(R.drawable.home_function_hui);
        button.setClickable(false);
    }

    public static void setButtonOrangeAvailable(Button button, String text) {
        button.setText(text);
        setButtonAvailable(button);

    }

    public static void setButtonOrangeAvailable(Button button) {
        setButtonAvailable(button);
    }

    private static void setButtonAvailable(Button button) {
        button.setBackgroundResource(R.drawable.home_bt_selector_shape_orange);
        button.setClickable(true);
    }


}
