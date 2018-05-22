package com.accountingmanager.Fragment.Accounting.Home;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.accountingmanager.Base.BaseFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.GreenDao.CommonUtils;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Utils.AccountingTypeClassicAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 经典视图
 * Created by Home-Pc on 2017/4/19.
 */

public class AccountingTypeClassicFragment extends BaseFragment {
    private RecyclerView v_RecyclerViewShow;
    private List<AssetsElementModel> adapterList = new ArrayList<>();
    private AccountingTypeClassicAdapter adapter;

    private AssetsElementModel model = new AssetsElementModel();


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.v_recyclerview_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        v_RecyclerViewShow = (RecyclerView) fgView.findViewById(R.id.v_RecyclerViewShow);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_2);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
        adapterList = CommonUtils.getInstance().contents();
        adapter = new AccountingTypeClassicAdapter(getActivity());
        v_RecyclerViewShow.setLayoutManager(manager);
        v_RecyclerViewShow.setAdapter(adapter);
        adapter.setAdapterList(adapterList);

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                model = adapterList.get(position);
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
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_2)) {
            adapterList.clear();
            adapterList = CommonUtils.getInstance().contents();
            adapter.setAdapterList(adapterList);
        }
    }
}
