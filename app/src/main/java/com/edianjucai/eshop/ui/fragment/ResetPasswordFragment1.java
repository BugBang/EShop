package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.Constant;
import com.edianjucai.eshop.presenter.impl.ResetPasswordPresenterlmpl1;
import com.edianjucai.eshop.presenter.usb.ResetPasswordPresenter1;
import com.edianjucai.eshop.ui.view.ResetPasswordView1;
import com.edianjucai.eshop.util.CountDownTimerUtils;
import com.edianjucai.eshop.util.DataUtil;
import com.edianjucai.eshop.util.SharedPreferencesUtils;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-14.
 */
public class ResetPasswordFragment1 extends BaseFragment implements ResetPasswordView1{
    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.act_registe_edt_cellphone_number)
    EditText mActRegisteEdtCellphoneNumber;
    @BindView(R.id.ll_edit_clear)
    LinearLayout mLlEditClear;
    @BindView(R.id.et_sms_code)
    EditText mEtSmsCode;
    @BindView(R.id.bt_send_sms_code)
    TextView mBtSendSmsCode;
    @BindView(R.id.bt_next)
    Button mBtNext;

    private String mUserName;
    private String mSmsCode;
    private ResetPasswordPresenter1 mResetPasswordPresenter1;

    public static ResetPasswordFragment1 newInstance() {
        return new ResetPasswordFragment1();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_reset_password1;
    }

    @Override
    public void doBusiness(Context mContext) {
        mResetPasswordPresenter1 = new ResetPasswordPresenterlmpl1(this);
        initUiState();
        initSendSmsState();
    }

    private void initUiState() {
        mActRegisteEdtCellphoneNumber.addTextChangedListener(new TextWatcherImpl(mBtNext, mLlEditClear, R.mipmap.notlogin_07, R.mipmap.login_07));
    }

    private void initSendSmsState() {
        long startTime = System.currentTimeMillis();
        long smsCodeTime = (long) SharedPreferencesUtils.getParam(mActivity, Constant.SharedPreferencesKeyName.SMS_COUNT_DOWN, -1L);
        if ((startTime - smsCodeTime) < Constant.Sms.SMS_LIMIT_TIME) {
            CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mBtSendSmsCode,
                    Constant.Sms.SMS_LIMIT_TIME - (startTime - smsCodeTime), 1000);
            mCountDownTimerUtils.start();
        }
    }


    @OnClick({R.id.iv_back, R.id.ll_edit_clear, R.id.bt_send_sms_code, R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                doBack();
                break;
            case R.id.ll_edit_clear:
                clearEditText();
                break;
            case R.id.bt_send_sms_code:
                mUserName = mActRegisteEdtCellphoneNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(mUserName)){
                    mResetPasswordPresenter1.requestResetPasswordCode(mUserName);
                }else {
                    ToastUtils.show(mActivity,"请输入手机号");
                }
                break;
            case R.id.bt_next:
                if (checkData()) {
                    Bundle _bundle = new Bundle();
                    _bundle.putString(Constant.User.USER_PHONE,mUserName);
                    _bundle.putString(Constant.User.RESET_PASSWORD_CODE,mSmsCode);
                    addFragment(ResetPasswordFragment2.newInstance(),_bundle);
                }
                break;
        }
    }

    private boolean checkData() {
        mUserName = mActRegisteEdtCellphoneNumber.getText().toString().trim();
        mSmsCode = mEtSmsCode.getText().toString().trim();
        switch (DataUtil.checkData(mSmsCode)) {
            case 0:
                ToastUtils.show(mActivity, "验证码不可为空");
                return false;
        }
        return true;
    }

    private void doBack() {
        removeFragment();
        mActivity.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void setSendSmsState() {
        long endTime = System.currentTimeMillis();
        SharedPreferencesUtils.setParam(mActivity, Constant.SharedPreferencesKeyName.SMS_COUNT_DOWN, endTime);
        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(mBtSendSmsCode, Constant.Sms.SMS_LIMIT_TIME, 1000);
        mCountDownTimerUtils.start();
    }

    private void clearEditText() {
        mActRegisteEdtCellphoneNumber.setText("");
    }

    @Override
    public void startResetPassword() {
        showLoadingDialog("正在请求验证码");
    }

    @Override
    public void finishResetPassword() {
        hideLoadingDialog();
    }

    @Override
    public void successResetPassword() {
        hideLoadingDialog();
        setSendSmsState();
    }

    @Override
    public void failResetPassword(String showErr) {
        ToastUtils.show(mActivity,showErr);
    }
}
