package com.edianjucai.eshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.CustomView.CarouselView;
import com.edianjucai.eshop.R;

/**
 * Created by user on 2016-09-13.
 */
class PosterAdapter implements CarouselView.Adapter {

    Context mContext;

    private LayoutInflater inflater ;
    public PosterAdapter(Context context){
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public View getView(int position) {
        View view = inflater.inflate(R.layout.top_ad_item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Bitmap myBitmap = null;
        try {
            myBitmap = Glide.with(mContext).load("").asBitmap().centerCrop()
                    .into(500, 500)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(myBitmap);
        return view;
    }

    @Override
    public int getCount() {
        return 1;
    }

}
