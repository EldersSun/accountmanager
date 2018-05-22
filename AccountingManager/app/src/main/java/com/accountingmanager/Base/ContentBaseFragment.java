package com.accountingmanager.Base;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Utils.StringUtils;

/**
 * 拥有title栏的基础fragment类
 * Created by Home_Pc on 2017/3/9.
 */

public abstract class ContentBaseFragment extends BaseFragment {

    protected LinearLayout fm_base_title;
    protected FrameLayout fm_base_content;
    protected RelativeLayout title_layout;

    protected ImageView title_back;
    protected TextView title_tvShow;
    protected TextView title_menuNum;
    protected Button title_menu;
    protected View contentView;


    @Override
    protected View initWidgetsViews(LayoutInflater inflater) {
        fgView = inflater.inflate(R.layout.fm_base_layout, null);
        initWidgets(inflater, fgView);
        return fgView;
    }

    /**
     * 设置title为透明样式
     */
    protected void setTitleTransparencyStyle(String title) {
        title_layout.setBackgroundResource(R.color.transparentColor);
        title_tvShow.setTextColor(getResources().getColor(R.color.whiteColor));
        if (!StringUtils.isBlank(title)) {
            title_tvShow.setText(title);
        } else {
            title_tvShow.setText("");
        }
    }

    /**
     * 设置title为白色样式
     */
    protected void setTitleWhiteStyle(String title) {
        title_layout.setBackgroundResource(R.color.whiteColor);
        title_tvShow.setTextColor(getResources().getColor(R.color.titleColor));
        if (!StringUtils.isBlank(title)) {
            title_tvShow.setText(title);
        } else {
            title_tvShow.setText("");
        }
    }

    protected abstract View initViews(LayoutInflater inflater);

    protected abstract void initWidgets(View fgView);

    protected abstract void initEvent();

    private void initWidgets(LayoutInflater inflater, View fgView) {
        fm_base_title = (LinearLayout) fgView.findViewById(R.id.fm_base_title);
        fm_base_content = (FrameLayout) fgView.findViewById(R.id.fm_base_content);
        title_layout = (RelativeLayout) inflater.inflate(R.layout.v_title_layout, null);
        title_back = (ImageView) title_layout.findViewById(R.id.title_back);
        title_tvShow = (TextView) title_layout.findViewById(R.id.title_tvShow);
        title_menuNum = (TextView) title_layout.findViewById(R.id.title_menuNum);
        title_menu = (Button) title_layout.findViewById(R.id.title_menu);
        contentView = initViews(inflater);
        fm_base_title.addView(title_layout);
        contentView = initViews(inflater);

        if (contentView == null) {
            Log.i("initFindViews", "initFindViews为空");
            return;
        }
        fm_base_content.addView(contentView);

        setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title_back.getVisibility() == View.VISIBLE) {
                    getActivity().finish();
                }
            }
        });
    }

    protected void setOnBackListener(View.OnClickListener onClickListener) {
        title_back.setOnClickListener(onClickListener);
    }

    protected void setOnTitleMenuListener(String menuName,View.OnClickListener onClickListener){
        title_menu.setVisibility(View.VISIBLE);
        title_menu.setText(menuName);
        title_menu.setOnClickListener(onClickListener);
    }

}
