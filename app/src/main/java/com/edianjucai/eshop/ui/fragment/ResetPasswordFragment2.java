package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.Constant;
import com.edianjucai.eshop.presenter.impl.ResetPasswordPresenterlmpl2;
import com.edianjucai.eshop.presenter.usb.ResetPasswordPresenter2;
import com.edianjucai.eshop.ui.view.ResetPasswordView2;
import com.edianjucai.eshop.util.DialogUtil;
import com.edianjucai.eshop.util.IntentUtil;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-14.
 */
public class ResetPasswordFragment2 extends BaseFragment implements ResetPasswordView2{

    @BindView(R.id.iv_back)
    LinearLayout mIvBack;
    @BindView(R.id.act_login_et_password)
    EditText mActLoginEtPassword;
    @BindView(R.id.bt_next)
    Button mBtNext;
    @BindView(R.id.tv_call_center)
    TextView mTvCallCenter;
    @BindView(R.id.ll_edit_clear)
    LinearLayout mLlEditClear;

    private String mUserPhone;
    private String mResetPassCode;
    private String mResetPassWord;

    private DialogUtil mDialogUtil;


    private ResetPasswordPresenter2 mResetPasswordPresenter2;

    public static ResetPasswordFragment2 newInstance() {
        return new ResetPasswordFragment2();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_reset_password2;
    }

    @Override
    public void doBusiness(Context mContext) {
        mDialogUtil = new DialogUtil(mActivity);
        mResetPasswordPresenter2 = new ResetPasswordPresenterlmpl2(this);
        initData();
        initUI();
    }

    private void initData() {
        mUserPhone = getArguments().getString(Constant.User.USER_PHONE);
        mResetPassCode = getArguments().getString(Constant.User.RESET_PASSWORD_CODE);
    }

    private void initUI() {
        mTvCallCenter.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mTvCallCenter.getPaint().setAntiAlias(true);//抗锯齿
        mActLoginEtPassword.addTextChangedListener(new TextWatcherImpl(
                mBtNext, mLlEditClear, R.mipmap.notlogin_07, R.mipmap.login_07));
    }

    @OnClick({R.id.iv_back, R.id.bt_next, R.id.tv_call_center, R.id.ll_edit_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                removeFragment();
                break;
            case R.id.bt_next:
                if (checkData()){
                    mResetPasswordPresenter2.requestResetPassword(mUserPhone,mResetPassCode,mResetPassWord);
                }
                break;
            case R.id.tv_call_center:
                clickServicePhone();
                break;
            case R.id.ll_edit_clear:
                clearEditText();
                break;
        }
    }

    private void clickServicePhone() {
        // TODO: 2016-09-27 从数据库取出客服电话
        mDialogUtil.confirm("提示", "确定拨打客服电话?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if ("075586966868" != null) {
                    startActivity(IntentUtil.getCallNumberIntent("075586966868"));
                } else {
                    ToastUtils.showToast("未找到客服电话");
                }
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private boolean checkData() {
        mResetPassWord = mActLoginEtPassword.getText().toString().trim();
        if (mResetPassWord.length()>20||mResetPassWord.length()<6){
            ToastUtils.show(mActivity,"密码的长度不能大于20位或小于6位");
            return false;
        }
        return true;
    }

    private void clearEditText() {
        mActLoginEtPassword.setText("");
    }

    @Override
    public void startResetPassword() {
        showLoadingDialog("请稍等");
    }

    @Override
    public void finishResetPassword() {
        hideLoadingDialog();
    }

    @Override
    public void successResetPassword() {
        hideLoadingDialog();
        mActivity.finish();
    }

    @Override
    public void failResetPassword(String showErr) {
        hideLoadingDialog();
        ToastUtils.show(mActivity,showErr);
    }
}
