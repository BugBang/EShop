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
    public int getCount() {
        if (mListModel != null) {
            return mListModel.size();
        } else {
            return 0;
        }
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, InitModel.CateListModel model) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_grid_project_list, null);
        }
        ImageView ivIcon = ViewHolder.get(convertView, R.id.iv_icon);
        TextView tvTitle = ViewHolder.get(convertView, R.id.tv_title);
        if (model != null) {
            Glide.with(mActivity).load("http://" + ApkConstant.SERVER_API_URL_MID +
                    "/wap/tpl/fanwe/images/commodity_type/type"+ model.getId()+".png").into(ivIcon);
            tvTitle.setText(model.getName());
        }
        if (position == mListModel.size()-1){
            // TODO: 2016-10-18 修改高度 
            TextView tvSubTitle = ViewHolder.get(convertView, R.id.tv_sub_title);
            Glide.with(mActivity).load("http://" + ApkConstant.SERVER_API_URL_MID +
                    "/wap/tpl/fanwe/images/commodity_type/type0.png").into(ivIcon);
            tvTitle.setText("更多项目");
            tvSubTitle.setText("敬请期待");
        }
        return convertView;
    }
}

