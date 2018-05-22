package com.accountingmanager.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.accountingmanager.R;
import com.accountingmanager.Sys.Communication.OnItemRecyclerViewClickListener;

/**
 * Created by Home-Pc on 2017/5/15.
 */

public class NationalHomeSelectorAdapter extends RecyclerView.Adapter<NationalHomeSelectorAdapter.ViewHold> {

    private Context context;

    public NationalHomeSelectorAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHold viewHold = new ViewHold(LayoutInflater.from(context).inflate(R.layout.v_national_home_selector_item_layout, null));
        return viewHold;
    }

    @Override
    public void onBindViewHolder(final ViewHold holder, final int position) {
        holder.national_home_selector_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemRecyclerViewClickListener != null) {
                    onItemRecyclerViewClickListener.onItemClickListener(holder.national_home_selector_item_layout, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHold extends RecyclerView.ViewHolder {
        private RelativeLayout national_home_selector_item_layout;
        private TextView national_home_selector_item_name;
        private TextView national_home_selector_item_time;
        private TextView national_home_selector_item_rate;

        public ViewHold(View itemView) {
            super(itemView);
            national_home_selector_item_layout = (RelativeLayout) itemView.findViewById(R.id.national_home_selector_item_layout);
            national_home_selector_item_name = (TextView) itemView.findViewById(R.id.national_home_selector_item_name);
            national_home_selector_item_time = (TextView) itemView.findViewById(R.id.national_home_selector_item_time);
            national_home_selector_item_rate = (TextView) itemView.findViewById(R.id.national_home_selector_item_rate);
        }
    }

    public void setOnItemRecyclerViewClickListener(OnItemRecyclerViewClickListener onItemRecyclerViewClickListener) {
        this.onItemRecyclerViewClickListener = onItemRecyclerViewClickListener;
    }

    private OnItemRecyclerViewClickListener onItemRecyclerViewClickListener;

}
