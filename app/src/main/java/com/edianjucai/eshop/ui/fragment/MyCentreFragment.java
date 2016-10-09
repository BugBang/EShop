package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edianjucai.eshop.FunctionImpl.TextWatcherImpl;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.ApkConstant;
import com.edianjucai.eshop.constant.Constant;
import com.edianjucai.eshop.event.EventMsg;
import com.edianjucai.eshop.event.EventTag;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.model.entity.LoginModel;
import com.edianjucai.eshop.presenter.impl.MyCenterPersenterImpl;
import com.edianjucai.eshop.presenter.usb.LoginPresenter;
import com.edianjucai.eshop.ui.activity.ChangePasswordActivity;
import com.edianjucai.eshop.ui.activity.RegisterActivity;
import com.edianjucai.eshop.ui.activity.ResetPasswordActivity;
import com.edianjucai.eshop.ui.activity.WebViewActivity;
import com.edianjucai.eshop.ui.view.MyCenterView;
import com.edianjucai.eshop.util.AnimUtil;
import com.edianjucai.eshop.util.DataUtil;
import com.edianjucai.eshop.util.DialogUtil;
import com.edianjucai.eshop.util.SharedPreferencesUtils;
import com.edianjucai.eshop.util.ToastUtils;
import com.edianjucai.eshop.util.UiUtils;

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
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_sub_title)
    TextView mTvSubTitle;
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
    @BindView(R.id.top_space)
    RelativeLayout mTopSpace;
    @BindView(R.id.item_modify_password)
    LinearLayout mItemModifyPassword;

    private LoginPresenter mLoginPresenter;
    private String mUserName;
    private String mPassWord;
    private LocalUser mLocalUser;
    private DialogUtil mDialogUtil;
    private int mLoginSpaceHeight;
    private int mTopSpaceHeight;

    @Override
    public int bindLayout() {
        return R.layout.fragment_my_centre;
    }

    @Override
    public void doBusiness(Context mContext) {
        initData();
        initUI();
    }

    private void initData() {

        int defult = (int) SharedPreferencesUtils.getParam(mActivity,Constant.View.MY_LOGIN_HEIGHT,-1);
        if (defult == -1){
            mLoginSpace.post(new Runnable() {
                @Override
                public void run() {
                    mLoginSpaceHeight = mLoginSpace.getMeasuredHeight();
                    SharedPreferencesUtils.setParam(mActivity, Constant.View.MY_LOGIN_HEIGHT,mLoginSpaceHeight);
                }
            });
            mTopSpace.post(new Runnable() {
                @Override
                public void run() {
                    mTopSpaceHeight = mTopSpace.getMeasuredHeight();
                    SharedPreferencesUtils.setParam(mActivity, Constant.View.MY_TOP_HEIGHT,mTopSpaceHeight);
                }
            });
        }else {
            mLoginSpaceHeight = (int) SharedPreferencesUtils.getParam(mActivity,Constant.View.MY_LOGIN_HEIGHT,-1);
            mTopSpaceHeight = (int) SharedPreferencesUtils.getParam(mActivity,Constant.View.MY_TOP_HEIGHT,-1);
        }
    }

    private void initUI() {
        mDialogUtil = new DialogUtil(mActivity);
        mLocalUser = App.getApplication().getmLocalUser();
        if (mLocalUser != null) {
            mTvUserName.setText(mLocalUser.getUserName());
            setTopSpaceZoomOut(0);
            setLoginSpaceGone();
//            setTopColor();
        } else {
            AnimUtil.AlphaAnimator(0.0f,0.0f,mHandleSpace,0);
        }
        mLoginPresenter = new MyCenterPersenterImpl(this);
        mActLoginEtUsername.addTextChangedListener(new TextWatcherImpl(mActLoginBtnLogin, mLlEditClear, -1, -1));//-1 表示不改变按钮状态
        mActLoginEtPassword.addTextChangedListener(new TextWatcherImpl(mActLoginBtnLogin, mIvEyes, -1, -1));//-1 表示不改变按钮状态
    }

//    private void setTopColor() {
//        mTvTitle.setTextColor(Color.WHITE);
//        mTopSpace.setBackgroundColor(Color.parseColor("#0080ff"));
//    }

    private void setLoginSpaceGone() {
        mLoginSpace.setVisibility(View.GONE);
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
                mLocalUser = App.getApplication().getmLocalUser();
                Intent intent = new Intent(mActivity, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL,
                        ApkConstant.SERVER_API_URL_PRE+ApkConstant.SERVER_API_URL_MID+
                                "/wap/index.php?ctl=loan_order&email2="+mLocalUser.getUserName()+"&pwd2="+mLocalUser.getUserPassword()+"&from2=app");
                $Log( ApkConstant.SERVER_API_URL_PRE+ApkConstant.SERVER_API_URL_MID+
                        "/wap/index.php?ctl=loan_order&email2="+mLocalUser.getUserName()+"&pwd2="+mLocalUser.getUserPassword()+"&from2=app");
                intent.putExtra(WebViewActivity.EXTRA_TITLE,"订单管理");
                startActivity(intent);
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
        AnimUtil.ValueAnimator(sHeight,eHeight,mLoginSpace,800);
        AnimUtil.AlphaAnimator(v,v1,mLoginSpace,800);
        AnimUtil.AlphaAnimator(v1,v,mHandleSpace,800);
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
    public void loginSuccess(LoginModel actModel) {
        setUserName(actModel);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mActLoginEtPassword.setText("");
                mActLoginEtPassword.requestFocus();
            }
        }, 800);

