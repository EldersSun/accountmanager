package com.accountingmanager.Fragment.Accounting.NationalDebt;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.StringUtils;
import com.accountingmanager.Utils.NationalHomeSelectorAdapter;

/**
 * 国债首页选择界面
 * Created by Home-Pc on 2017/5/15.
 */

public class NationalDebtHomeSelectorFragment extends ContentBaseFragment {

    private RecyclerView national_selector_show;
    private NationalHomeSelectorAdapter adapter;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_national_home_selector_layout,null);
    }

    @Override
    protected void initWidgets(View fgView) {
        national_selector_show = (RecyclerView) fgView.findViewById(R.id.national_selector_show);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_13);
        adapter = new NationalHomeSelectorAdapter(getActivity());

        national_selector_show.setLayoutManager(new LinearLayoutManager(getActivity()));
        national_selector_show.setAdapter(adapter);

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                RelativeLayout layout = (RelativeLayout) view;
                TextView tvShow = (TextView) layout.getChildAt(0);

                if(tvShow.getText() != null && !StringUtils.isBlank(tvShow.getText().toString())){
                    assetsElementModel.setMenuName(assetsElementModel.getMenuName() + AppConfig.getInstance().NAME_PART_LINE + tvShow.getText().toString());
                    bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                    ServiceBaseActivity.startActivity(getActivity(),NationalDebtContentInputFragment.class.getName(),bundle);
                }
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
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_13)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3,msgObject);
            getActivity().finish();
        }
    }
}
