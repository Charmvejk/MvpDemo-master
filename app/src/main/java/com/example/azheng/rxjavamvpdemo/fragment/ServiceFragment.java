package com.example.azheng.rxjavamvpdemo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.azheng.rxjavamvpdemo.MainActivity;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.bean.ZYGLBean;
import com.example.azheng.rxjavamvpdemo.bean.ZYGLSCBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sxt.library.chart.ChartPie;
import com.sxt.library.chart.bean.ChartPieBean;
import com.sxt.library.chart.listener.LineOnScrollChangeListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceFragment extends Fragment implements SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {
    private View view;
    private boolean is = false;
    private TextView tvTitle;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private static MyRecyclerAdapter recycleAdapter;
    private static ZYGLBean resultBean;
    private static ZYGLSCBean resultBean1;
    private EditText edtSearch;
    LoadingDailog.Builder loadBuilder;
    LoadingDailog dialog;
    private static List<String> mList = new ArrayList<>();
    private static List<String> mListCount = new ArrayList<>();
    private ImageView ivClear;
    private static List<String> wholeList;
    String mess = null;
    protected String[] mMonths = new String[]{

    };
    protected BarChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    private Typeface mTf;
    private String title;


    private Typeface tf;
    protected String[] mParties = new String[]{

    };
    private LinearLayout lineLayoutList;
    protected String[] mPartie1 = new String[]{""

    };
    private List<ChartPieBean> pieBeanList;

    public static ServiceFragment newInstance(String a) {
        return new ServiceFragment();
    }

    private Handler handler = new Handler() {
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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout._fragment, container, false);

        tvTitle = view.findViewById(R.id.tv_title_toolbar);
        ivClear = view.findViewById(R.id.iv_clear);
        lineLayoutList = view.findViewById(R.id.line_layout_list);
        TextView tvRightTitle = view.findViewById(R.id.tv_left_title_toolbar);

        edtSearch = view.findViewById(R.id.serach);


        tvTitle.setText("数据分析");
        view.findViewById(R.id.iv_back_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 getActivity().getSupportFragmentManager().popBackStack();


            }
        });


        Bundle bundle = this.getArguments();//得到从Activity传来的数据

        if (bundle != null) {
            tvRightTitle.setVisibility(View.VISIBLE);
            mess = bundle.getString("data");
            tvRightTitle.setText(bundle.getString("title"));
            title = bundle.getString("title");
        }

        view.findViewById(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtSearch.getText().toString().trim().isEmpty()) {
                    Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
                    edtSearch.startAnimation(shake);
                    Toast.makeText(getActivity(), "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    hideInputKeyboard(edtSearch);
                    try {

                        final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/usermanager/db_regist_dest_name/data";//发送路径

                        InputStream inStream = null;
                        try {
                            inStream = getResources().getAssets().open("zydz.xml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//        InputStream inStream = this.getClass().getClassLoader()
//                .getResourceAsStream("zydz.xml");//加载本地XML文件
                        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras><para><name>dbname</name><sqldbtype>VarChar</sqldbtype><value>DZZZ</value></para></paras>";

                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                postXml(path, xml, 2);

                            }


                        }).start();


                        //把你上面那段网络访问的代码放在这里


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        });


        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                hideInputKeyboard(edtSearch);
                refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
                try {

                    final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/usermanager/table_page_fy_order/data";//发送路径

                    InputStream inStream = null;
                    try {
                        inStream = getResources().getAssets().open("zydz.xml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//        InputStream inStream = this.getClass().getClassLoader()
//                .getResourceAsStream("zydz.xml");//加载本地XML文件
                    final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras><para><name>PCount</name><sqldbtype>BigInt</sqldbtype><value>0</value></para><para><name>RCount</name><sqldbtype>BigInt</sqldbtype><value>0</value></para><para><name>sys_Table</name><sqldbtype>NVarChar</sqldbtype><value>view_table_list_release</value></para><para><name>sys_Key</name><sqldbtype>VarChar</sqldbtype><value>tableid</value></para><para><name>sys_Fields</name><sqldbtype>NVarChar</sqldbtype><value>tableid,dbname,tablename,tablecname,tablecode,abstract,src_dw,theme,name,operationdatetime,org_name,theme_name,table_rows</value></para><para><name>sys_Where</name><sqldbtype>NVarChar</sqldbtype><value>1=1" + "and" + " dbname=" + "'" + mess + "'" + "</value></para><para><name>sys_Order</name><sqldbtype>NVarChar</sqldbtype><value>tablename DESC</value></para><para><name>sys_PageIndex</name><sqldbtype>Int</sqldbtype><value>0</value></para><para><name>sys_PageSize</name><sqldbtype>Int</sqldbtype><value>50</value></para></paras>";

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            postXml(path, xml, 1);

                        }


                    }).start();


                    //把你上面那段网络访问的代码放在这里


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500/*,false*/);//传入false表示加载失败
            }
        });
        try {
            final StringBuffer sb = new StringBuffer();
            sb.append("<?xml version='1.0' encoding='utf-8' standalone='yes' ?>");
            sb.append("<paras>");

            sb.append("<para>");

            sb.append("<name>");
            sb.append("PCount");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("BigInt");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append(0);
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("RCount");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("BigInt");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append(0);
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_Table");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("NVarChar");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append("view_table_list_release");
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_Key");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("VarChar");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append("tableid");
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_Fields");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("NVarChar");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append("tableid,dbname,tablename,tablecname,tablecode,abstract,src_dw,theme,name,operationdatetime,org_name,theme_name,table_rows");
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_Where");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("NVarChar");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append("1=1 and " + "'" + mess + "'");
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_Order");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("NVarChar");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append("tablename DESC");
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_PageIndex");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("Int");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append(0);
            sb.append("</value>");


            sb.append("</para>");
            sb.append("<para>");

            sb.append("<name>");
            sb.append("sys_PageSize");
            sb.append("</name>");

            sb.append("<sqldbtype>");
            sb.append("Int");
            sb.append("</sqldbtype>");

            sb.append("<value>");
            sb.append(50);
            sb.append("</value>");


            sb.append("</para>");


            sb.append("</paras>");
            final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/usermanager/table_page_fy_order/data";//发送路径

            InputStream inStream = null;
            try {
                inStream = getResources().getAssets().open("zydz.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
//        InputStream inStream = this.getClass().getClassLoader()
//                .getResourceAsStream("zydz.xml");//加载本地XML文件
            final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras><para><name>PCount</name><sqldbtype>BigInt</sqldbtype><value>0</value></para><para><name>RCount</name><sqldbtype>BigInt</sqldbtype><value>0</value></para><para><name>sys_Table</name><sqldbtype>NVarChar</sqldbtype><value>view_table_list_release</value></para><para><name>sys_Key</name><sqldbtype>VarChar</sqldbtype><value>tableid</value></para><para><name>sys_Fields</name><sqldbtype>NVarChar</sqldbtype><value>tableid,dbname,tablename,tablecname,tablecode,abstract,src_dw,theme,name,operationdatetime,org_name,theme_name,table_rows</value></para><para><name>sys_Where</name><sqldbtype>NVarChar</sqldbtype><value>1=1" + "and" + " dbname=" + "'" + mess + "'" + "</value></para><para><name>sys_Order</name><sqldbtype>NVarChar</sqldbtype><value>tablename DESC</value></para><para><name>sys_PageIndex</name><sqldbtype>Int</sqldbtype><value>0</value></para><para><name>sys_PageSize</name><sqldbtype>Int</sqldbtype><value>50</value></para></paras>";

            new Thread(new Runnable() {
                @Override
                public void run() {

                    postXml(path, xml, 1);

                }


            }).start();


            //把你上面那段网络访问的代码放在这里


        } catch (Exception e) {
            e.printStackTrace();
        }
//        setListener();
        initDatas();
        init(view);
        return view;
    }

    private void initDatas() {
        pieBeanList = new ArrayList<>();
        pieBeanList.add(new ChartPieBean(90, "押金使用", R.color.main_green));
        pieBeanList.add(new ChartPieBean(501f, "天猫购物", R.color.blue_rgba_24_261_255));
        pieBeanList.add(new ChartPieBean(800, "话费充值", R.color.orange));
        pieBeanList.add(new ChartPieBean(1000, "生活缴费", R.color.red_2));
        pieBeanList.add(new ChartPieBean(2300, "早餐", R.color.progress_color_default));
    }

    private void init(View view) {
        tvX = (TextView) view.findViewById(R.id.tvXMax);
        tvY = (TextView) view.findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) view.findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) view.findViewById(R.id.seekBar2);

        mChart = (BarChart) view.findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);

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

        mTf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");

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
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });


        // setting data
        mSeekBarY.setProgress(50);
        mSeekBarX.setProgress(12);

        mSeekBarY.setOnSeekBarChangeListener(this);
        mSeekBarX.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress() + 1));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress() + 1, mSeekBarY.getProgress());
        mChart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    private void setData(int count, float range) {

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

    @SuppressLint("NewApi")
    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;

        RectF bounds = mChart.getBarBounds((BarEntry) e);
        PointF position = mChart.getPosition(e, YAxis.AxisDependency.LEFT);

        Log.i("bounds", bounds.toString());
        Log.i("position", position.toString());

        Log.i("x-index",
                "low: " + mChart.getLowestVisibleXIndex() + ", high: "
                        + mChart.getHighestVisibleXIndex());
    }

    public void onNothingSelected() {
    }

    ;

    /**
     * 设置监听
     */
    private void setListener() {
        //edittext的监听
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //每次edittext内容改变时执行 控制删除按钮的显示隐藏
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
                //匹配文字 变色
//                doChangeColor(editable.toString().trim());
            }
        });

        //删除按钮的监听
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearch.setText("");
            }
        });
    }


    protected void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private void initRecycleView(int i) {

        recyclerView = view.findViewById(R.id.recyclerview);
        recycleAdapter = new MyRecyclerAdapter(getActivity(), null, null, i);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //TODO 下划线
//        recyclerView.addItemDecoration(new DividerItemD
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
        private String text;
        private List<String> mDatas;
        private List<String> mDatas1;
        private Context mContext;
        private LayoutInflater inflater;
        private int i1 = 0;
        private List<String> list = new ArrayList<>();

        public MyRecyclerAdapter(Context context, List<String> datas, List<String> datas1, int i) {
            this.mContext = context;
            this.mDatas = datas;
            this.mDatas1 = datas1;
            i1 = i;

            inflater = LayoutInflater.from(mContext);
        }

        public MyRecyclerAdapter(Context context, List<String> list) {
            this.mContext = context;
            this.list = list;


        }

        @Override
        public int getItemCount() {

            return wholeList.size();
        }

        /**
         * 在MainActivity中设置text
         */
        public void setText(String text) {
            this.text = text;
        }


        //填充onCreateViewHolder方法返回的holder中的控件
        @Override
        public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, final int position) {
//            holder.tvDate.setText(mDatas1.get(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    android.support.v4.app.FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft2.replace(R.id.ll_container, new ServiceFragment());
//                    ft2.setCustomAnimations(
//                            R.anim.fragment_slide_left_enter,
//                            R.anim.fragment_slide_left_exit);
//                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft2.addToBackStack(null);
//                    ft2.commit();
                }
            });

            /**如果没有进行搜索操作或者搜索之后点击了删除按钮 我们会在MainActivity中把text置空并传递过来*/
            if (text != null) {
                //设置span
                SpannableString string = matcherSearchText(Color.rgb(0, 0, 255), wholeList.get(position), text);
                holder.tvName.setText(string);
            } else {
                if (i1 == 1) {
                    holder.tvName.setText(wholeList.get(position));
                } else {
                    holder.tvName.setText(wholeList.get(position));
                }
            }
            //属性动画
