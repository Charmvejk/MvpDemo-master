package com.example.azheng.rxjavamvpdemo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.base.BaseActivity;
import com.example.azheng.rxjavamvpdemo.fragment.CarFragment;
import com.example.azheng.rxjavamvpdemo.fragment.RunCarFragment;

import java.util.ArrayList;
import java.util.List;

/*人口查询
 * */
public class SelectPeopleActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private List<String> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slect_people);
        initView();
    }


    private void initView() {


        TextView tvBar = findViewById(R.id.tv_title_toolbar);
        tvBar.setText("葵花宝典");
        ImageView ivLeft = findViewById(R.id.iv_back_toolbar);
        ivLeft.setOnClickListener(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("跑车Star"));
        tabLayout.addTab(tabLayout.newTab().setText("越野Star"));
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.gray), ContextCompat.getColor(this, R.color.colorMainDrawerPrimary));
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorMainDrawerPrimary));
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new RunCarFragment(), "跑车Star");
        adapter.addFragment(new CarFragment(), "越野Star");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_toolbar:
                finish();
                break;
        }
    }


}
