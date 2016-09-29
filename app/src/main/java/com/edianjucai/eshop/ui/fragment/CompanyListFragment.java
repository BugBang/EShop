package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;

import com.edianjucai.eshop.CustomView.MyListView;
import com.edianjucai.eshop.CustomView.TitleView;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.adapter.CompanyListAdapter;
import com.edianjucai.eshop.base.BaseFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import butterknife.BindView;

/**
 * Created by user on 2016-09-29.
 */
public class CompanyListFragment extends BaseFragment {

    @BindView(R.id.title)
    TitleView mTitle;
    @BindView(R.id.list_company)
    MyListView mListCompany;
    @BindView(R.id.pull_refresh)
    PullToRefreshScrollView mPullRefresh;

    private ScrollView mScrollView;
    private CompanyListAdapter mCompanyListAdapter;

    public static CompanyListFragment newInstance() {
        return new CompanyListFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_company_list;
    }

    @Override
    public void doBusiness(Context mContext) {
        mScrollView = mPullRefresh.getRefreshableView();
        mScrollView.smoothScrollTo(0, 0); // 滑动到顶部
        initTitle();
        initRefresh();
        initList();
    }


    private void initTitle() {
        // TODO: 2016-09-29 初始化标题
        mTitle.setTitle("");
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
                $Log("onRefresh");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPullRefresh.onRefreshComplete();
                        $Log("onRefreshComplete");
                    }
                },3000);
            }
        });
    }

    private void initList() {
        mCompanyListAdapter = new CompanyListAdapter(mActivity);
        mListCompany.setAdapter(mCompanyListAdapter);
        mListCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                $Log("postion"+position);
            }
        });
    }
}