//        animator = AnimatorInflater.loadAnimator(context, R.animator.anim_set);
//        animator.setTarget(holder.mLlItem);
//        animator.start();
            //点击监听
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.item_zyml, parent, false);
            MyRecyclerAdapter.MyViewHolder holder = new MyRecyclerAdapter.MyViewHolder(view);
            return holder;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tvDate;

            public MyViewHolder(View view) {
                super(view);

                tvName = view.findViewById(R.id.tv_title);
            }

        }

        /**
         * 正则匹配 返回值是一个SpannableString 即经过变色处理的数据
         */
        private SpannableString matcherSearchText(int color, String text, String keyword) {
            SpannableString spannableString = new SpannableString(text);
            //条件 keyword
            Pattern pattern = Pattern.compile(keyword);
            //匹配
            Matcher matcher = pattern.matcher(spannableString);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                //ForegroundColorSpan 需要new 不然也只能是部分变色
                spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //返回变色处理的结果
            return spannableString;
        }
    }


    public String postXml(String url, String xmlStr, int i) {
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


                wholeList = new ArrayList<>();

                resultBean = gson.fromJson(jsonString, ZYGLBean.class);
                if ("济宁市法人库".equals(title)) {

                    for (int g = 0; g < 2; g++) {
                        if (resultBean.getData().size() > g) {
                            if (mList.size() != 4) {
                                mList.add("" + resultBean.getData().get(g).getTable_rows());
                                mListCount.add(resultBean.getData().get(g).getTablecname());
                            }
                        } else {


                        }
                    }
                } else if ("电子证照".equals(title)) {
                    for (int g = 41; g < 44; g++) {
                        if (resultBean.getData().size() > g) {
                            if (mList.size() != 4) {
                                mList.add("" + resultBean.getData().get(g).getTable_rows());
                                mListCount.add(resultBean.getData().get(g).getTablecname());
                            }
                        } else {


                        }
                    }
                } else {
                    for (int g = 0; g < 6; g++) {
                        if (resultBean.getData().size() > g) {
                            if (resultBean.getData().get(g).getTablecname().length() <= 6) {
                                if (mList.size() != 4) {
                                    mList.add("" + resultBean.getData().get(g).getTable_rows());
                                    mListCount.add(resultBean.getData().get(g).getTablecname());
                                }
                            } else {

                            }


                        } else {

                        }
                    }

                }

                Log.e("mlist11", "" + wholeList);
                //假数据  实际开发中请从网络或者数据库获取
                Log.e("mlist22", "" + mList);
                Message msg = new Message();
                msg.what = 1;

                handler.sendMessage(msg);//用activity中的handler发送消息
                return jsonString;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "";


    }

    /**
     * 字体匹配方法
     */
    private void doChangeColor(String text) {
        //clear是必须的 不然只要改变edittext数据，list会一直add数据进来
        wholeList.clear();
        //不需要匹配 把所有数据都传进来 不需要变色
        if (text.equals("")) {
            wholeList.addAll(mList);
            //防止匹配过文字之后点击删除按钮 字体仍然变色的问题
            recycleAdapter.setText(null);

        } else {
            //如果edittext里面有数据 则根据edittext里面的数据进行匹配 用contains判断是否包含该条数据 包含的话则加入到list中
            for (String i : mList) {
                if (i.contains(text)) {
                    wholeList.add(i);
                }
            }
            //设置要变色的关键字
            recycleAdapter.setText(text);

        }
    }


    //    private void setData(int count, float range) {
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        ArrayList<String> xVals = new ArrayList<String>();
//
//
//
//        BarDataSet set1 = new BarDataSet(yVals1, "DataSet 1");
//
//        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//        dataSets.add(set1);
//
//        BarData data = new BarData(xVals, dataSets);
//        data.setValueTextSize(10f);
//        data.setValueTypeface(tf);
//
//        mChart.setData(data);
//    }


    ;
}
