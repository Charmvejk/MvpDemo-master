package com.example.azheng.rxjavamvpdemo.fragment;

import android.animation.Animator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.tu.loadingdialog.LoadingDailog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.azheng.rxjavamvpdemo.MainActivity;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.activity.DividerItemDecoration;
import com.example.azheng.rxjavamvpdemo.bean.HomeItem;
import com.example.azheng.rxjavamvpdemo.bean.ZYGLBean;
import com.example.azheng.rxjavamvpdemo.bean.ZYGLSCBean;
import com.example.azheng.rxjavamvpdemo.bean.ZYLBBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import javax.xml.transform.Result;

import butterknife.internal.Utils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Utf8;

public class ResourceFragment extends Fragment {
    private View view;
    private TextView tvTitle;
    private RecyclerView rlView;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView recyclerView;
    private List<String> mList = new ArrayList<>();
    private ArrayList<HomeItem> mDataList;
    private ZYLBBean resultBean;
    private String mFinalKey;
    private List<String> mValue = new ArrayList<>();
    private static final String[] COLOR_STR = {"#0dddb8", "#0bd4c3", "#03cdcd", "#03cdcd", "#00b1c5", "#04b2d1", "#04b2d1", "#04b2d1"};

    public static ResourceFragment newInstance() {
        return new ResourceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.resource_fragment, container, false);
        tvTitle = view.findViewById(R.id.tv_title_toolbar);
//        initDatas();

        tvTitle.setText("资源分类");
        view.findViewById(R.id.iv_back_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.showToast("呵呵");
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });
//        smartRefreshLayout = view.findViewById(R.id.refreshLayout);
        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(500/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(500/*,false*/);//传入false表示加载失败
            }
        });
        try {

            final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/usermanager/db_regist_allname/data";//发送路径

            InputStream inStream = null;
            try {
                inStream = getResources().getAssets().open("zydz.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
//        InputStream inStream = this.getClass().getClassLoader()
//                .getResourceAsStream("zydz.xml");//加载本地XML文件
            final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras>  </paras>";

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


        return view;
    }


    private void initDatas() {
        mList.add("测试数据");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    private void initRecycleView() {
        recyclerView = view.findViewById(R.id.recyclerview);
        BaseQuickAdapter homeAdapter = new HomeAdapter(getActivity(), R.layout.item_zyml, mDataList);
        homeAdapter.openLoadAnimation();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(homeAdapter);
        //设置增加或删除条目的动画
//        homeAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(HomeActivity.this, ACTIVITY[position]);
//                startActivity(intent);
//            }
//        });
//        homeAdapter.setOnRecyclerViewItemLongClickListener(new BaseQuickAdapter.OnRecyclerViewItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(View view, int position) {
//                Toast.makeText(HomeActivity.this,"onItemLongClick",Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });
        //TODO 下划线
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));

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
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.showToast("呵呵");
                    getActivity().getSupportFragmentManager().popBackStack();
                    //Toast.makeText(getActivity(), "按了返回键", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });
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
            if (response.isSuccessful()) {
                Gson gson = new Gson();

                mDataList = new ArrayList<>();
                resultBean = gson.fromJson(jsonString, ZYLBBean.class);
                for (int g = 0; g < resultBean.getData().size(); g++) {
                    mList.add(resultBean.getData().get(g).getName());
                    mValue.add(resultBean.getData().get(g).getDest_db());
                    if ("全部".equals(mList.get(g)) || "122".equals(mList.get(g))) {

                    } else {
                        HomeItem item = new HomeItem();
                        item.setTitle(mList.get(g));
                        item.setKey(mValue.get(g));
                        //                    item.setActivity(ACTIVITY[i]);

                        if (g < COLOR_STR.length) {
                            item.setColorStr(COLOR_STR[g]);
                        } else {
                            item.setColorStr(COLOR_STR[2]);
                        }

//                    mDataList.add(item);

                        mDataList.add(item);
                    }
                }


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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initRecycleView();

                    break;

            }
        }
    };

    public class HomeAdapter extends BaseQuickAdapter<HomeItem> {
        public HomeAdapter(Context context, int layoutResId, List data) {
            super(context, layoutResId, data);
        }

        @Override
        protected void startAnim(Animator anim, int index) {
            super.startAnim(anim, index);
            if (index < 5)
                anim.setStartDelay(index * 150);
        }

        @Override
        protected void convert(BaseViewHolder helper, final HomeItem item) {

            helper.setText(R.id.tv_title, item.getTitle());
            helper.getView(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ServiceFragment fragment = new ServiceFragment();
                    android.support.v4.app.FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
                    ft2.replace(R.id.ll_container, fragment);
                    ft2.setCustomAnimations(
                            R.anim.fragment_slide_left_enter,
                            R.anim.fragment_slide_left_exit);
                    Bundle bundle = new Bundle();
                    bundle.putString("data", item.getKey());
                    bundle.putString("title", item.getTitle());
                    fragment.setArguments(bundle);//数据传递到fragment中
                    ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft2.addToBackStack(null);
                    ft2.commit();
                }
            });
            CardView cardView = helper.getView(R.id.card_view);
            cardView.setCardBackgroundColor(Color.parseColor(item.getColorStr()));
        }
    }
}
