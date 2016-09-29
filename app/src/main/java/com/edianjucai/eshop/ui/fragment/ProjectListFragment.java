package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.CustomView.CarouselView;
import com.edianjucai.eshop.CustomView.MyGridView;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.adapter.CarouselViewAdapter;
import com.edianjucai.eshop.adapter.GridListAdapter;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.model.entity.ProjectListModel;
import com.edianjucai.eshop.ui.activity.CompanyActivity;
import com.edianjucai.eshop.ui.view.ProjectListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import butterknife.BindView;

/**
 * Created by user on 2016-09-12.
 */
public class ProjectListFragment extends BaseFragment implements AdapterView.OnItemClickListener,ProjectListView{

    @BindView(R.id.top_ad_view)
    CarouselView mTopAdView;
    @BindView(R.id.gv_project_list)
    MyGridView mGvProjectList;
    @BindView(R.id.pull_refresh)
    PullToRefreshScrollView mPullToRefreshScrollView;

    private ScrollView mScrollView;
    private PosterAdapter mPosterAdapter;

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
        mScrollView = mPullToRefreshScrollView.getRefreshableView();
        mScrollView.smoothScrollTo(0, 0); // 滑动到顶部
        initRefresh();
        initListener();
        mPosterAdapter = new PosterAdapter(mContext);
        mGvProjectList.setAdapter(new GridListAdapter(mActivity));
        mTopAdView.setAdapter(mPosterAdapter);
    }

    private void initListener() {
        mGvProjectList.setOnItemClickListener(this);
    }

    private void initRefresh() {
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                $Log("onRefresh");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullToRefreshScrollView.onRefreshComplete();
                        $Log("onRefreshComplete");
                    }
                },3000);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        $Log("position="+position);
        Intent intent = new Intent(mActivity, CompanyActivity.class);
        startActivity(intent);
    }


    @Override
    public void setProjectListData(ProjectListModel projectListModel) {

    }

    @Override
    public void startRequest() {

    }

    @Override
    public void finishRequest() {

    }

    @Override
    public void successRequest() {

    }

    @Override
    public void failRequest(String showErr) {

    }

    class PosterAdapter implements CarouselViewAdapter {

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