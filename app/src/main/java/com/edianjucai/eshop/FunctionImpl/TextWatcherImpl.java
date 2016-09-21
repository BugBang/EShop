package com.edianjucai.eshop.FunctionImpl;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by user on 2016-09-14.
 */
public class TextWatcherImpl implements TextWatcher {

    private Button mButton;
    private LinearLayout mLinearLayout;
    private int mBeforeResourceId;
    private int mAfterResourceId;


    /**
     *
     * @param button - 要改变状态的button
     * @param linearLayout - 要改变状态的linearLayout
     * @param beforeResourceId -button改变之前的颜色(不可点击) 不改变传入-1
     * @param afterResourceId -button改变之后的颜色(可点击) 不改变传入-1
     */
    public TextWatcherImpl(Button button,LinearLayout linearLayout,int beforeResourceId,int afterResourceId){
        this.mButton = button;
        this.mLinearLayout = linearLayout;
        this.mBeforeResourceId = beforeResourceId;
        this.mAfterResourceId = afterResourceId;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.length() > 0) {
            mLinearLayout.setVisibility(View.VISIBLE);
        } else {
            mLinearLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            mLinearLayout.setVisibility(View.VISIBLE);
            if (!(mAfterResourceId ==-1)){
                mButton.setBackgroundResource(mAfterResourceId);
                mButton.setEnabled(true);
            }
        } else {
            mLinearLayout.setVisibility(View.INVISIBLE);
            if (!(mBeforeResourceId ==-1)){
                mButton.setBackgroundResource(mBeforeResourceId);
                mButton.setEnabled(false);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}