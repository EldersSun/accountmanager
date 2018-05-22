package com.accountingmanager.Fragment.Accounting.BankFinancing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.AddAssetsSelectorTypeFragment;
import com.accountingmanager.Fragment.Accounting.Fund.FundDetailsContentInputFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.ArrayUtils;
import com.accountingmanager.Sys.Widgets.GroupRecyclerView.LatterView;
import com.accountingmanager.Utils.ContactAdapter;
import com.accountingmanager.Utils.DividerItemDecoration;

/**
 * 银行理财一级选择界面
 * Created by Home-Pc on 2017/5/12.
 */

public class BankFinancingHomeBankSelectorFragment extends ContentBaseFragment {

    private RecyclerView BankFinancingHomeSelectorContentShow;
    private LatterView BankFinancingHomeSelectorTypeShow;

    private ContactAdapter adapter;

    private LinearLayoutManager manager;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_bankfinancing_home_bank_selector_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        BankFinancingHomeSelectorContentShow = (RecyclerView) fgView.findViewById(R.id.BankFinancingHomeSelectorContentShow);
        BankFinancingHomeSelectorTypeShow = (LatterView) fgView.findViewById(R.id.BankFinancingHomeSelectorTypeShow);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_12);

        manager = new LinearLayoutManager(getActivity());
        adapter = new ContactAdapter(getActivity(), getResources().getStringArray(R.array.SelectorBankList));

        BankFinancingHomeSelectorContentShow.setLayoutManager(manager);
        BankFinancingHomeSelectorContentShow.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        BankFinancingHomeSelectorContentShow.setAdapter(adapter);

        BankFinancingHomeSelectorTypeShow.setCharacterListener(new LatterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                manager.scrollToPositionWithOffset(adapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                manager.scrollToPositionWithOffset(0, 0);
            }
        });

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                LinearLayout linearLayout = (LinearLayout) view;
                TextView tvShow = (TextView) linearLayout.getChildAt(1);
                assetsElementModel.setMenuName(tvShow.getText() == null ? "" : tvShow.getText().toString());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(),BankFinancingContentSelectorFragment.class.getName(),bundle);
            }
        });

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
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
    protected void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_12)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, msgObject);
            getActivity().finish();
        }
    }
}
