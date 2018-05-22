package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.Balance.BalanceSeletorFragment;
import com.accountingmanager.Fragment.Accounting.BankFinancing.BankFinancingHomeBankSelectorFragment;
import com.accountingmanager.Fragment.Accounting.DigitalCurrency.DigitalCurrencyHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.ForeignExchange.ForeignExchangeHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.Fund.FundContentInputFragment;
import com.accountingmanager.Fragment.Accounting.Liabilities.LiabilitiesSelectorFragment;
import com.accountingmanager.Fragment.Accounting.MonetaryFund.MonetaryFundHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.NationalDebt.NationalDebtHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.Other.AddAssetsOtherHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.P2PNetLoan.NetLoanHomeSelectorFragment;
import com.accountingmanager.Fragment.Accounting.Shares.SharesContentInputFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.Adr.MResource;
import com.accountingmanager.Sys.Utils.ToastUtils;
import com.accountingmanager.Utils.AddAssetsMenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加资产
 * Created by Home-Pc on 2017/4/18.
 */

public class AddAssetsFragment extends ContentBaseFragment implements View.OnClickListener {

    private Button addAssets_search;
    private RecyclerView addAssetsLiveContentShow, addAssetsPropertyContentShow,
            addAssetsNationalContentShow, addAssetsOtherContentShow;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_addassets_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        addAssets_search = (Button) fgView.findViewById(R.id.addAssets_search);
        addAssetsLiveContentShow = (RecyclerView) fgView.findViewById(R.id.addAssetsLiveContentShow);
        addAssetsPropertyContentShow = (RecyclerView) fgView.findViewById(R.id.addAssetsPropertyContentShow);
        addAssetsNationalContentShow = (RecyclerView) fgView.findViewById(R.id.addAssetsNationalContentShow);
        addAssetsOtherContentShow = (RecyclerView) fgView.findViewById(R.id.addAssetsOtherContentShow);
    }

    @Override
    protected void initEvent() {
        setTitleWhiteStyle(getResources().getString(R.string.selector_property));
        addAssets_search.setOnClickListener(this);

        observeMessage();
        setMenuShowContent();
    }

    private void observeMessage() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3);
        observeMessage(AppConfig.getInstance().SELECTOR_FUND_HOME_STATE);
        observeMessage(AppConfig.getInstance().SELECTOR_ACCUMULATION_FUND);
        observeMessage(AppConfig.getInstance().SELECTOR_SHARES_HOME_STATE);
    }

    private void setMenuShowContent() {
        setLiveContentDate();
        setPropertyContentDate();
        setNationalContentDate();
        setOtherContentDate();
    }

    /**
     * @param iconRes icon
     * @param textRes menuName
     * @return
     */
    private List<AssetsElementModel> getMenuContentContent(int iconRes, int textRes, int typeRes,int MenuTypes) {
        List<AssetsElementModel> contentList = new ArrayList<>();
        String[] drawables = getResources().getStringArray(iconRes);
        String[] texts = getResources().getStringArray(textRes);
        int[] types = getResources().getIntArray(typeRes);
        int[] menuTypes = getResources().getIntArray(MenuTypes);
        if (drawables.length == texts.length && drawables.length == types.length) {
            for (int i = 0; i < drawables.length; i++) {
                AssetsElementModel model = new AssetsElementModel();
                int drawable = MResource.getIdByNameForDrawable(getActivity(), drawables[i]);
                model.setMenuIcon(drawable);
                model.setGroupIcon(drawable);
                model.setMenuName(texts[i]);
                model.setGroupName(texts[i]);
                model.setType(String.valueOf(types[i]));
                model.setMenuType(String.valueOf(menuTypes[i]));
                contentList.add(model);
            }
        }
        return contentList;
    }

    /**
     * 生活资产
     */
    private void setLiveContentDate() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        addAssetsLiveContentShow.setLayoutManager(gridLayoutManager);
        AddAssetsMenuAdapter menuAdapter = new AddAssetsMenuAdapter(getActivity());
        addAssetsLiveContentShow.setAdapter(menuAdapter);

        final List<AssetsElementModel> liveContents = getMenuContentContent(R.array.AssetsLiveMenuIcon,
                R.array.AssetsLiveMenuTvShow, R.array.AssetsLiveColorType,R.array.AssetsLiveMenuType);
        menuAdapter.setMenuList(liveContents);

        menuAdapter.setOnItemClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, liveContents.get(position));
                switch (position) {
                    case 0://现金
                        ServiceBaseActivity.startActivity(getActivity(), AddInputContentFragment.class.getName(), bundle);
                        break;
                    case 1://银行卡
                    case 2://信用卡
                        ServiceBaseActivity.startActivity(getActivity(), SelectorBankListFragment.class.getName(), bundle);
                        break;
                    case 3://账户余额
                        ServiceBaseActivity.startActivity(getActivity(), BalanceSeletorFragment.class.getName(), bundle);
                        break;
                    case 4://公积金
                        bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG, AppConfig.getInstance().SELECTOR_ACCUMULATION_FUND);
                        bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_CONTENT_TAG, getString(R.string.AddAssetsMsg_1));
                        ServiceBaseActivity.startActivity(getActivity(), AddAssetsSelectorTypeFragment.class.getName(), bundle);
                        break;
                    case 5://贷款/欠款
                        ServiceBaseActivity.startActivity(getActivity(), LiabilitiesSelectorFragment.class.getName(), bundle);
                        break;
                    case 6://借出/应收
                        ServiceBaseActivity.startActivity(getActivity(), LendInputFragment.class.getName(), bundle);
                        break;
                    case 7://固定资产
                        ServiceBaseActivity.startActivity(getActivity(), FixedAssetsFragment.class.getName(), bundle);
                        break;
                    default:
                        ToastUtils.shortShow(liveContents.get(position).getMenuName());
                        break;
                }
            }
        });
    }

    /**
     * 理财资产
     */
    private void setPropertyContentDate() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        addAssetsPropertyContentShow.setLayoutManager(gridLayoutManager);
        AddAssetsMenuAdapter menuAdapter = new AddAssetsMenuAdapter(getActivity());
        addAssetsPropertyContentShow.setAdapter(menuAdapter);

        final List<AssetsElementModel> PropertyContents = getMenuContentContent(R.array.AssetsPropertyMenuIcon,
                R.array.AssetsPropertyMenuTvShow, R.array.AssetsPropertyColorType,R.array.AssetsPropertyMenuType);
        menuAdapter.setMenuList(PropertyContents);

        menuAdapter.setOnItemClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, PropertyContents.get(position));
                switch (position) {
                    case 0://P2P 网贷
                        ServiceBaseActivity.startActivity(getActivity(), NetLoanHomeSelectorFragment.class.getName(), bundle);
                        break;
                    case 1://货币基金
                        ServiceBaseActivity.startActivity(getActivity(), MonetaryFundHomeSelectorFragment.class.getName(), bundle);
                        break;
                    case 2://基金
                        bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG, AppConfig.getInstance().SELECTOR_FUND_HOME_STATE);
                        ServiceBaseActivity.startActivity(getActivity(), AddAssetsSelectorTypeFragment.class.getName(), bundle);
                        break;
                    case 3://股票
                        bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG, AppConfig.getInstance().SELECTOR_SHARES_HOME_STATE);
                        ServiceBaseActivity.startActivity(getActivity(), AddAssetsSelectorTypeFragment.class.getName(), bundle);
                        break;
                    case 4://定期存款
                        ServiceBaseActivity.startActivity(getActivity(), FixedDepositFragment.class.getName(), bundle);
                        break;
                    case 5://银行理财
                        ServiceBaseActivity.startActivity(getActivity(), BankFinancingHomeBankSelectorFragment.class.getName(), bundle);
                        break;
                    default:
                        ToastUtils.shortShow(PropertyContents.get(position).getMenuName());
                        break;
                }
            }
        });
    }

    /**
     * 债券资产
     */
    private void setNationalContentDate() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        addAssetsNationalContentShow.setLayoutManager(gridLayoutManager);
        AddAssetsMenuAdapter menuAdapter = new AddAssetsMenuAdapter(getActivity());
        addAssetsNationalContentShow.setAdapter(menuAdapter);

        final List<AssetsElementModel> NationalContents = getMenuContentContent(R.array.AssetsNationalMenuIcon,
                R.array.AssetsNationalMenuTvShow, R.array.AssetsNationalColorType,R.array.AssetsNationalMenuType);
        menuAdapter.setMenuList(NationalContents);

        menuAdapter.setOnItemClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, NationalContents.get(position));
                switch (position) {
                    case 0://国债
                        ServiceBaseActivity.startActivity(getActivity(), NationalDebtHomeSelectorFragment.class.getName(), bundle);
                        break;
                    case 1://保险理财
                    case 2://私募
                    case 3://信托
                    case 4://票据
                        ServiceBaseActivity.startActivity(getActivity(), InsuranceInputContentFragment.class.getName(), bundle);
                        break;
                    case 5://外汇
                        ServiceBaseActivity.startActivity(getActivity(), ForeignExchangeHomeSelectorFragment.class.getName(), bundle);
                        break;
                    case 6://数字货币
                        ServiceBaseActivity.startActivity(getActivity(), DigitalCurrencyHomeSelectorFragment.class.getName(), bundle);
                        break;
                    default:
                        ToastUtils.shortShow(NationalContents.get(position).getMenuName());
                        break;
                }
            }
        });
    }

    /**
     * 其他
     */
    private void setOtherContentDate() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        addAssetsOtherContentShow.setLayoutManager(gridLayoutManager);
        AddAssetsMenuAdapter menuAdapter = new AddAssetsMenuAdapter(getActivity());
        addAssetsOtherContentShow.setAdapter(menuAdapter);

        final List<AssetsElementModel> OtherContents = getMenuContentContent(R.array.AssetsOtherMenuIcon,
                R.array.AssetsOtherMenuTvShow, R.array.AssetsOtherColorType,R.array.AssetsOtherMenuType);
        menuAdapter.setMenuList(OtherContents);

        menuAdapter.setOnItemClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, OtherContents.get(position));
                ServiceBaseActivity.startActivity(getActivity(), AddAssetsOtherHomeSelectorFragment.class.getName(), bundle);
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

    }

    @Override
    public void onReceiveMessage(String msgKey, Object msgObject) {
        super.onReceiveMessage(msgKey, msgObject);
        if (msgKey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3)) {
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_1, msgObject);
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_2, msgObject);
            getActivity().finish();
        } else if (msgKey.equals(AppConfig.getInstance().SELECTOR_ACCUMULATION_FUND)) {// 公积金手动添加页面
            Bundle bundle = (Bundle) msgObject;
            ServiceBaseActivity.startActivity(getActivity(), AccumulationFundInputFragment.class.getName(), bundle);
        } else if (msgKey.equals(AppConfig.getInstance().SELECTOR_FUND_HOME_STATE)) {//基金选择界面
            Bundle bundle = (Bundle) msgObject;
            ServiceBaseActivity.startActivity(getActivity(), FundContentInputFragment.class.getName(), bundle);
        } else if (msgKey.equals(AppConfig.getInstance().SELECTOR_SHARES_HOME_STATE)) {//股票
            Bundle bundle = (Bundle) msgObject;
            ServiceBaseActivity.startActivity(getActivity(), SharesContentInputFragment.class.getName(), bundle);
        }
    }
}
