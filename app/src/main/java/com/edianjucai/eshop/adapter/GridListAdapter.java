package com.edianjucai.eshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edianjucai.eshop.R;

/**
 * Created by user on 2016-09-23.
 */
public class GridListAdapter extends BaseAdapter {
    private Context mContext;
    public GridListAdapter(Context context){
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.item_grid_project_list, null);

//            viewHolder.tvTitle = (TextView) convertView
//                    .findViewById(R.id.tv_title);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //            // 设置数据


        return convertView;
    }

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvRate;
        TextView tvMoney;
        TextView tvMoneyType;
        TextView tvTime;
        ProgressBar pbProBar;
        TextView tvProgress;

    }
}

