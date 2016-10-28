package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.edianjucai.eshop.app.App;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.ApkConstant;
import com.edianjucai.eshop.dao.InitModelDao;
import com.edianjucai.eshop.model.entity.InitModel;
import com.edianjucai.eshop.model.entity.LocalUser;
import com.edianjucai.eshop.presenter.impl.ProjectListPresenterlmpl;
import com.edianjucai.eshop.presenter.usb.ProjectListPresenter;
import com.edianjucai.eshop.ui.activity.CompanyActivity;
import com.edianjucai.eshop.ui.activity.HomeActivity;
import com.edianjucai.eshop.ui.activity.WebViewActivity;
import com.edianjucai.eshop.ui.view.ProjectListView;
import com.edianjucai.eshop.util.ToastUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.List;

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

    private List<InitModel.AdvsModel> mAdvs;
    private List<InitModel.CateListModel> mCate_list;
    private ProjectListPresenter mProjectListPresenter;
    private GridListAdapter mGridListAdapter;
    private InitModel mInitModel;
    private InitModel.CateListModel mCateListModel;


    @Override
    public int bindLayout() {
        return R.layout.fragment_project_list;
    }

    @Override
    public void doBusiness(final Context mContext) {
        initData();
        mProjectListPresenter = new ProjectListPresenterlmpl(this);
        mGridListAdapter = new GridListAdapter(mCate_list, mActivity);
        mScrollView = mPullToRefreshScrollView.getRefreshableView();
        mScrollView.smoothScrollTo(0, 0); // 滑动到顶部
        initRefresh();
        initListener();
        mPosterAdapter = new PosterAdapter(mContext);
        mGvProjectList.setAdapter(mGridListAdapter);
        mTopAdView.setAdapter(mPosterAdapter);


    }

    private void initData() {
        mInitModel = InitModelDao.readInitDB();
        if (mInitModel!=null){
            mAdvs = mInitModel.getAdvs();
            mCate_list = mInitModel.getCate_list();
            addLastData(mCate_list);
        }
    }

    private void addLastData(List<InitModel.CateListModel> cate_list) {
        mCateListModel = new InitModel.CateListModel();
        mCateListModel.setName("敬请期待");
        cate_list.add(mCateListModel);
    }

    private void initListener() {
        mGvProjectList.setOnItemClickListener(this);
    }

    private void initRefresh() {
        mPullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mProjectListPresenter.requestProjectListData();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position== mCate_list.size()-1){
            return;
        }
        Intent intent = new Intent(mActivity, CompanyActivity.class);
        intent.putExtra(CompanyListFragment.COMPANY_TYPE_ID,mInitModel.getCate_list().get(position).getId());
        intent.putExtra(CompanyListFragment.COMPANY_TITLE,mInitModel.getCate_list().get(position).getName());
        startActivity(intent);
    }


    @Override
    public void setProjectListData(InitModel projectListModel) {
        mCate_list = projectListModel.getCate_list();
        addLastData(mCate_list);
        mGridListAdapter.setData(mCate_list);
        mGridListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startRequest() {

    }

    @Override
    public void finishRequest() {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void successRequest() {
        mPullToRefreshScrollView.onRefreshComplete();
    }

    @Override
    public void failRequest() {
        mPullToRefreshScrollView.onRefreshComplete();
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
        public View getView(final int position) {
            View view = inflater.inflate(R.layout.top_ad_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Glide.with(mContext)
                    .load("http://" + ApkConstant.SERVER_API_URL_MID + mAdvs.get(position).getImg())
                    .placeholder(R.mipmap.placeholder_img)
                    .error(R.mipmap.load_error_img )
                    .into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LocalUser localUser = App.getApplication().getmLocalUser();
                    if (localUser==null){
                        ToastUtils.showToast("请先登录");
                        HomeActivity activity = (HomeActivity)mActivity;
                        activity.setCurrentTab(1);
                        return;
                    }
                    Intent intent = new Intent(mActivity, WebViewActivity.class);
                    intent.putExtra(WebViewActivity.EXTRA_TITLE,mAdvs.get(position).getTitle());
                    intent.putExtra(WebViewActivity.EXTRA_URL,mAdvs.get(position).getUrl()+
                            "&email2="+localUser.getUserName()+"&pwd2="+localUser.getUserPassword()+"&from2=app");
                    startActivity(intent);
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return mAdvs.size();
        }
    }
}