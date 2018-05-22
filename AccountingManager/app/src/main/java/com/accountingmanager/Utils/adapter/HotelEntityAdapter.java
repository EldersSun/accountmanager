package com.accountingmanager.Utils.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Model.AssetsTypeElementModel;
import com.accountingmanager.Sys.Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import com.accountingmanager.R;

public class HotelEntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> {


    private List<AssetsTypeElementModel> adapterList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    private SparseBooleanArray mBooleanMap;

    private int itemCount = 0;

    public HotelEntityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<AssetsTypeElementModel> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        if (adapterList.get(section) == null) {
            return 0;
        }
        int count = adapterList.get(section).getAssetsElementModelList().size();
        if (count >= itemCount && !mBooleanMap.get(section)) {
            count = itemCount;
        }
        return count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.hotel_title_item, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.hotel_desc_item, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        holder.type_title_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isOpen = mBooleanMap.get(section);
                String text = isOpen ? mContext.getString(R.string.open) : mContext.getString(R.string.close);
                mBooleanMap.put(section, !isOpen);
                holder.type_title_open.setText(text);
                notifyDataSetChanged();
            }
        });

        AssetsTypeElementModel model = adapterList.get(section);

        holder.type_title_tvShow.setText(model.getGroupName());
        holder.type_title_open.setText(mBooleanMap.get(section) ? mContext.getString(R.string.close) : mContext.getString(R.string.open));
        holder.type_title_icon.setImageResource(model.getMenuIcon());

        if (mBooleanMap.get(section)) {
            if (StringUtils.isBlank(model.getType())) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_angle_color_1);
                return;
            }
            if (model.getType().equals("1")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_angle_color_1);
            } else if (model.getType().equals("2") || model.getType().equals("3")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_angle_color_2);
            } else if (model.getType().equals("4")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_angle_color_3);
            } else if (model.getType().equals("5")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_angle_color_4);
            }
        } else {
            if (StringUtils.isBlank(model.getType())) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_color_1);
                return;
            }
            if (model.getType().equals("1")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_color_1);
            } else if (model.getType().equals("2") || model.getType().equals("3")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_color_2);
            } else if (model.getType().equals("4")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_color_3);
            } else if (model.getType().equals("5")) {
                holder.type_title_layout.setBackgroundResource(R.drawable.type_classificarion_item_color_4);
            }
        }
    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(DescHolder holder, int section, int position) {

        AssetsElementModel model = adapterList.get(section).getAssetsElementModelList().get(position);

        holder.type_menuNmae.setText(model.getMenuName());
        holder.type_menuNumber.setText(model.getAmount());

        if (StringUtils.isBlank(model.getType())) {
            return;
        }

        if (model.getType().equals("1")) {
            holder.type_menuNmae.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_1));
            holder.type_menuNumber.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_1));
        } else if (model.getType().equals("2") || model.getType().equals("3")) {
            holder.type_menuNmae.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_2));
            holder.type_menuNumber.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_2));
        } else if (model.getType().equals("4")) {
            holder.type_menuNmae.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_3));
            holder.type_menuNumber.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_3));
        } else if (model.getType().equals("5")) {
            holder.type_menuNmae.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_4));
            holder.type_menuNumber.setTextColor(mContext.getResources().getColor(R.color.type_classic_color_4));
        }
    }
}
