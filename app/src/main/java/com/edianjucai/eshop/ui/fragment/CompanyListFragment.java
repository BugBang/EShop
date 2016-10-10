package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.edianjucai.eshop.CustomView.MyListView;
import com.edianjucai.eshop.CustomView.TitleView;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.adapter.CompanyListAdapter;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.constant.ApkConstant;
import com.edianjucai.eshop.model.entity.CompanyListModel;
import com.edianjucai.eshop.presenter.impl.CompanyListPresenterImpl;
import com.edianjucai.eshop.presenter.usb.CompanyListPresenter;
import com.edianjucai.eshop.ui.view.CompanyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListFragment extends BaseFragment implements CompanyListView {

    @BindView(R.id.title)
    TitleView mTitle;
    @BindView(R.id.list_company)
    MyListView mListCompany;
    @BindView(R.id.pull_refresh)
    PullToRefreshScrollView mPullRefresh;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.iv_banner)
    ImageView mIvBanner;
    @BindView(R.id.et_search)
    EditText mEtSearch;
    @BindView(R.id.ll_edit_search)
    LinearLayout mLlSearch;

    private ScrollView mScrollView;
    private CompanyListAdapter mCompanyListAdapter;
    private CompanyListPresenter mCompanyListPresenter;
    private CompanyListModel mCompanyListModel;

    private String mTypeId;
    private String mStringTitle;

    public static final String TYPE_ID = "type_id";
    public static final String TITLE = "title";
    private List<CompanyListModel.CompanyListBannerModel> mBannerList;

    public static CompanyListFragment newInstance() {
        return new CompanyListFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_company_list;
    }

    @Override
    public void doBusiness(Context mContext) {
        initParms();
        intiEditView();
        mCompanyListPresenter = new CompanyListPresenterImpl(this);
        mScrollView = mPullRefresh.getRefreshableView();
        mScrollView.smoothScrollTo(0, 0); // 滑动到顶部
        initTitle();
        initRefresh();
    }

    private void initParms() {
        mTypeId = getArguments().getString(TYPE_ID);
        mStringTitle = getArguments().getString(TITLE);
    }

    private void intiEditView() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCompanyListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void initTitle() {
        mTitle.setTitle(mStringTitle);
        mTitle.setBackButtonListener(new TitleView.OnBackButtonClickListener() {
            @Override
            public void onBackBtnClick(View view) {
                mActivity.finish();
            }
        });
    }

    private void initRefresh() {
        mPullRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mCompanyListPresenter.getCompanyList(mTypeId);
            }
        });
        mPullRefresh.setRefreshing();
    }

    private void initList() {

        mCompanyListAdapter = new CompanyListAdapter(mCompanyListModel.getList(), mActivity);
        mListCompany.setAdapter(mCompanyListAdapter);
        mListCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2016-10-09 跳转网页
                $Log("postion" + mCompanyListAdapter.mListModel.get(position).getName());
                mCompanyListModel.getList().get(position).getName();
            }
        });
    }

    @Override
    public void startRequest() {

    }

    @Override
    public void finishRequest() {
        mPullRefresh.onRefreshComplete();
    }

    @Override
    public void failRequest(String showErr) {
        mPullRefresh.onRefreshComplete();

    }

    @Override
    public void successRequest(CompanyListModel companyListModel) {
        this.mCompanyListModel = companyListModel;
        if (mCompanyListAdapter == null) {
            initList();
        }
        if (companyListModel!=null){
            mBannerList = companyListModel.getDeal_cates();
            for (int i =0;i<mBannerList.size();i++){
                if (mBannerList.get(i).getId().equals(mTypeId)){
                    Glide.with(mActivity).load("http://" + ApkConstant.SERVER_API_URL_MID +mBannerList.get(i).getBanner()).into(mIvBanner);
                }
            }
        }

        mPullRefresh.onRefreshComplete();
    }


    private boolean isShow;
    @OnClick({R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                if (isShow){
                    mLlSearch.setVisibility(View.GONE);
                    isShow = !isShow;
                }else {
                    mLlSearch.setVisibility(View.VISIBLE);
                    isShow = !isShow;
                }
                break;
        }
    }
}
