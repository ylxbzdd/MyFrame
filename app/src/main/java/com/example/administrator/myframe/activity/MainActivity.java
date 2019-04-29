package com.example.administrator.myframe.activity;

import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.example.administrator.myframe.R;
import com.example.administrator.myframe.adapter.viewpageAdapter;
import com.example.administrator.myframe.base.BaseActivity;
import com.example.administrator.myframe.fragment.OneFragment;
import com.example.administrator.myframe.fragment.TwoFragment;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class MainActivity extends BaseActivity {

    private ViewPager viewpager;
    private BottomNavigationView bottom_view;
    private String[] str = new String[]{"one", "two"};
    private viewpageAdapter viewpageAdapter;

    @Override
    public void initview() {
        setContentView(R.layout.activity_main);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        bottom_view = (BottomNavigationView) findViewById(R.id.bottom_view);
        coo2 = new int[]{R.color.black, R.color.red};
    }

    @Override
    public void initdata() {
        setbottom();
    }

    String[] strr = new String[]{"one", "two"};
    int[] image = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    int[] coo2 = null;

    private void setbottom() {
        setviewpager();
        if (bottom_view != null) {
            // 汉字是否一直显`示:(false:显示选中的；true全部显示)
//            bnv_menu.isWithText(false);
            bottom_view.isWithText(true);
            // 整体背景色
            bottom_view.isColoredBackground(true);
            //当 bottomNavigationView.isColoredBackground(true);设置为false时icon和汉字显示颜色能用
            bottom_view.isColoredBackground(false);
            bottom_view.setItemActiveColorWithoutColoredBackground(ContextCompat.getColor(this, R.color.red));
            // 选中字体的大小
            bottom_view.setTextActiveSize(getResources().getDimension(R.dimen.dp_10));
            //未选中字体的大小
            bottom_view.setTextInactiveSize(getResources().getDimension(R.dimen.dp_10));
            // 去掉影子
            bottom_view.disableShadow();
            // 设置字体
            bottom_view.setFont(Typeface.DEFAULT);

//            bnv_menu.setUpWithViewPager(viewpager, coo2, image);
        }
        for (int i = 0; i < strr.length; i++) {
            BottomNavigationItem bottomNavigationItem = new BottomNavigationItem(strr[i], coo2[i], image[i]);
            bottom_view.addTab(bottomNavigationItem);
        }
        bottom_view.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                viewpager.setCurrentItem(index);
            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (bottom_view.getCurrentItem() != position) {
                    bottom_view.selectTab(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setviewpager() {
        viewpageAdapter = new viewpageAdapter(getSupportFragmentManager(), str);
        viewpageAdapter.addFragment(new OneFragment());
        viewpageAdapter.addFragment(new TwoFragment());
        viewpager.setAdapter(viewpageAdapter);
    }
}
