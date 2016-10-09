package com.edianjucai.eshop.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BXBaseAdapter;
import com.edianjucai.eshop.constant.ApkConstant;
import com.edianjucai.eshop.model.entity.InitModel;
import com.edianjucai.eshop.util.ViewHolder;

import java.util.List;

/**
 * Created by user on 2016-09-23.
 */
public class GridListAdapter extends BXBaseAdapter<InitModel.CateListModel> {

    public GridListAdapter(List<InitModel.CateListModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    public void setData(List<InitModel.CateListModel> listModel){
        this.mListModel =listModel;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, InitModel.CateListModel model) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_grid_project_list, null);
        }
        ImageView ivIcon = ViewHolder.get(convertView, R.id.iv_icon);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);

        if (model != null) {
            Glide.with(mActivity).load("http://" + ApkConstant.SERVER_API_URL_MID +model.getIco()).into(ivIcon);
            tvTitle.setText(model.getName());
        }
        return convertView;
    }
}

