package com.accountingmanager.Fragment.Accounting.ForeignExchange;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Model.ForeignContentModel;
import com.accountingmanager.Sys.Utils.Adr.MResource;
import com.accountingmanager.Sys.Utils.ImgUtils;
import com.accountingmanager.Utils.ForeignHomeSelectorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 外汇首页选择页面
 * Created by Home-Pc on 2017/5/16.
 */

public class ForeignExchangeHomeSelectorFragment extends ContentBaseFragment {

    private RecyclerView v_RecyclerViewShow;
    private List<ForeignContentModel> adapterList = new ArrayList<>();
    private ForeignHomeSelectorAdapter adapter;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.v_recyclerview_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        v_RecyclerViewShow = (RecyclerView) fgView.findViewById(R.id.v_RecyclerViewShow);
    }

    @Override
    protected void initEvent() {
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_14);
        v_RecyclerViewShow.setLayoutManager(new LinearLayoutManager(getActivity()));

        String[] names = getResources().getStringArray(R.array.foreignHomeSelectorMenu);
        String[] icons = getResources().getStringArray(R.array.foreignHomeSelectorMenuIcon);
        String[] companys = getResources().getStringArray(R.array.foreignHomeSelectorMenuCompany);

        if (names.length == icons.length && names.length == companys.length) {
            for (int i = 0; i < names.length; i++) {
                ForeignContentModel model = new ForeignContentModel();
                model.setImgResources(MResource.getIdByNameForDrawable(getActivity(),icons[i]));
                model.setName(names[i]);
                model.setCompany(companys[i]);
                adapterList.add(model);
            }
        }

        adapter = new ForeignHomeSelectorAdapter(getActivity());
        v_RecyclerViewShow.setAdapter(adapter);
        adapter.setApadpterData(adapterList);

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                assetsElementModel.setMenuName(adapterList.get(position).getName());
                assetsElementModel.setMenuIcon(adapterList.get(position).getImgResources());
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG,assetsElementModel);
                bundle.putString(AppConfig.getInstance().FOREIGN_SELECTOR_CONTENT_TAG, adapterList.get(position).getCompany());
                ServiceBaseActivity.startActivity(getActivity(),ForeignExchangeContentInputFragment.class.getName(),bundle);

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
        if(msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_14)){
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, msgObject);
            getActivity().finish();
        }
    }
}
