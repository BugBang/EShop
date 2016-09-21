package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.Constant;
import com.edianjucai.eshop.presenter.impl.RegisterPersenterlmpl2;
import com.edianjucai.eshop.presenter.usb.RegisterPresenter2;
import com.edianjucai.eshop.ui.view.RegisterView2;
import com.edianjucai.eshop.util.DataUtil;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-14.
 */
public class RegisterFragment2 extends BaseFragment implements RegisterView2{
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.et_sms_code)
    EditText mEtSmsCode;
    @BindView(R.id.act_login_et_password)
    EditText mActLoginEtPassword;
    @BindView(R.id.iv_eyes)
    LinearLayout mIvEyes;
    @BindView(R.id.et_invite_people)
    EditText mEtInvitePeople;
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.tv_deal)
    TextView mTvDeal;

    private String mUserPhone;
    private String mSmsCode;
    private String mUserPassWord;
    private String mGroom;

    private RegisterPresenter2 mRegisterPresenter2;

    public static RegisterFragment2 newInstance() {
        return new RegisterFragment2();
    }

    @Override
    public int bindLayout() {
        return R.layout.register_fragment2;
    }

    @Override
    public void doBusiness(Context mContext) {
        init();
    }

    private void init() {
        mRegisterPresenter2 = new RegisterPersenterlmpl2(this);
        mActLoginEtPassword.addTextChangedListener(new TextWatcherImpl(mBtNext,mIvEyes,-1,-1));
        mUserPhone = getArguments().getString(Constant.User.USER_PHONE);
    }


    @OnClick({R.id.iv_back, R.id.iv_eyes, R.id.bt_next, R.id.tv_deal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                removeFragment();
                break;
            case R.id.iv_eyes:
                setPasswordState();
                break;
            case R.id.bt_next:
                if (checkData()){
                    mRegisterPresenter2.requestRegister(mUserPhone,mUserPassWord,mGroom,mSmsCode);
                }
                break;
            case R.id.tv_deal:

                break;
        }
    }


    private boolean checkData() {
        mSmsCode = mEtSmsCode.getText().toString().trim();
        mUserPassWord = mActLoginEtPassword.getText().toString().trim();
        mGroom = mEtInvitePeople.getText().toString().trim();
        switch(DataUtil.checkData(mSmsCode,mUserPassWord)){
            case 0:
                ToastUtils.show(mActivity, "短信验证码不可为空");
                return false;
            case 1:
                ToastUtils.show(mActivity, "请设置登录密码");
                return false;
        }
        return true;
    }

    private boolean isPass = true;
    private void setPasswordState() {
        if (isPass){
            mActLoginEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mActLoginEtPassword.setSelection(mActLoginEtPassword.length());
            isPass = !isPass;
        }else {
            mActLoginEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mActLoginEtPassword.setSelection(mActLoginEtPassword.length());
            isPass = !isPass;
        }

    }

    @Override
    public void startRegister() {
        showLoadingDialog("注册中");
    }

    @Override
    public void finishRegister() {
        hideLoadingDialog();
    }

    @Override
    public void registerSuccess() {
        hideLoadingDialog();
        mActivity.finish();
    }

    @Override
    public void registerFail(String showError) {
        hideLoadingDialog();
        ToastUtils.show(mActivity,showError);
    }
}
