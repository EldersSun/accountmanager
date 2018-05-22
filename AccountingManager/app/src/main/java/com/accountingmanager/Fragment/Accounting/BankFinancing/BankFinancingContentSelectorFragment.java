package com.accountingmanager.Fragment.Accounting.BankFinancing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Utils.BankFinancingContentAdapter;

/**
 * 银行理财二级选择界面
 * Created by Home-Pc on 2017/5/12.
 */

public class BankFinancingContentSelectorFragment extends ContentBaseFragment {

    private RecyclerView BankFinancingContentSelectorContentShow;
    private BankFinancingContentAdapter adapter;
    private LinearLayoutManager manager;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_bankfinancing_content_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        BankFinancingContentSelectorContentShow = (RecyclerView) fgView.findViewById(R.id.BankFinancingContentSelectorContentShow);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_11);

        adapter = new BankFinancingContentAdapter(getActivity());
        manager = new LinearLayoutManager(getActivity());

        BankFinancingContentSelectorContentShow.setLayoutManager(manager);
        BankFinancingContentSelectorContentShow.setAdapter(adapter);

        bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
            setTitleWhiteStyle(assetsElementModel.getMenuName());
        }

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                RelativeLayout layout = (RelativeLayout) view;
                TextView tvShow = (TextView) layout.getChildAt(0);
                if(tvShow.getText() != null && tvShow.getText().toString() != null){
                    assetsElementModel.setMenuName(assetsElementModel.getMenuName() + AppConfig.getInstance().NAME_PART_LINE + tvShow.getText().toString());
                    bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                }
                ServiceBaseActivity.startActivity(getActivity(),BankFinancingContentInputFragment.class.getName(),bundle);
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
    protected void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_11)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_12,msgObject);
            getActivity().finish();
        }
    }
}
