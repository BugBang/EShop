package com.edianjucai.eshop.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edianjucai.eshop.R;

/**
 * Created by user on 2016-09-29.
 */
public class TitleView extends FrameLayout implements View.OnClickListener{

    private LinearLayout mBack;
    private TextView mTitle;
    private OnBackButtonClickListener mOnBackButtonClickListener;

    public TitleView(Context context) {
        this(context,null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.title_view, this, true);

        mBack = (LinearLayout) findViewById(R.id.iv_back);
        mTitle = (TextView) findViewById(R.id.tv_title);

        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (mOnBackButtonClickListener != null)
            mOnBackButtonClickListener.onBackBtnClick(v);
    }

    public void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setBackInvisibil() {
        mBack.setVisibility(INVISIBLE);
    }

    public void setBackButtonListener(OnBackButtonClickListener onBackButtonClickListener){
        this.mOnBackButtonClickListener = onBackButtonClickListener;
    }

    public interface OnBackButtonClickListener {
        void onBackBtnClick(View view);
    }
}
