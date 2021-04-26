package com.example.azheng.rxjavamvpdemo.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.azheng.rxjavamvpdemo.MainActivity;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.bean.XNYBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by yifeng on 16/8/3.
 */
public class TabListFragment extends Fragment implements OnChartValueSelectedListener {
    protected static BarChart mChart;
    private static final String EXTRA_CONTENT = "content";
    private static Typeface mTf;
    private ListView mContentLv;
    private static View view;
    protected static String[] mMonths = new String[]{

    };
    private static XNYBean resultBean;
    private static List<String> mList = new ArrayList<>();
    private static List<String> mListCount = new ArrayList<>();

    public static TabListFragment newInstance(String content, Typeface mTf1) {
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_CONTENT, content);
        TabListFragment tabContentFragment = new TabListFragment();
        tabContentFragment.setArguments(arguments);
        mTf = mTf1;
        if (view != null) {
            init1(view);


            return null;
        }
        return tabContentFragment;
    }


    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    mMonths = new String[mListCount.size()];
                    mListCount.toArray(mMonths);

                    setData(12, 50);

                    break;
                case 2:


                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tab_list, container, false);
        TextView tvRightTitle = view.findViewById(R.id.tv_left_title_toolbar1);
        Bundle bundle = this.getArguments();//得到从Activity传来的数据

        if (bundle != null) {
            tvRightTitle.setVisibility(View.VISIBLE);

            tvRightTitle.setText(bundle.getString("title"));

        }
        view.findViewById(R.id.iv_back_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resource2Fragment.getLlk().setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });


        init1(view);
        return view;

    }


    public static void init1(View view) {


        mChart = (BarChart) view.findViewById(R.id.chart1);

        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        YAxisValueFormatter custom = new MyYValueFormatter();

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(7, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(7, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        mChart.animateY(2500);
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/loginfo/tj_hj_clientall/data";//发送路径

        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras> </paras>";

        new Thread(new Runnable() {
            @Override
            public void run() {

                postXml(path, xml, 1);

            }


        }).start();


    }


    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

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
    @Override
    public void onNothingSelected() {

    }

    private static void setData(int count, float range) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mListCount.size(); i++) {
            String a = mMonths[i];
            xVals.add(mMonths[i]);
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < mList.size(); i++) {
            float mult = (range + 1);

            yVals1.add(new BarEntry(Integer.parseInt(mList.get(i)), i));


        }

        BarDataSet set1 = new BarDataSet(yVals1, "数据量");
        set1.setBarSpacePercent(50f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        //data.setGroupSpace(100);
        data.setValueFormatter(new CustomerPercentFormatter(data));
        data.setValueTextSize(10f);
        data.setValueTypeface(mTf);
        mChart.setData(data);
        mChart.setVisibleXRangeMaximum(4);
    }

    private class ContentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_simple_list_2, null);

            return contentView;
        }
    }

    public static void postXml(String url, String xmlStr, int i) {
        RequestBody body = RequestBody.create(MediaType.parse("charset=UTF-8"), xmlStr);
        Request requestOk = new Request.Builder()
                .url(url)

                .post(body)
                .build();

        Response response;
        try {
            response = new OkHttpClient().newCall(requestOk).execute();
            String jsonString = response.body().string();
            mList.clear();
            mListCount.clear();
            if (response.isSuccessful()) {
                Gson gson = new Gson();


                resultBean = gson.fromJson(jsonString, XNYBean.class);


                for (int g = 0; g < resultBean.getData().size(); g++) {
                    if (resultBean.getData().size() > g) {
                        if (resultBean.getData().get(g).getDbcname().length() <= 6) {
                            if (mList.size() != 4) {
                                int a = (int) Math.round(resultBean.getData().get(g).getRows_sum());
                                mList.add("" + a);
                                mListCount.add("" + resultBean.getData().get(g).getDbcname());
                            }
                        }
                    } else {


                    }


                }

//                } else if ("电子证照".equals(title)) {
//                    for (int g = 41; g < 44; g++) {
//                        if (resultBean.getData().size() > g) {
//                            if (mList.size() != 4) {
//                                mList.add("" + resultBean.getData().get(g).getTable_rows());
//                                mListCount.add(resultBean.getData().get(g).getTablecname());
//                            }
//                        } else {
//
//
//                        }
//                    }
//                } else {
//                    for (int g = 0; g < 6; g++) {
//                        if (resultBean.getData().size() > g) {
//                            if (resultBean.getData().get(g).getTablecname().length() <= 6) {
//                                if (mList.size() != 4) {
//                                    mList.add("" + resultBean.getData().get(g).getTable_rows());
//                                    mListCount.add(resultBean.getData().get(g).getTablecname());
//                                }
//                            } else {
//
//                            }
//
//
//                        } else {
//
//                        }
//                    }
//
//                }

                Message msg = new Message();
                msg.what = 1;

                handler.sendMessage(msg);//用activity中的handler发送消息
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }


    }
}
