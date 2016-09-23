package com.edianjucai.eshop.model.mode;

import com.edianjucai.eshop.presenter.usb.OnProjectListListener;

/**
 * Created by user on 2016-09-23.
 */
public interface ProjectListMode {
    void requestProjectListData(OnProjectListListener onProjectListListener);
}
