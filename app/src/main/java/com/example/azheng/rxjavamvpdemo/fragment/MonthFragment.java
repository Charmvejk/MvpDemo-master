package com.example.azheng.rxjavamvpdemo.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azheng.rxjavamvpdemo.MainActivity;
import com.example.azheng.rxjavamvpdemo.R;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

public class MonthFragment extends Fragment {
    private List<Fragment> tabFragments;
    private View view;
    private TextView tvTitle;
    TabLayout mIndicatorTl;
    private ViewPager mContentVp;
    private List<String> tabIndicators;
    private static Typeface mTf;
    public static MonthFragment newInstance() {
        return new MonthFragment();
    }

    private ContentPagerAdapter contentAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.month_fragment, container, false);

        tvTitle = view.findViewById(R.id.tv_title_toolbar);

        TextView tvRightTitle = view.findViewById(R.id.tv_left_title_toolbar);


        view.findViewById(R.id.iv_back_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().popBackStack();



            }
        });
        Bundle bundle = this.getArguments();//得到从Activity传来的数据

        if (bundle != null) {

            tvTitle.setText(bundle.getString("title"));

        }
        initContent();
        initTab();
        return view;
    }

    private void initContent() {
        tabIndicators = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
        }
        tabIndicators.add("全量统计");
        tabIndicators.add("按月统计");

        tabFragments = new ArrayList<>();
//        for (String s : tabIndicators) {
//    }

    }

    private void initTab() {
        mIndicatorTl.setTabMode(TabLayout.MODE_FIXED);
        mIndicatorTl.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.gray2), ContextCompat.getColor(getActivity(), R.color.white));
        mIndicatorTl.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
        ViewCompat.setElevation(mIndicatorTl, 10);
        mIndicatorTl.setupWithViewPager(mContentVp);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    Resource2Fragment.getLlk().setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().popBackStack();
                    //Toast.makeText(getActivity(), "按了返回键", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });
    }

    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }

    }
}
