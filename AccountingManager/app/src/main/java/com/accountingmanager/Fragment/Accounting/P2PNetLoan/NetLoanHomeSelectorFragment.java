package com.accountingmanager.Fragment.Accounting.P2PNetLoan;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.Base.ContentBaseFragment;
import com.accountingmanager.Base.ServiceBaseActivity;
import com.accountingmanager.Fragment.Accounting.AddAssetsSelectorTypeFragment;
import com.accountingmanager.Fragment.Accounting.BankInputContentFragment;
import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Config.AppConfig;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Widgets.GroupRecyclerView.LatterView;
import com.accountingmanager.Utils.ContactAdapter;
import com.accountingmanager.Utils.DividerItemDecoration;

/**
 * P2P 网贷选择界面
 * Created by Home-Pc on 2017/5/4.
 */

public class NetLoanHomeSelectorFragment extends ContentBaseFragment {
    private RecyclerView NetLoanHomeSelectorContentShow;
    private LatterView NetLoanHomeSelectorTypeShow;
    private String[] contactNames;
    private LinearLayoutManager manager;
    private ContactAdapter adapter;

    private AssetsElementModel assetsElementModel = new AssetsElementModel();
    private Bundle bundle;

    private TextView NetLoanHomeSelectorMsgTvShow;

    @Override
    protected View initViews(LayoutInflater inflater) {
        return inflater.inflate(R.layout.fm_netloan_home_selector_layout, null);
    }

    @Override
    protected void initWidgets(View fgView) {
        NetLoanHomeSelectorContentShow = (RecyclerView) fgView.findViewById(R.id.NetLoanHomeSelectorContentShow);
        NetLoanHomeSelectorTypeShow = (LatterView) fgView.findViewById(R.id.NetLoanHomeSelectorTypeShow);

        NetLoanHomeSelectorMsgTvShow = (TextView) fgView.findViewById(R.id.NetLoanHomeSelectorMsgTvShow);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initEvent() {

        observeMessage(AppConfig.getInstance().SELECTOR_NETLOAN_HOME_STATE);
        observeMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_9);

        contactNames = new String[]{"张三丰", "郭靖", "黄蓉", "黄老邪", "赵敏", "123",
                "天山童姥", "任我行", "于万亭", "陈家洛", "韦小宝", "$6", "穆人清",
                "陈圆圆", "郭芙", "郭襄", "穆念慈", "东方不败", "梅超风", "林平之",
                "林远图", "灭绝师太", "段誉", "鸠摩智"};

        manager = new LinearLayoutManager(getActivity());

        adapter = new ContactAdapter(getActivity(), contactNames);

        NetLoanHomeSelectorContentShow.setLayoutManager(manager);
        NetLoanHomeSelectorContentShow.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        NetLoanHomeSelectorContentShow.setAdapter(adapter);

        NetLoanHomeSelectorTypeShow.setCharacterListener(new LatterView.CharacterClickListener() {
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

        NetLoanHomeSelectorContentShow.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                getScollYDistance();
            }
        });

        adapter.setOnItemRecyclerViewClickListener(new OnItemRecyclerViewClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                LinearLayout linearLayout = (LinearLayout) view;
                TextView tvShow = (TextView) linearLayout.getChildAt(1);
                assetsElementModel.setMenuName(tvShow.getText() == null ? "" : tvShow.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putString(AppConfig.getInstance().JUMP_SELECTOR_TYPE_PAGE_TAG, AppConfig.getInstance().SELECTOR_NETLOAN_HOME_STATE);
                bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, assetsElementModel);
                ServiceBaseActivity.startActivity(getActivity(), AddAssetsSelectorTypeFragment.class.getName(), bundle);
            }
        });

    }

    public void getScollYDistance() {
        //onScrollStateChanged 方法
        RecyclerView.LayoutManager layoutManager = NetLoanHomeSelectorContentShow.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        //只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            if (firstItemPosition > 0) {
                NetLoanHomeSelectorMsgTvShow.setVisibility(View.GONE);
            } else {
                NetLoanHomeSelectorMsgTvShow.setVisibility(View.VISIBLE);
            }
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
    public void onReceiveMessage(String msgkey, Object msgObject) {
        super.onReceiveMessage(msgkey, msgObject);
        if (msgkey.equals(AppConfig.getInstance().SELECTOR_NETLOAN_HOME_STATE)) {
            Bundle bundle = (Bundle) msgObject;
            bundle.putSerializable(AppConfig.getInstance().FRAGMENT_OBJECT_TAG, assetsElementModel);
            if (bundle.containsKey(AppConfig.getInstance().ADD_ASSETS_SELECTOR_TYPE) &&
                    !bundle.getBoolean(AppConfig.getInstance().ADD_ASSETS_SELECTOR_TYPE)) {//手动
                ServiceBaseActivity.startActivity(getActivity(), NetLoanInputHomeFragment.class.getName(), bundle);
            } else {
                //自动添加
            }
        } else if (msgkey.equals(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_9)) {
            sendMessage(AppConfig.getInstance().ADD_INPUT_GET_CONTENT_TAG_3, msgObject);
            getActivity().finish();
        }
    }
}
