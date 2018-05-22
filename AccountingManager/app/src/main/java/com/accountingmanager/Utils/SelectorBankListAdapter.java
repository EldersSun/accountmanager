package com.accountingmanager.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;
import com.accountingmanager.Sys.Model.AssetsElementModel;
import com.accountingmanager.Sys.Widgets.UiContentView.UiContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择银行卡适配器
 * Created by Home-Pc on 2017/4/26.
 */

public class SelectorBankListAdapter extends BaseAdapter {
    private Context context;
    private List<AssetsElementModel> adapterList = new ArrayList<>();

    public void setAdapterList(List<AssetsElementModel> adapterList) {
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    public SelectorBankListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return adapterList == null ? 0 : adapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return adapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHold holder;
        if (convertView == null) {
            holder = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.v_selector_bank_item_layout, null);
            holder.Selector_bank_list_icon = (ImageView) convertView.findViewById(R.id.Selector_bank_list_icon);
            holder.Selector_bank_list_name = (TextView) convertView.findViewById(R.id.Selector_bank_list_name);
            holder.Selector_bank_list_content = (TextView) convertView.findViewById(R.id.Selector_bank_list_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHold) convertView.getTag();
        }
        AssetsElementModel model = adapterList.get(position);
        holder.Selector_bank_list_icon.setImageResource(model.getMenuIcon());
        holder.Selector_bank_list_name.setText(model.getMenuName());
        holder.Selector_bank_list_content.setText(context.getString(R.string.selector_bank_list_msg_3));
        return convertView;
    }

    class ViewHold {
        ImageView Selector_bank_list_icon;
        TextView Selector_bank_list_name;
        TextView Selector_bank_list_content;
    }
}
