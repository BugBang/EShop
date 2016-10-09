package com.edianjucai.eshop.ui.view;

import com.edianjucai.eshop.model.entity.InitModel;

/**
 * Created by user on 2016-09-23.
 */
public interface ProjectListView {
    void setProjectListData(InitModel projectListModel);
    void startRequest();
    void finishRequest();
    void successRequest();
    void failRequest();
}
