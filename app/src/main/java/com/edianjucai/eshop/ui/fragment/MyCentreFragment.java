package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.presenter.impl.MyCenterPersenterImpl;
import com.edianjucai.eshop.presenter.usb.LoginPresenter;
import com.edianjucai.eshop.ui.activity.ChangePasswordActivity;
import com.edianjucai.eshop.ui.activity.RegisterActivity;
import com.edianjucai.eshop.ui.activity.ResetPasswordActivity;
import com.edianjucai.eshop.ui.view.MyCenterView;
import com.edianjucai.eshop.util.DataUtil;
import com.edianjucai.eshop.util.DialogUtil;
import com.edianjucai.eshop.util.ToastUtils;
import com.edianjucai.eshop.util.UiUtils;
import com.edianjucai.eshop.util.ViewUtil;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import butterknife.BindView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by user on 2016-09-12.
 */
public class MyCentreFragment extends BaseFragment implements MyCenterView {

    @BindView(R.id.act_login_et_username)
    EditText mActLoginEtUsername;
    @BindView(R.id.ll_edit_clear)
    LinearLayout mLlEditClear;
    @BindView(R.id.act_login_et_password)
    EditText mActLoginEtPassword;
    @BindView(R.id.iv_eyes)
    LinearLayout mIvEyes;
    @BindView(R.id.act_login_tv_forget)
    TextView mActLoginTvForget;
    @BindView(R.id.act_login_tv_register)
    TextView mActLoginTvRegister;
    @BindView(R.id.act_login_btn_login)
    Button mActLoginBtnLogin;
    @BindView(R.id.login_space)
    LinearLayout mLoginSpace;
    @BindView(R.id.item_order_handle)
    LinearLayout mItemOrderHandle;
    @BindView(R.id.item_logout)
    LinearLayout mItemLogout;
    @BindView(R.id.handle_space)
    LinearLayout mHandleSpace;
    @BindView(R.id.item_modify_password)
    LinearLayout mItemModifyPassword;

    private LoginPresenter mLoginPresenter;
    private String mUserName;
    private String mPassWord;
    private LocalUser mLocalUser;
    private DialogUtil mDialogUtil;
    private int mLoginSpaceHeight;

    @Override
    public int bindLayout() {
        return R.layout.fragment_my_centre;
    }

    @Override
    public void doBusiness(Context mContext) {
        initUI();
    }

    private void initUI() {
        mDialogUtil = new DialogUtil(mActivity);
        mLocalUser = App.getApplication().getmLocalUser();
        if (mLocalUser != null) {
            setLoginSpaceGone();
        } else {
            ValueAnimator colorAnim = ObjectAnimator.ofFloat(mHandleSpace, "alpha", 0.0f);
            colorAnim.setDuration(0);
            colorAnim.start();
        }
        mLoginPresenter = new MyCenterPersenterImpl(this);
        mActLoginEtUsername.addTextChangedListener(new TextWatcherImpl(mActLoginBtnLogin, mLlEditClear, -1, -1));//-1 表示不改变按钮状态
        mActLoginEtPassword.addTextChangedListener(new TextWatcherImpl(mActLoginBtnLogin, mIvEyes, -1, -1));//-1 表示不改变按钮状态
    }

    private void setLoginSpaceGone() {
        mLoginSpace.setVisibility(View.GONE);
        mLoginSpaceHeight= ViewUtil.getTargetHeight(mLoginSpace);
    }

    @OnClick({R.id.ll_edit_clear, R.id.iv_eyes, R.id.act_login_tv_forget, R.id.act_login_tv_register, R.id.act_login_btn_login, R.id.item_order_handle, R.id.item_logout, R.id.item_modify_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_edit_clear:
                clearEditText();
                break;
            case R.id.iv_eyes:
                setPasswordState();
                break;
            case R.id.act_login_tv_forget:
                startRestePasswordActivity();
                break;
            case R.id.act_login_tv_register:
                startRegisterActivity();
                break;
            case R.id.act_login_btn_login:
                if (checkLoginData()) {
                    mLoginPresenter.getLoginMsg(mUserName, mPassWord);
                }
                break;
            case R.id.item_order_handle:
                $Log("item_order_handle");
                break;
            case R.id.item_modify_password:
                UiUtils.showNormal(mActivity, ChangePasswordActivity.class,false);
                break;
            case R.id.item_logout:
                clickLogout();
                break;
        }
    }

