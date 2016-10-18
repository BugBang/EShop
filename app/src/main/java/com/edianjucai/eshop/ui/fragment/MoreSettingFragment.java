package com.edianjucai.eshop.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.dao.InitModelDao;
import com.edianjucai.eshop.model.entity.InitModel;
import com.edianjucai.eshop.presenter.impl.FeedBackPersenterImpl;
import com.edianjucai.eshop.presenter.usb.FeedBackPresenter;
import com.edianjucai.eshop.ui.activity.WebViewActivity;
import com.edianjucai.eshop.ui.view.FeedBackView;
import com.edianjucai.eshop.util.ClearCacheUtil;
import com.edianjucai.eshop.util.DialogUtil;
import com.edianjucai.eshop.util.IntentUtil;
import com.edianjucai.eshop.util.PackageUtil;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-12.
 */
public class MoreSettingFragment extends BaseFragment implements FeedBackView{

    @BindView(R.id.ll_about_us)
    LinearLayout mLlAboutUs;
    @BindView(R.id.tv_cell_center)
    TextView mTvCellCenter;
    @BindView(R.id.ll_cell_center)
    LinearLayout mLlCellCenter;
    @BindView(R.id.tv_send_email)
    TextView mTvSendEmail;
    @BindView(R.id.ll_send_email)
    LinearLayout mLlSendEmail;
    @BindView(R.id.ll_idea_feedback)
    LinearLayout mLlIdeaFeedback;
    @BindView(R.id.tv_update_version)
    TextView mTvUpdateVersion;
    @BindView(R.id.ll_update_version)
    LinearLayout mLlUpdateVersion;
    @BindView(R.id.tv_clear_cache)
    TextView mTvClearCache;
    @BindView(R.id.ll_clear_cache)
    LinearLayout mLlClearCache;

    private DialogUtil mDialogUtil;
    private InitModel mInitModel;
    private FeedBackPresenter mFeedBackPresenter;
    private AlertDialog mDialog;

    @Override
    public int bindLayout() {
        return R.layout.fragment_more_setting;
    }

    @Override
    public void doBusiness(Context mContext) {
        initVersion();
        mFeedBackPresenter = new FeedBackPersenterImpl(this);
        mInitModel = InitModelDao.readInitDB();
        mDialogUtil = new DialogUtil(mActivity);
        setData();
    }

    private void initVersion() {
        PackageInfo pi = PackageUtil.getCurrentAppPackageInfo(
                App.getApplication(), getActivity().getPackageName());
        mTvUpdateVersion.setText("V" + pi.versionName);
    }

    private void setData() {
        mTvClearCache.setText(ClearCacheUtil.getTotalCacheSize(mActivity));
    }

    @OnClick({R.id.ll_about_us, R.id.ll_cell_center, R.id.ll_send_email, R.id.ll_idea_feedback, R.id.ll_update_version, R.id.ll_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_about_us:
                if (mInitModel!=null){
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    int about_info = mInitModel.getAbout_info();
                    intent.putExtra(WebViewActivity.EXTRA_ARTICLE_ID,about_info+"");
                    startActivity(intent);
                }
                break;
            case R.id.ll_cell_center:
                clickServicePhone();
                break;
            case R.id.ll_send_email:
                clickServiceEmail();
                break;
            case R.id.ll_idea_feedback:
                clickFeedBack();
                break;
            case R.id.ll_update_version:
                $Log("ll_update_version");
                break;
            case R.id.ll_clear_cache:
                clearCache();
                break;
        }
    }

    private void clickFeedBack() {
        showAlertDialog();
    }

    private void clearCache() {
        ClearCacheUtil.clearAllCache(mActivity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mActivity).clearDiskCache();
            }
        }).start();
        mTvClearCache.setText(ClearCacheUtil.getTotalCacheSize(mActivity));
    }

    private void clickServiceEmail() {
        if (mInitModel!=null){
            String kf_email = mInitModel.getKf_email();
            startActivity(Intent.createChooser(IntentUtil.getEmailIntent(kf_email), "邮件"));
        }
    }


    private void clickServicePhone() {
        if (mInitModel!=null){
            final String kf_phone = mInitModel.getKf_phone();
            mDialogUtil.confirm("提示", "确定拨打客服电话?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (kf_phone != null) {
                        startActivity(IntentUtil.getCallNumberIntent(kf_phone));
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
    }


    private void showAlertDialog() {
        mDialog = new AlertDialog.Builder(mActivity).create();
        mDialog.setView(LayoutInflater.from(mActivity).inflate(R.layout.dialog_feed_back, null));
        mDialog.show();
        mDialog.getWindow().setContentView(R.layout.dialog_feed_back);
        Button _bt = (Button) mDialog.findViewById(R.id.bt_submit);
        final EditText _et = (EditText) mDialog.findViewById(R.id.et_feet_back);
        _bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = _et.getText().toString();
                if (TextUtils.isEmpty(content)){
                    ToastUtils.showToast("内容不可为空");
                }else {
                    mFeedBackPresenter.requestFeedBack(content);
                }
            }
        });

    }

    @Override
    public void startFeedBack() {
        showLoadingDialog("反馈中");
    }

    @Override
    public void finishFeedBack() {
        if (mDialog!=null){
            mDialog.dismiss();
        }
        hideLoadingDialog();
    }

    @Override
    public void successFeedBack() {
        ToastUtils.showToast("反馈成功");
        if (mDialog!=null){
            mDialog.dismiss();
        }
        hideLoadingDialog();
    }

    @Override
    public void failFeedBack(String err) {
        ToastUtils.showToast(err);
        hideLoadingDialog();
    }
}