//        setTitleColor(Color.parseColor("#0080ff"),Color.WHITE);
//        setTopBackColor(Color.WHITE,Color.parseColor("#0080ff"));
        startAmin(mLoginSpaceHeight, 0, 1.0f, 0.0f);
        setTopSpaceZoomOut(800);
    }

    private void setUserName(LoginModel actModel) {
        if (actModel!=null){
            mTvUserName.setText(actModel.getUser_name());
        }
    }

//    private void setTopBackColor(final int startColor, final int endColor) {
//        android.animation.ValueAnimator _valueAnimator = android.animation.ValueAnimator.ofFloat(0, 1).setDuration(800);
//        _valueAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
//                float animatedFraction = (float) animation.getAnimatedValue();
//                mTopSpace.setBackgroundColor((Integer) ColorUtil.evaluateColor(animatedFraction, startColor,endColor));
//            }
//        });
//        _valueAnimator.start();
//    }


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

    private void doLogout() {
        initData();
//        setTitleColor(Color.WHITE, Color.parseColor("#0080ff"));
//        setTopBackColor(Color.parseColor("#0080ff"),Color.WHITE);
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_LOGOUT_SUCCESS));
        mLoginSpace.setVisibility(View.VISIBLE);
        startAmin(0, mLoginSpaceHeight, 0.0f, 1.0f);
        setTopSpaceZoomIn(800);
        ToastUtils.showToast("成功退出帐号!");
    }

//    private void setTitleColor(final int startColor, final int endColor) {
//        android.animation.ValueAnimator _valueAnimator = android.animation.ValueAnimator.ofFloat(0, 1).setDuration(800);
//        _valueAnimator.addUpdateListener(new android.animation.ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(android.animation.ValueAnimator animation) {
//                float animatedFraction = (float) animation.getAnimatedValue();
//                mTvTitle.setTextColor((Integer) ColorUtil.evaluateColor(animatedFraction, startColor,endColor));
//            }
//        });
//        _valueAnimator.start();
//    }


    private void doRegisterSuccess() {
        initData();
//        setTopColor();
        mLocalUser = App.getApplication().getmLocalUser();
        if (mLocalUser != null) {
            mTvUserName.setText(mLocalUser.getUserName());
        }
        setLoginSpaceGone();
        setTopSpaceZoomOut(0);
        AnimUtil.AlphaAnimator(1.0f,1.0f,mHandleSpace,0);
    }

    private void doChangePasswordSuccess() {
//        setTopColor2();
        initData();
        setLoginSpaceVisible();
    }
//    private void setTopColor2() {
//        mTvTitle.setTextColor(Color.parseColor("#0080ff"));
//        mTopSpace.setBackgroundColor(Color.WHITE);
//    }

    private void setLoginSpaceVisible() {
        EventBus.getDefault().post(new EventMsg(null, EventTag.EVENT_LOGOUT_SUCCESS));
        setTopSpaceZoomIn(0);
        mLoginSpace.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = mLoginSpace.getLayoutParams();
        layoutParams.height = mLoginSpaceHeight;
        mLoginSpace.setLayoutParams(layoutParams);
        mLoginSpace.invalidate();
        AnimUtil.AlphaAnimator(0.0f,1.0f,mLoginSpace,0);
        AnimUtil.AlphaAnimator(0.0f,0.0f,mHandleSpace,0);
    }

    /**
     * 放大头部字体
     * @param duration 动画时间
     */
    private void setTopSpaceZoomIn(int duration) {
        AnimUtil.ScaleAnimator(0.6f,1f,0.6f,1f,mTvTitle,duration);
        AnimUtil.ValueAnimator((float)(mTopSpaceHeight*0.45),mTopSpaceHeight,mTopSpace,duration);
    }

    /**
     * 缩小头部字体
     * @param duration 动画时间
     */
    private void setTopSpaceZoomOut(int duration) {
        AnimUtil.ScaleAnimator(1f,0.6f,1f,0.6f,mTvTitle,duration);
        AnimUtil.ValueAnimator(mTopSpaceHeight,(float)(mTopSpaceHeight*0.45),mTopSpace,duration);
    }
}