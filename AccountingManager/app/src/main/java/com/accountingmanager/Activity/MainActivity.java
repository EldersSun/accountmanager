package com.accountingmanager.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.accountingmanager.Base.BaseActivity;
import com.accountingmanager.Fragment.Accounting.Home.AccountingHomeFragment;
import com.accountingmanager.Fragment.Assets.AssetsAnalysisFragment;
import com.accountingmanager.Fragment.Assets.AssetsHomeFragment;
import com.accountingmanager.Fragment.Me.AccountHomeFragment;
import com.accountingmanager.Fragment.News.NewsHomeFragment;
import com.accountingmanager.Fragment.Tools.ToolsMainFragment;
import com.accountingmanager.R;


/**
 * Created by Home_Pc on 2017/4/17.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private RadioButton main_accounting,main_assets,main_tools,main_news;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.ac_main_layout,null);
    }

    @Override
    protected void initWidgets() {
        main_accounting = (RadioButton) findViewById(R.id.main_accounting);
        main_assets = (RadioButton) findViewById(R.id.main_assets);
        main_tools = (RadioButton) findViewById(R.id.main_tools);
        main_news = (RadioButton) findViewById(R.id.main_news);
    }

    @Override
    protected void initEvent() {
        main_accounting.setOnClickListener(this);
        main_assets.setOnClickListener(this);
        main_tools.setOnClickListener(this);
        main_news.setOnClickListener(this);

        pushFragmentController(new AccountingHomeFragment(),false);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_accounting:
                pushFragmentController(new AccountingHomeFragment(),false);
                break;
            case R.id.main_assets:
                pushFragmentController(new AssetsHomeFragment(),false);
                break;
            case R.id.main_tools:
                pushFragmentController(new ToolsMainFragment(),false);
                break;
            case R.id.main_news:
                pushFragmentController(new AccountHomeFragment(),false);
                break;
        }
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
}
