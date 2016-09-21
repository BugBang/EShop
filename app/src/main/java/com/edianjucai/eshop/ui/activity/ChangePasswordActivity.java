package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.edianjucai.eshop.CustomView.ClearEditText;
import com.edianjucai.eshop.CustomView.PasswordEditText;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.presenter.impl.ChangePasswordPresenterlmpl;
import com.edianjucai.eshop.presenter.usb.ChangePasswordPresenter;
import com.edianjucai.eshop.ui.view.ChangePasswordView;
import com.edianjucai.eshop.util.DataUtil;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by user on 2016-09-21.
 */
public class ChangePasswordActivity extends BaseActivity implements ChangePasswordView{

    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.et_old_password)
    ClearEditText mEtOldPassword;
    @BindView(R.id.et_new_password1)
    PasswordEditText mEtNewPassword1;
    @BindView(R.id.et_new_password2)
    PasswordEditText mEtNewPassword2;
    @BindView(R.id.btn_done)
    Button mBtnDone;

    private String mOldPassword;
    private String mNewPassword1;
    private String mNewPassword2;
    private ChangePasswordPresenter mChangePasswordPresenter;
    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    public void doBusiness(Context mContext) {
        mChangePasswordPresenter = new ChangePasswordPresenterlmpl(this);
    }

    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

    @OnClick({R.id.iv_back, R.id.btn_done})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_done:
                if (checkData()){
                    mChangePasswordPresenter.requestChangePassword(App.getApplication().getmLocalUser().getUserName(),mOldPassword,mNewPassword1,mNewPassword2);
                }
                break;
        }
    }

    private boolean checkData() {
        mOldPassword = mEtOldPassword.getText().toString().trim();
        mNewPassword1 = mEtNewPassword1.getText().toString().trim();
        mNewPassword2 = mEtNewPassword2.getText().toString().trim();

        switch(DataUtil.checkData(mOldPassword,mNewPassword1,mNewPassword2)){
            case 0:
                ToastUtils.show(this,"旧密码不可为空");
                return false;
            case 1:
                ToastUtils.show(this,"请输入新密码");
                return false;
            case 2:
                ToastUtils.show(this,"请确认新密码");
                return false;
        }
        if (!mNewPassword1.equals(mNewPassword2)){
            ToastUtils.show(this,"两次密码输入不一致");
            return false;
        }
        return true;
    }


    @Override
    public void startChangePassword() {
        showLoadingDialog("更改密码中");
    }

    @Override
    public void finishChangePassword() {
        hideLoadingDialog();
    }

    @Override
    public void successChangePassword() {
        hideLoadingDialog();
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_CHANGE_PASSWORD_SUCCESS));
        finish();
    }

    @Override
    public void failChangePassword(String showErr) {
        hideLoadingDialog();
        ToastUtils.show(this,showErr);
    }
}
