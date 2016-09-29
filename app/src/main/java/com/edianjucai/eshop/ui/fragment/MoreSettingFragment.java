package com.edianjucai.eshop.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.util.ClearCacheUtil;
import com.edianjucai.eshop.util.DialogUtil;
import com.edianjucai.eshop.util.IntentUtil;
import com.edianjucai.eshop.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-12.
 */
public class MoreSettingFragment extends BaseFragment {

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

    @Override
    public int bindLayout() {
        return R.layout.fragment_more_setting;
    }

    @Override
    public void doBusiness(Context mContext) {
        mDialogUtil = new DialogUtil(mActivity);
        setData();
    }

    private void setData() {
        mTvClearCache.setText(ClearCacheUtil.getTotalCacheSize(mActivity));
    }

    @OnClick({R.id.ll_about_us, R.id.ll_cell_center, R.id.ll_send_email, R.id.ll_idea_feedback, R.id.ll_update_version, R.id.ll_clear_cache})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_about_us:
                $Log("ll_about_us");
                break;
            case R.id.ll_cell_center:
                clickServicePhone();
                break;
            case R.id.ll_send_email:
                clickServiceEmail();
                break;
            case R.id.ll_idea_feedback:
                $Log("ll_idea_feedback");
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
        mTvClearCache.setText(ClearCacheUtil.getTotalCacheSize(mActivity));

    }

    private void clickServiceEmail() {
        // TODO: 2016-09-27 从数据库取出客服邮箱 
        if ("ympbx@163.com" != null) {
            startActivity(Intent.createChooser(IntentUtil.getEmailIntent("ympbx@163.com"), "邮件"));
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


    private void showAlertDialog() {
        // TODO: 2016-09-28 添加意见反馈接口
        final AlertDialog dialog = new AlertDialog.Builder(mActivity).create();
        dialog.setView(LayoutInflater.from(mActivity).inflate(R.layout.dialog_feed_back, null));
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_feed_back);
        Button _bt = (Button) dialog.findViewById(R.id.bt_submit);
        final EditText _et = (EditText) dialog.findViewById(R.id.et_feet_back);
        _bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}
