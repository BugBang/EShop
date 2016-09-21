package com.edianjucai.eshop.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.panxw.android.imageindicator.ImageIndicatorView;

import java.util.List;

/**
 * Created by user on 2016-09-12.
 */
public class NetworkImageIndicatorView extends ImageIndicatorView {

    public NetworkImageIndicatorView(Context context) {
        super(context);
    }

    public NetworkImageIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupLayoutByImageUrl(List<String> urlList) {
        for (String url : urlList) {
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(url).into(imageView);
            addViewItem(imageView);
        }
    }

}