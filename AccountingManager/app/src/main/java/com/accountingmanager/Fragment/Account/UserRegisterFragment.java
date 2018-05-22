package com.accountingmanager.Fragment.Account;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Widgets.CustomGetCodeWidgets;

/**
 * 注册用户
 * Created by Home-Pc on 2017/5/31.
 */

public class UserRegisterFragment extends ContentBaseFragment implements View.OnClickListener {

    private TextView User_Register_tvShow;
    private EditText User_Register_code, User_Register_pwd;
    private Button User_Register_submit;

    private CustomGetCodeWidgets User_Register_getCode;


    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_user_register_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        User_Register_tvShow = (TextView) fgView.findViewById(R.id.User_Register_tvShow);
        User_Register_code = (EditText) fgView.findViewById(R.id.User_Register_code);
        User_Register_pwd = (EditText) fgView.findViewById(R.id.User_Register_pwd);
        User_Register_getCode = (CustomGetCodeWidgets) fgView.findViewById(R.id.User_Register_getCode);
        User_Register_submit = (Button) fgView.findViewById(R.id.User_Register_submit);
    }

    @Override
    protected void initEvent() {
        User_Register_getCode.setOnClickListener(this);
        User_Register_submit.setOnClickListener(this);

        setTitleWhiteStyle(getString(R.string.user_register_msg_5));

        User_Register_getCode.setOnTimerInterfaceListener(new CustomGetCodeWidgets.OnTimerInterfaceListener() {
            @Override
            public void OnTick(long millisUntilFinished) {
                User_Register_getCode.setText(getString(R.string.user_register_msg_6) + millisUntilFinished / 1000 + getString(R.string.user_register_msg_7));
            }

            @Override
            public void OnFinish() {
                User_Register_getCode.setText(R.string.user_register_msg_8);
            }
        });

        User_Register_getCode.setTimer(AppConfig.getInstance().millisInFuture,AppConfig.getInstance().countDownInterval);
        User_Register_getCode.startTimer();
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
            case R.id.User_Register_submit:
                break;
            case R.id.User_Register_getCode:
                User_Register_getCode.startTimer();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        User_Register_getCode.monitorTimerStatue();
    }
}
