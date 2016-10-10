package com.edianjucai.eshop.presenter.impl;

import com.edianjucai.eshop.model.impl.FeedBackModelImpl;
import com.edianjucai.eshop.model.mode.FeedBackMode;
import com.edianjucai.eshop.presenter.usb.FeedBackPresenter;
import com.edianjucai.eshop.presenter.usb.OnFeedBackListener;
import com.edianjucai.eshop.ui.view.FeedBackView;

/**
 * Created by user on 2016-10-10.
 */
public class FeedBackPersenterImpl implements FeedBackPresenter,OnFeedBackListener {

    private FeedBackView mFeedBackView;
    private FeedBackMode mFeedBackMode;

    public FeedBackPersenterImpl(FeedBackView feedBackView){
        this.mFeedBackView = feedBackView;
        mFeedBackMode = new FeedBackModelImpl();
    }

    @Override
    public void requestFeedBack(String content) {
        mFeedBackMode.requesetFeedBack(content,this);
    }

    @Override
    public void startFeedBack() {
        mFeedBackView.startFeedBack();
    }

    @Override
    public void finishFeedBack() {
        mFeedBackView.finishFeedBack();
    }

    @Override
    public void successFeedBack() {
        mFeedBackView.successFeedBack();
    }

    @Override
    public void failFeedBack(String err) {
        mFeedBackView.failFeedBack(err);
    }
}
