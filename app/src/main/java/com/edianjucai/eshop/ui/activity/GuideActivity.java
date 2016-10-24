package com.edianjucai.eshop.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.edianjucai.eshop.R;
import com.edianjucai.eshop.base.BaseActivity;
import com.edianjucai.eshop.base.BaseFragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by user on 2016-09-26.
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.guide_view)
    ViewPager mGuideView;
    private List<View> mImageViews; // 滑动的图片集合
    private int[] imageResId; // 图片ID
    private GestureDetector gestureDetector;


    private int currentItem = 0; // 当前图片的索引号
    private int flaggingWidth;// 互动翻页所需滚动的长度是当前屏幕宽度的1/3
    private Button mBtnPass;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_guide;
    }

    @Override
    public void doBusiness(Context mContext) {
        doView();
    }

    private void doView() {
        gestureDetector = new GestureDetector(new GuideViewTouch());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        flaggingWidth = dm.widthPixels / 3;
        imageResId = new int[]
                {R.mipmap.guide_11, R.mipmap.guide_12, R.mipmap.guide_13, R.mipmap.guide_14, R.mipmap.guide_15};
        mImageViews = new ArrayList<View>();
        // 初始化图片资源
        LayoutInflater viewInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View convertView0 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout0 = (LinearLayout) convertView0.findViewById(R.id.guide_item);
        linearLayout0.setBackgroundDrawable(new BitmapDrawable(readBitMap(this, imageResId[0])));
        mImageViews.add(linearLayout0);
        initBt(convertView0);
        // 1
        View convertView1 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout1 = (LinearLayout) convertView1.findViewById(R.id.guide_item);
        linearLayout1.setBackgroundDrawable(new BitmapDrawable(readBitMap(this, imageResId[1])));
        mImageViews.add(linearLayout1);
        initBt(convertView1);
        // 2
        View convertView2 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout2 = (LinearLayout) convertView2.findViewById(R.id.guide_item);
        linearLayout2.setBackgroundDrawable(new BitmapDrawable(readBitMap(this, imageResId[2])));
        mImageViews.add(linearLayout2);
        initBt(convertView2);
        // 3
        View convertView3 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout3 = (LinearLayout) convertView3.findViewById(R.id.guide_item);
        linearLayout3.setBackgroundDrawable(new BitmapDrawable(readBitMap(this, imageResId[3])));
        mImageViews.add(linearLayout3);
        initBt(convertView3);
        // 4
        View convertView4 = viewInflater.inflate(R.layout.guide_item, null);
        LinearLayout linearLayout4 = (LinearLayout) convertView4.findViewById(R.id.guide_item);
        linearLayout4.setBackgroundDrawable(new BitmapDrawable(readBitMap(this, imageResId[4])));
        mImageViews.add(linearLayout4);
        initBt(convertView4);
        // button监听
        Button btn = (Button) convertView4.findViewById(R.id.start);
        btn.setVisibility(View.VISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GoToMainActivity();
            }
        });

        mGuideView.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        mGuideView.setOnPageChangeListener(new MyPageChangeListener());
    }

    private void initBt(View view) {
        mBtnPass = (Button) view.findViewById(R.id.bt_pass);
        mBtnPass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                GoToMainActivity();
            }
        });

    }

    private Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }


    void GoToMainActivity() {
        Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        public void onPageSelected(int position) {
            currentItem = position;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageResId.length;
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mImageViews.get(arg1));
            return mImageViews.get(arg1);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GoToMainActivity();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }
    	private class GuideViewTouch extends GestureDetector.SimpleOnGestureListener {
    		@Override
    		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    			if (currentItem == 3) {
                    mBtnPass.setVisibility(View.INVISIBLE);
    				if (Math.abs(e1.getX() - e2.getX()) > Math.abs(e1.getY() - e2.getY()) && (e1.getX() - e2.getX() <= (-flaggingWidth) || e1.getX() - e2.getX() >= flaggingWidth)) {
    					if (e1.getX() - e2.getX() >= flaggingWidth) {
    						GoToMainActivity();
    						return true;
    					}
    				}
    			}
    			return false;
    		}
    	}
    @Override
    protected int getFragmentContentId() {
        return 0;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return null;
    }

}