    private void clickLogout() {
        if (App.getApplication().getmLocalUser() != null) {
            mDialogUtil.confirm("提示", "确定要退出账户?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    doLogout();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
    }

    private void doLogout() {
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_LOGOUT_SUCCESS));
        mLoginSpace.setVisibility(View.VISIBLE);
        startAmin(0, mLoginSpaceHeight, 0.0f, 1.0f);
        ToastUtils.showToast("成功退出帐号!");
    }


    private boolean checkLoginData() {
        mUserName = mActLoginEtUsername.getText().toString().trim();
        mPassWord = mActLoginEtPassword.getText().toString().trim();

        switch(DataUtil.checkData(mUserName,mPassWord)){
            case 0:
                ToastUtils.show(mActivity, "用户名不可为空");
                return false;
            case 1:
                ToastUtils.show(mActivity, "登录密码不可为空");
                return false;
        }
        return true;
    }

    private void startRestePasswordActivity() {
        UiUtils.showNormal(getActivity(), ResetPasswordActivity.class, false);
    }

    private void startRegisterActivity() {
        UiUtils.showNormal(getActivity(), RegisterActivity.class, false);
    }

    /**
     * 控制登录界面与控制界面的显示与隐藏
     */
    private void startAmin(int sHeight, int eHeight, float v, float v1) {
        android.animation.ValueAnimator animator = android.animation.ValueAnimator.ofInt(sHeight, eHeight).setDuration(800);
        animator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
                int animatedFraction = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLoginSpace.getLayoutParams();
                layoutParams.height = animatedFraction;
                mLoginSpace.setLayoutParams(layoutParams);
                mLoginSpace.invalidate();

            }
        });
        animator.start();

        ValueAnimator colorAnim1 = ObjectAnimator.ofFloat(mLoginSpace, "alpha", v, v1);
        colorAnim1.setDuration(800);
        colorAnim1.start();

        ValueAnimator colorAnim2 = ObjectAnimator.ofFloat(mHandleSpace, "alpha", v1, v);
        colorAnim2.setDuration(800);
        colorAnim2.start();

    }


    private void clearEditText() {
        mActLoginEtUsername.setText("");
    }

    private boolean isPass = true;

    private void setPasswordState() {
        if (isPass) {
            mActLoginEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            mActLoginEtPassword.setSelection(mActLoginEtPassword.length());
            isPass = !isPass;
        } else {
            mActLoginEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            mActLoginEtPassword.setSelection(mActLoginEtPassword.length());
            isPass = !isPass;
        }

    }

    @Override
    public void showLoading() {
        showLoadingDialog("登录中");
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void onError() {

    }

    @Override
    public void loginSuccess() {
        mLoginSpaceHeight = mLoginSpace.getHeight();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActLoginEtUsername.setText("");
                mActLoginEtPassword.setText("");
                mActLoginEtUsername.requestFocus();
            }
        }, 800);
        startAmin(mLoginSpaceHeight, 0, 1.0f, 0.0f);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEventMainThread(EventMsg messageEvent) {
        switch(messageEvent.getImsg()){
            case EventTag.EVENT_REGISTER_AND_LOGIN_SUCCESS:
                doRegisterSuccess();
                break;
            case EventTag.EVENT_CHANGE_PASSWORD_SUCCESS:
                doChangePasswordSuccess();
                break;
        }
    }

    private void doRegisterSuccess() {
        setLoginSpaceGone();
        ValueAnimator colorAnim = ObjectAnimator.ofFloat(mHandleSpace, "alpha", 1.0f);
        colorAnim.setDuration(0);
        colorAnim.start();
    }

    private void doChangePasswordSuccess() {
        setLoginSpaceVisible();
    }

    private void setLoginSpaceVisible() {
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_LOGOUT_SUCCESS));
        mLoginSpace.setVisibility(View.VISIBLE);
        mLoginSpaceHeight= ViewUtil.getTargetHeight(mLoginSpace);
        ViewGroup.LayoutParams layoutParams = mLoginSpace.getLayoutParams();
        layoutParams.height = mLoginSpaceHeight;
        mLoginSpace.setLayoutParams(layoutParams);
        ValueAnimator colorAnim = ObjectAnimator.ofFloat(mHandleSpace, "alpha", 0.0f);
        colorAnim.setDuration(0);
        colorAnim.start();
    }
}