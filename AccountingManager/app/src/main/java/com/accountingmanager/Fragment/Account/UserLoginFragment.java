package com.accountingmanager.Fragment.Account;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;

/**
 * 用户登录
 * Created by Home-Pc on 2017/5/31.
 */

public class UserLoginFragment extends ContentBaseFragment implements View.OnClickListener {

    private TextView user_Login_tvShow;
    private EditText user_Login_input_pwd;
    private Button user_Login_submit, user_Login_rePwd;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_user_login_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        user_Login_tvShow = (TextView) fgView.findViewById(R.id.user_Login_tvShow);
        user_Login_input_pwd = (EditText) fgView.findViewById(R.id.user_Login_input_pwd);
        user_Login_submit = (Button) fgView.findViewById(R.id.user_Login_submit);
        user_Login_rePwd = (Button) fgView.findViewById(R.id.user_Login_rePwd);
    }

    @Override
    protected void initEvent() {
        user_Login_submit.setOnClickListener(this);
        user_Login_rePwd.setOnClickListener(this);

        setTitleWhiteStyle(getString(R.string.user_login_msg_3));
        setOnTitleMenuListener(getString(R.string.user_login_msg_4), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
            case R.id.user_Login_submit:
                break;
            case R.id.user_Login_rePwd:
                break;
        }
    }
}
