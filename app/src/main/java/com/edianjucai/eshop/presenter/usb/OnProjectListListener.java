package com.edianjucai.eshop.presenter.usb;

import com.edianjucai.eshop.model.entity.ProjectListModel;

/**
 * Created by user on 2016-09-23.
 */
public interface OnProjectListListener {
    void setProjectListData(ProjectListModel projectListModel);
    void startRequest();
    void finishRequest();
    void successRequest();
    void failRequest(String showErr);
}
