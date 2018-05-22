package com.accountingmanager.Fragment.Accounting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Utils.Adr.MResource;
import com.accountingmanager.Sys.Widgets.SlideView.SlideListView;
import com.accountingmanager.Utils.SelectorBankListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择银行列表
 * Created by Home-Pc on 2017/4/26.
 */

public class SelectorBankListFragment extends ContentBaseFragment implements View.OnClickListener {
    private SlideListView Selector_Bank_list_recyclerView;
    private List<AssetsElementModel> adapterList = new ArrayList<>();
    private SelectorBankListAdapter adapter;
    private Button Selector_Bank_list_Search;
    private AssetsElementModel assetsElementModel = new AssetsElementModel();

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_selectorbanklist_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        Selector_Bank_list_recyclerView = (SlideListView) fgView.findViewById(R.id.Selector_Bank_list_recyclerView);
        Selector_Bank_list_Search = (Button) fgView.findViewById(R.id.Selector_Bank_list_Search);
    }

    @Override
    protected void initEvent() {
        setTitleWhiteStyle(getString(R.string.Selector_Bank));
        Selector_Bank_list_Search.setOnClickListener(this);
        observeMessage(AppConfig.getInstance().FRAGMENT_SELECTOR_BANK_TAG);
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_8);
        Bundle bundle = getArguments();
        if (bundle.containsKey(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) && bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG) != null) {
            assetsElementModel = (AssetsElementModel) bundle.get(AppConfig.getInstance().FRAGMENT_OBJECT_TAG);
        }
        adapter = new SelectorBankListAdapter(getActivity());
        Selector_Bank_list_recyclerView.setAdapter(adapter);
        String[] titles = getResources().getStringArray(R.array.SelectorBankList);
        String[] icons = getResources().getStringArray(R.array.SelectorBankIcon);

        if (titles.length == icons.length) {
            for (int i = 0; i < titles.length; i++) {
                AssetsElementModel model = new AssetsElementModel();
                model.setMenuName(titles[i]);
                model.setGroupName(assetsElementModel.getGroupName());
                model.setGroupIcon(assetsElementModel.getGroupIcon());
                model.setMenuIcon(MResource.getIdByNameForDrawable(getActivity(), icons[i]));
                model.setType(assetsElementModel.getType());
                adapterList.add(model);
            }
        }
        adapter.setAdapterList(adapterList);

        Selector_Bank_list_recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                assetsElementModel = adapterList.get(position);
                bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG, AppConfig.getInstance().FRAGMENT_SELECTOR_BANK_TAG);
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, adapterList.get(position));
                ServiceBaseActivity.startActivity(getActivity(), AddAssetsSelectorTypeFragment.class.getName(), bundle);
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
        if (msgkey.equals(AppConfig.getInstance().FRAGMENT_SELECTOR_BANK_TAG)) {
            Bundle bundle = (Bundle) msgObject;
            bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, assetsElementModel);
            if (bundle.containsKey(AppConfig.getInstance().ADD_ASSETS_SELECTOR_TYPE) &&
                    !bundle.getBoolean(AppConfig.getInstance().ADD_ASSETS_SELECTOR_TYPE)) {//手动
                ServiceBaseActivity.startActivity(getActivity(), BankInputContentFragment.class.getName(), bundle);
            } else {
                //自动添加
            }
        } else if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_8)) {
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, msgObject);
            getActivity().finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Selector_Bank_list_Search:
                break;
        }
    }
}
