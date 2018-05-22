package com.accountingmanager.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.accountingmanager.Base.BaseActivity;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Account.UserLoginFragment;
import com.accountingmanager.Fragment.Account.UserRegisterFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Utils.NoUnderlineSpan;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Utils.ToastUtils;

/**
 * Created by Home-Pc on 2017/5/31.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText userLogin_input_userName;
    private Button userLogin_input_submit;
    private CheckBox isCheckCreateAccount;
    private TextView isCheckCreateAccountTvShow;
    private ImageButton userLogin_input_weChat, userLogin_input_QQ;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.ac_login_layout, null);
    }

    @Override
    protected void initWidgets() {
        userLogin_input_userName = (EditText) findViewById(R.id.userLogin_input_userName);
        userLogin_input_submit = (Button) findViewById(R.id.userLogin_input_submit);
        isCheckCreateAccount = (CheckBox) findViewById(R.id.isCheckCreateAccount);
        isCheckCreateAccountTvShow = (TextView) findViewById(R.id.isCheckCreateAccountTvShow);
        userLogin_input_weChat = (ImageButton) findViewById(R.id.userLogin_input_weChat);
        userLogin_input_QQ = (ImageButton) findViewById(R.id.userLogin_input_QQ);
    }

    @Override
    protected void initEvent() {
        userLogin_input_submit.setOnClickListener(this);
        userLogin_input_weChat.setOnClickListener(this);
        userLogin_input_QQ.setOnClickListener(this);

        isCheckCreateAccountTvShow.setText(setLinkUnderline());
        isCheckCreateAccountTvShow.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onResponsSuccess(int TAG, Object result) {

    }

    @Override
    public void onResponsFailed(int TAG, String result) {

    }

    @Override
    public void onNetConnectFailed(int TAG, String result) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userLogin_input_submit:
//                if (StringUtils.isBlank(userLogin_input_userName.getText().toString())) {
//                    ToastUtils.shortShow(getString(R.string.login_msg_4));
//                    return;
//                }
//
//                if (!isCheckCreateAccount.isChecked()) {
//                    ToastUtils.shortShow(getString(R.string.login_msg_5));
//                    return;
//                }
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.userLogin_input_weChat:
                ServiceBaseActivity.startActivity(this, UserLoginFragment.class.getName());
                break;
            case R.id.userLogin_input_QQ:
                ServiceBaseActivity.startActivity(this, UserRegisterFragment.class.getName());
                break;
        }
    }

    private SpannableString setLinkUnderline() {
        SpannableString spannableString = new SpannableString(isCheckCreateAccountTvShow.getText());
        NoUnderlineSpan underlineSpan = new NoUnderlineSpan();
        spannableString.setSpan(underlineSpan, 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.shortShow("用户协议");
            }
        }, 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.type_classic_color_1)),
                7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(underlineSpan, 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ToastUtils.shortShow("隐私声明");
            }
        }, 14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.type_classic_color_1)),
                14, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
