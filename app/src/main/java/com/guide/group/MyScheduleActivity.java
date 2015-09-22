package com.guide.group;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.guide.R;
import com.guide.base.BaseActivity;
import com.squareup.picasso.Picasso;

/**
 * Created by mac on 2/9/15.
 */
public class MyScheduleActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    /**
     * ViewPager
     */
    private ViewPager viewPager;

    /**
     * 装ImageView数组
     */
    private ImageView[] mImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        //载入图片资源
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String[] list = bundle.getString("schedule").split(";");
            //将图片装载到数组中
            mImageViews = new ImageView[list.length];
            for (int i = 0; i < list.length; ++i) {
                ImageView imageView = new ImageView(this);
                mImageViews[i] = imageView;
                Picasso.with(this).load(list[i]).into(imageView);
            }

            //设置Adapter
            viewPager.setAdapter(new MyAdapter());
            viewPager.setOnPageChangeListener(this);
            viewPager.setCurrentItem(0);
            setActionBarTitle("行程单 1/" + mImageViews.length);
        }
    }

    public class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(mImageViews[position % mImageViews.length], 0);
            } catch (Exception e) {
                //handler something
            }
            return mImageViews[position % mImageViews.length];
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        setActionBarTitle("行程单 " + (arg0 + 1) + "/" + mImageViews.length);
    }

}

