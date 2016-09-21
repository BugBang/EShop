package com.edianjucai.eshop.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.edianjucai.eshop.R;

/**
 * Created by user on 2016-09-19.
 */
public class ProgressDialog extends Dialog {

    private TextView mTxtMsg = null;
    private ProgressBar mPbCircle = null;

    public TextView getmTxtMsg() {
        return mTxtMsg;
    }

    public void setmTxtMsg(TextView mTxtMsg) {
        this.mTxtMsg = mTxtMsg;
    }

    public void setMsg(String msg) {
        if (mTxtMsg != null && msg != null) {
            mTxtMsg.setText(msg);
        }
    }

    public ProgressDialog(Context context) {
        super(context);
        init();
    }

    public ProgressDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_custom_loading, null);
        mTxtMsg = (TextView) view.findViewById(R.id.dialog_custom_loading_txt_progress_msg);
        mPbCircle = (ProgressBar) view.findViewById(R.id.dialog_custom_loading_pb_progress);
        mPbCircle.setIndeterminateDrawable(getContext().getResources().getDrawable(R.drawable.rotate_progress_white));
        this.setContentView(view);

    }

}

