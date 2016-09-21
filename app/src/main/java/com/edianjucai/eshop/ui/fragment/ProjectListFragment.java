package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.CustomView.CarouselView;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by user on 2016-09-12.
 */
public class ProjectListFragment extends BaseFragment {

    @BindView(R.id.top_ad_view)
    CarouselView mTopAdView;

    String[] mStrings = new String[]{
            "http://112.74.194.230/public/avatar/temp/2059virtual_avatar_middle.jpg",
            "http://img.taopic.com/uploads/allimg/130710/267873-130G011000550.jpg",
            "http://112.74.194.230/public/avatar/temp/2059virtual_avatar_middle.jpg",
            "http://112.74.194.230/public/avatar/temp/2059virtual_avatar_middle.jpg",
            "http://112.74.194.230/public/avatar/temp/2059virtual_avatar_middle.jpg"
    };

    @Override
    public int bindLayout() {
        return R.layout.fragment_project_list;
    }

    @Override
    public void doBusiness(final Context mContext) {
        PosterAdapter adapter = new PosterAdapter(mContext);
        mTopAdView.setAdapter(adapter);

    }


    class PosterAdapter implements CarouselView.Adapter {

        Context mContext;
        private LayoutInflater inflater;
        public PosterAdapter(Context context) {
            this.mContext = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public View getView(int position) {
            View view = inflater.inflate(R.layout.top_ad_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Glide.with(mContext).load(mStrings[position]).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    $Log("imageView");
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return mStrings.length;
        }

    }
}