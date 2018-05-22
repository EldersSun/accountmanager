package com.accountingmanager.Fragment.Accounting.Home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.AddAssetsFragment;
import com.accountingmanager.Fragment.Accounting.YesterdayEarningsFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Shared.SharedPreferencesUtil;
import com.accountingmanager.Sys.Utils.Money;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Sys.Widgets.SelectorTimeDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 资产首页
 * Created by Home_Pc on 2017/4/17.
 */
public class AccountingHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private SwipeRefreshLayout accounting_home_SwipeRefresh;
    private Button accounting_home_add, accounting_home_earnings_money;
    private RadioButton accounting_home_type_Classic, accounting_home_type_Classification, accounting_home_type_Expire;
    private TextView accounting_home_earnings_tvShow;
    private ImageButton accounting_home_calendar;

    private List<AssetsElementModel> adapterList = new ArrayList<>();
    private SelectorTimeDialog selectorTimeDialog;

    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_accountinghome_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        accounting_home_SwipeRefresh = (SwipeRefreshLayout) fgView.findViewById(R.id.accounting_home_SwipeRefresh);
        accounting_home_add = (Button) fgView.findViewById(R.id.accounting_home_add);
        accounting_home_earnings_money = (Button) fgView.findViewById(R.id.accounting_home_earnings_money);
        accounting_home_type_Classic = (RadioButton) fgView.findViewById(R.id.accounting_home_type_Classic);
        accounting_home_type_Classification = (RadioButton) fgView.findViewById(R.id.accounting_home_type_Classification);
        accounting_home_type_Expire = (RadioButton) fgView.findViewById(R.id.accounting_home_type_Expire);
        accounting_home_earnings_tvShow = (TextView) fgView.findViewById(R.id.accounting_home_earnings_tvShow);

        accounting_home_calendar = (ImageButton) fgView.findViewById(R.id.accounting_home_calendar);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_1);

        accounting_home_SwipeRefresh.setOnRefreshListener(this);
        accounting_home_add.setOnClickListener(this);
        accounting_home_type_Classic.setOnClickListener(this);
        accounting_home_type_Classification.setOnClickListener(this);
        accounting_home_type_Expire.setOnClickListener(this);
        accounting_home_earnings_money.setOnClickListener(this);
        accounting_home_calendar.setOnClickListener(this);

        selectorTimeDialog = new SelectorTimeDialog(getActivity());
        selectorTimeDialog.setDialogOperationclick(new SelectorTimeDialog.onCustomDialogOperationclick() {
            @Override
            public void Confirm(Calendar calendar) {

            }

            @Override
            public void cancel() {

            }
        });

        adapterList = CommonUtils.getInstance().contents();
        accounting_home_earnings_tvShow.setText(getAllNum() == null ? "" : getAllNum());

        int homePage = SharedPreferencesUtil.getInt(getActivity(), AppConfig.getInstance().SELECTOR_HOME_PAGE);

        switch (homePage) {
            case 2://分类
                accounting_home_type_Classification.setChecked(true);
                pushFragmentController(R.id.accounting_home_content, new AccountingTypeClassifacationFragment(), null, false);
                break;
            case 3://到期
                accounting_home_type_Expire.setChecked(true);
                pushFragmentController(R.id.accounting_home_content, new AccouningExpireFeagment(), null, false);
                break;
            case 1:
            default://经典
                accounting_home_type_Classic.setChecked(true);
                pushFragmentController(R.id.accounting_home_content, new AccountingTypeClassicFragment(), null, false);
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

    @Override
    public void onRefresh() {
        accounting_home_SwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accounting_home_add://添加账单
                ServiceBaseActivity.startActivity(getActivity(), AddAssetsFragment.class.getName());
                break;
            case R.id.accounting_home_earnings_money://昨天收益
                ServiceBaseActivity.startActivity(getActivity(), YesterdayEarningsFragment.class.getName());
                break;
            case R.id.accounting_home_type_Classic://经典视图
                SharedPreferencesUtil.putInt(getActivity(), AppConfig.getInstance().SELECTOR_HOME_PAGE, 1);
                pushFragmentController(R.id.accounting_home_content, new AccountingTypeClassicFragment(), null, false);
                break;
            case R.id.accounting_home_type_Classification://分类视图
                SharedPreferencesUtil.putInt(getActivity(), AppConfig.getInstance().SELECTOR_HOME_PAGE, 2);
                pushFragmentController(R.id.accounting_home_content, new AccountingTypeClassifacationFragment(), null, false);
                break;
            case R.id.accounting_home_type_Expire://到期视图
                SharedPreferencesUtil.putInt(getActivity(), AppConfig.getInstance().SELECTOR_HOME_PAGE, 3);
                pushFragmentController(R.id.accounting_home_content, new AccouningExpireFeagment(), null, false);
                break;
            case R.id.accounting_home_calendar://日历控件
                selectorTimeDialog.show();
                break;
        }
    }

    @Override
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_1)) {
            adapterList.clear();
            adapterList = CommonUtils.getInstance().contents();
            accounting_home_earnings_tvShow.setText(getAllNum() == null ? "" : getAllNum());
        }
    }

    /**
     * 获得总数
     *
     * @return
     */
    private String getAllNum() {
        Money money = new Money();
        for (int i = 0; i < adapterList.size(); i++) {
            if (!StringUtils.isBlank(adapterList.get(i).getAmount())) {
                money.addTo(new Money(adapterList.get(i).getAmount()));
            }
        }
        return money + getString(R.string.element);
    }

}
