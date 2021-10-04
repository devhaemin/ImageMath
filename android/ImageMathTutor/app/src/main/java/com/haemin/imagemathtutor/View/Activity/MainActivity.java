package com.haemin.imagemathtutor.View.Activity;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.tabs.TabLayout;
import com.haemin.imagemathtutor.Adapter.MainPagerAdapter;
import com.haemin.imagemathtutor.R;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tab_main)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); //BindView by ID
        setSupportActionBar(toolbar); //set Toolbar for appbar

        MainPagerAdapter pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), MainPagerAdapter.FRAGMENT_COUNT);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager, true);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
