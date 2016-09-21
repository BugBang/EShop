package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.Constant;
import com.edianjucai.eshop.presenter.impl.RegisterPersenterlmpl1;
import com.edianjucai.eshop.presenter.usb.RegisterPresenter1;
import com.edianjucai.eshop.ui.view.RegisterView1;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-14.
 */
public class RegisterFragment1 extends BaseFragment implements RegisterView1{

    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.act_registe_edt_cellphone_number)
    EditText mActRegisteEdtCellphoneNumber;
    @BindView(R.id.ll_edit_clear)
    LinearLayout mLlEditClear;
    @BindView(R.id.bt_next)
    Button mBtNext;

    private String mUserPhone;
    private RegisterPresenter1 mRegisterPresenter1;

    public static RegisterFragment1 newInstance() {
        return new RegisterFragment1();
    }

    @Override
    public int bindLayout() {
        return R.layout.register_fragment1;
    }

    @Override
    public void doBusiness(Context mContext) {
        mRegisterPresenter1 = new RegisterPersenterlmpl1(this);
        mActRegisteEdtCellphoneNumber.addTextChangedListener(new TextWatcherImpl
                (mBtNext,mLlEditClear,R.mipmap.notlogin_07,R.mipmap.login_07));
    }

    @OnClick({R.id.iv_back, R.id.ll_edit_clear, R.id.bt_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                removeFragment();
                mActivity.finish();
                break;
            case R.id.ll_edit_clear:
                clearEditText();
                break;
            case R.id.bt_next:
                if (checkData()){
                    mRegisterPresenter1.requestPhoneCode(mUserPhone);
                }
                break;
        }
    }

    private boolean checkData() {
        mUserPhone = mActRegisteEdtCellphoneNumber.getText().toString().trim();
        if (mUserPhone.length()!=11){
            ToastUtils.show(mActivity,"请输入正确的手机号码");
            return false;
        }
        return true;
    }

    private void clearEditText() {
        mActRegisteEdtCellphoneNumber.setText("");
    }

    @Override
    public void startRequestCode() {
        showLoadingDialog("正在验证");
    }

    @Override
    public void finishRequestCode() {
        hideLoadingDialog();
    }

    @Override
    public void requsetCodeSuccess() {
        hideLoadingDialog();
        addFragment(RegisterFragment2.newInstance(), Constant.User.USER_PHONE, mUserPhone);
    }

    @Override
    public void requestCodeFail() {
        hideLoadingDialog();
    }
}
