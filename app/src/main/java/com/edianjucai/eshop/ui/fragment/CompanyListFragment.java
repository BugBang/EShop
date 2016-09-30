package com.edianjucai.eshop.ui.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;

import com.edianjucai.eshop.CustomView.MyListView;
import com.edianjucai.eshop.CustomView.TitleView;
import com.edianjucai.eshop.R;
import com.edianjucai.eshop.adapter.CompanyListAdapter;
import com.edianjucai.eshop.base.BaseFragment;
import com.edianjucai.eshop.model.entity.CompanyListModel;
import com.edianjucai.eshop.presenter.impl.CompanyListPresenterImpl;
import com.edianjucai.eshop.presenter.usb.CompanyListPresenter;
import com.edianjucai.eshop.ui.view.CompanyListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

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

    private ScrollView mScrollView;
    private CompanyListAdapter mCompanyListAdapter;
    private CompanyListPresenter mCompanyListPresenter;
    private CompanyListModel mCompanyListModel;

    private PopupWindow mEditPop;
    private View mEditPopView;
    private EditText mEditTextSearch;

    public static CompanyListFragment newInstance() {
        return new CompanyListFragment();
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_company_list;
    }

    @Override
    public void doBusiness(Context mContext) {
        initPop();
        mCompanyListPresenter = new CompanyListPresenterImpl(this);
        mScrollView = mPullRefresh.getRefreshableView();
        mScrollView.smoothScrollTo(0, 0); // 滑动到顶部
        initTitle();
        initRefresh();
    }

    private void initPop() {
        mEditPopView = getActivity().getLayoutInflater().inflate(
                R.layout.pop_edit_search, null);
        mEditPop = new PopupWindow(mEditPopView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mEditTextSearch = (EditText) mEditPopView.findViewById(R.id.et_pop_search);
        mEditPop.setTouchable(true);
        mEditPop.setOutsideTouchable(true);
        mEditPop.setBackgroundDrawable(new ColorDrawable(
                0x00000000));

        mEditPop.getContentView().setFocusableInTouchMode(true);
        mEditPop.getContentView().setFocusable(true);
        intiEditView();
    }


    private void intiEditView() {
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //                mAdapter.getFilter().filter(s);

                mCompanyListAdapter.getFilter().filter(s);
                $Log("s="+s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                // TODO: 2016-09-29 传type
                mCompanyListPresenter.getCompanyList(9);
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
                $Log("postion" + position);
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
        mPullRefresh.onRefreshComplete();
    }


    @OnClick({R.id.iv_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                mEditPop.showAsDropDown(mIvSearch);
                break;
        }
    }
}
