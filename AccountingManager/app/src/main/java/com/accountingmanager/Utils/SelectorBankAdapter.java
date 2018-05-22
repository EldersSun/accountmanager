package com.accountingmanager.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.accountingmanager.R;

import java.util.List;

/**
 * Created by Home_Pc on 2017/3/14.
 */

public class SelectorBankAdapter extends BaseAdapter{

    private Context context;
    private List<String> list;

    public SelectorBankAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHold viewHold;
        if(convertView == null){
            viewHold = new ViewHold();
            convertView = LayoutInflater.from(context).inflate(R.layout.v_selector_layout, null);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.selector_bank_dialog_item = (TextView) convertView.findViewById(R.id.selector_bank_dialog_item);
        viewHold.selector_bank_dialog_item.setText(list.get(position));
        return convertView;
    }

    class ViewHold{
        TextView selector_bank_dialog_item;
    }
}
