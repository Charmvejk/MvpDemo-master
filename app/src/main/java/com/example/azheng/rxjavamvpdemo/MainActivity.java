package com.example.azheng.rxjavamvpdemo;


import android.Manifest;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.azheng.rxjavamvpdemo.activity.PicDeatilsActivity;
import com.example.azheng.rxjavamvpdemo.activity.SelectPeopleActivity;
import com.example.azheng.rxjavamvpdemo.activity.carDetals.CarDetailsActivity;
import com.example.azheng.rxjavamvpdemo.activity.ft.SendPostActivity;
import com.example.azheng.rxjavamvpdemo.base.BaseActivity;
import com.example.azheng.rxjavamvpdemo.bean.ZYGLBean;
import com.example.azheng.rxjavamvpdemo.bean.numBean;
import com.example.azheng.rxjavamvpdemo.fragment.Resource2Fragment;
import com.example.azheng.rxjavamvpdemo.fragment.ResourceFragment;
import com.example.azheng.rxjavamvpdemo.net.RetrofitClient;

import com.example.azheng.rxjavamvpdemo.util.PhotoPopupWindow;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends BaseActivity implements View.OnClickListener, OnBannerListener {

    private numBean resultBean;
    private TextView tvAddPeople;
    private Banner banner;
    private LinearLayout llZiYuan;
    private LinearLayout llZiYuan2;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;
    private ImageView ivLiked;
    private File deviceFile;//生成的file文件
    private AppCompatButton btnUpload;
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recycleAdapter;
    private List<String> mDatas = new ArrayList<>();
    private List<String> mDatasPlace = new ArrayList<>();
    private static final int[] COLOR_STR = {R.color.good5, R.color.good2, R.color.good3, R.color.good4, R.color.good1};
    private static final int[] img = {R.drawable.icon_count, R.drawable.icon_zydl, R.drawable.icon_zyxl, R.drawable.icon_sjx, R.drawable.icon_fw};

    private List<String> mList = new ArrayList<>();
    @BindView(R.id.lin_one)
    LinearLayout lintonOne;
    @BindView(R.id.iv_select_one)
    ImageView ivBg1;
    @BindView(R.id.iv_select_thre1)
    ImageView ivBg2;
    @BindView(R.id.iv_select_three)
    ImageView ivBg3;
    @BindView(R.id.lin_three)
    LinearLayout lintonThree;
    @BindView(R.id.lin_four)
    LinearLayout lintonFour;
    PagerAdapter adapter;

    @BindView(R.id.tv_main)
    TextView tvMain;

    private List<View> mViews = new ArrayList<>();
    @BindView(R.id.tv_mthree)
    TextView tvmy;

    @BindView(R.id.tv_set)
    TextView tvset;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<String> mListUrl = new ArrayList<>();
    private LinearLayout llContent;
    private LinearLayout llC;


    private FrameLayout mainFrame;
    private BottomNavigationView bottomNavigation;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    //定义一个变量，来标识是否退出
    private static int isExit = 0;
    private ImageView main_icon;
    private PhotoPopupWindow mPhotoPopupWindow;
    private Uri mImageUri;
    //实现按两次后退才退出
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit--;
        }
    };

    @Override
    public void initView() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.fragment_chat, null);

        bottomNavigation = (BottomNavigationView) findViewById(R.id.bnv_menu);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        bottomNavigation.setSelectedItemId(bottomNavigation.getMenu().getItem(1).getItemId());
        View view2 = inflater.inflate(R.layout.fragment_contact, null);

        RecyclerView rv = view2.findViewById(R.id.rl_vc);
        ImageView iv = view2.findViewById(R.id.iv_theme);
        Glide.with(MainActivity.this).load(R.mipmap.icon_top). apply(bitmapTransform(new CenterCrop()))
                .into(iv);

        rv.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        rv.setAdapter(new GridAdapter(MainActivity.this));
        CardView cardView = view2.findViewById(R.id.cv_content1);
        View view3 = inflater.inflate(R.layout.fragment_my, null);
        mViews.add(view1);
        mViews.add(view2);

        mViews.add(view3);

        //todo 人员添加
        tvAddPeople = view1.findViewById(R.id.person_add);
        banner = view1.findViewById(R.id.banner1);
        llZiYuan = view1.findViewById(R.id.ll_ziyaun);
        llZiYuan2 = view3.findViewById(R.id.ll_ziyaun2);
        tvAddPeople.setOnClickListener(this);
        ivLiked = view2.findViewById(R.id.iv_ardently);
        btnUpload = view2.findViewById(R.id.btn_upload);
        view2.findViewById(R.id.floatbutton).setOnClickListener(this);
        initRecycleView(view2);
        llContent = view1.findViewById(R.id.ll_top);
        llC = view3.findViewById(R.id.ll_top2);

        RefreshLayout refreshLayout = (RefreshLayout) view2.findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
            }
        });
        llZiYuan2.setOnClickListener(this);
        ivLiked.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        llZiYuan.setOnClickListener(this);
        //todo 我的页面

        main_icon = view3.findViewById(R.id.iv_head);
        TextView tvUserName = view3.findViewById(R.id.tv_user);
        tvUserName.setText(getIntent().getStringExtra("name"));
        main_icon.setOnClickListener(this);
        view3.findViewById(R.id.rl_generate).setOnClickListener(this);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.no_avatar)
//                .circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);

        Glide.with(this).load("http://119.188.171.201:38080/query_pic")
                .apply(requestOptions)
                .into(main_icon);
        //轮播
//        setBanner();
        int netMobile = NetUtil.getNetWorkState(MainActivity.this);

//        if (netMobile == 1) {
//            Toast.makeText(MainActivity.this, "网络类型-WIFI", Toast.LENGTH_SHORT).show();
//
//        } else if (netMobile == 0) {
//            Toast.makeText(MainActivity.this, "网络类型-移动数据", Toast.LENGTH_SHORT).show();
//
//        } else if (netMobile == -1) {
//            Toast.makeText(MainActivity.this, "当前没有网络", Toast.LENGTH_SHORT).show();
//        }
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?><paras><para><name>dbname</name><sqldbtype>VarChar</sqldbtype><value>DZZZ</value></para></paras>";
        final String path = "http://115.28.9.164:9001/postparas2jsonstream/username/pwd/usermanager/db_regist_dest_name/data";//发送路径

        new Thread(new Runnable() {
            @Override
            public void run() {

                postXml(path, xml, 2);

            }


        }).start();

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                resetImg();
                switch (position) {
                    case 0:
                        mViewPager.setCurrentItem(0);

                        ivBg1.setImageResource(R.mipmap.icon_zy_select);
                        tvMain.setTextColor(Color.parseColor("#1296db"));
                        break;

                    case 1:
//                        mViewPager.setCurrentItem(1);
//                        tvmy.setTextColor(Color.parseColor("#fd7304"));
//                        ivBg2.setImageResource(R.mipmap.tab_message_item_focus);

                        mViewPager.setCurrentItem(1);
                        ivBg2.setImageResource(R.mipmap.icon_main_select);
                        tvmy.setTextColor(Color.parseColor("#1296db"));
                        break;
                    case 2:
                        mViewPager.setCurrentItem(2);
                        ivBg3.setImageResource(R.mipmap.icon_tongji_select);
                        tvset.setTextColor(Color.parseColor("#1296db"));

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViews.get(position);

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViews.get(position));
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(1);
        ivBg2.setImageResource(R.mipmap.icon_main_select);
        tvmy.setTextColor(Color.parseColor("#1296db"));
    }

    private void initRecycleView(View view2) {
        recyclerView = view2.findViewById(R.id.recyclerview);
        recycleAdapter = new MyRecyclerAdapter(MainActivity.this, mDatas, mDatasPlace);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //TODO 下划线
//        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    //这里因为需要对3个fragment进行切换
                    //start
                    resetImg();
                    tvMain.setTextColor(Color.parseColor("#1296db"));
                    mViewPager.setCurrentItem(0);

                    ivBg1.setImageResource(R.mipmap.icon_zy_select);
                    //end
                    //如果只是想测试按钮点击，不管fragment的切换，可以把start到end里面的内容去掉
                    return true;
                case R.id.action_explore:
                    resetImg();
                    mViewPager.setCurrentItem(1);
                    tvmy.setTextColor(Color.parseColor("#1296db"));
                    ivBg2.setImageResource(R.mipmap.icon_main_select);
                    return true;
                case R.id.action_me:
                    resetImg();
                    mViewPager.setCurrentItem(2);
                    tvset.setTextColor(Color.parseColor("#1296db"));
                    ivBg3.setImageResource(R.mipmap.icon_tongji_select);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };
//    private void setBanner() {
//
//        //放图片地址的集合
//        list_path = new ArrayList<>();
//        //放标题的集合
//        list_title = new ArrayList<>();
//        list_path.add("https://ae01.alicdn.com/kf/Hcda8579bc7574201a94b708680011d0aW.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/Hdb2933629b804d5c98c915bff6a2054cA.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/H56d58b6dc72045c98928a8ca3249b26c0.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/H53479b6789764146b0aec014865054d4W.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/Hd87d133e3dd641e69e8038a3056dec34z.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/H30a7b5eb45664a7d94a9d8cac183b2a52.jpg");
//        list_path.add("https://ae01.alicdn.com/kf/H750924d16c8a496989975b86e53554a03.jpg");
//
//        list_title.add("兰博基尼");
//        list_title.add("法拉利");
//        list_title.add("玛莎拉蒂");
//        list_title.add("奔驰");
//        list_title.add("劳斯莱斯");
//        list_title.add("保时捷");
//        list_title.add("宾利");
//        mListUrl.add("https://baike.baidu.com/item/%E5%85%B0%E5%8D%9A%E5%9F%BA%E5%B0%BC/246705?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E7%8E%9B%E8%8E%8E%E6%8B%89%E8%92%82/44776?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E7%8E%9B%E8%8E%8E%E6%8B%89%E8%92%82/44776?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E5%A5%94%E9%A9%B0/1064?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E5%8A%B3%E6%96%AF%E8%8E%B1%E6%96%AF/65611?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E4%BF%9D%E6%97%B6%E6%8D%B7/177110?fr=aladdin");
//        mListUrl.add("https://baike.baidu.com/item/%E5%AE%BE%E5%88%A9/200195?fr=aladdin");
//
//        //设置内置样式，共有六种可以点入方法内逐一体验使用。
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//        //设置图片加载器，图片加载器在下方
//        banner.setImageLoader(new MyLoader());
//        //设置图片网址或地址的集合
//        banner.setImages(list_path);
//        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
//        banner.setBannerAnimation(Transformer.Default);
//        //设置轮播图的标题集合
//        banner.setBannerTitles(list_title);
//        //设置轮播间隔时间
//        banner.setDelayTime(3000);
//        //设置是否为自动轮播，默认是“是”。
//        banner.isAutoPlay(true);
//        //设置指示器的位置，小点点，左中右。
//        banner.setIndicatorGravity(BannerConfig.CENTER)
//                //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
//                .setOnBannerListener(this)
//                //必须最后调用的方法，启动轮播图。
//                .start();
//
//
//    }

    private class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        private List<String> mDatas;
        private List<String> mDatas1;
        private Context mContext;
        private LayoutInflater inflater;

        public MyRecyclerAdapter(Context context, List<String> datas, List<String> datas1) {
            this.mContext = context;
            this.mDatas = datas;
            this.mDatas1 = datas1;

            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getItemCount() {

            return 1;
        }

        //填充onCreateViewHolder方法返回的holder中的控件
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
//            holder.tvDate.setText(mDatas1.get(position));
//            holder.tvName.setText(mDatas.get(position));
        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.item_chat, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tvDate;

            public MyViewHolder(View view) {
                super(view);

            }

        }
    }


    //轮播图的监听方法
    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(MainActivity.this, CarDetailsActivity.class);
        intent.putExtra("mList", mListUrl.get(position));
        intent.putExtra("mListtitle", list_title.get(position));
        startActivity(intent);
    }


    //自定义的图片加载器
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        super.onNetChange(netMobile);
        //网络状态变化时的操作
        if (netMobile == NetUtil.NETWORK_NONE) {
            Toast.makeText(MainActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "网络连接正常", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.lin_one, R.id.lin_three1, R.id.lin_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.lin_one:
                resetImg();
                tvMain.setTextColor(Color.parseColor("#1296db"));
                mViewPager.setCurrentItem(0);

                ivBg1.setImageResource(R.mipmap.icon_zy_select);

                break;

            case R.id.lin_three1:
                resetImg();
                mViewPager.setCurrentItem(1);
                tvmy.setTextColor(Color.parseColor("#1296db"));
                ivBg2.setImageResource(R.mipmap.icon_main_select);
                break;
            case R.id.lin_four:
                resetImg();
                mViewPager.setCurrentItem(2);
                tvset.setTextColor(Color.parseColor("#1296db"));
                ivBg3.setImageResource(R.mipmap.icon_tongji_select);

                break;
        }


    }


    //重置TAB的字体颜色（也可以变化图片）
    private void resetImg() {
        tvMain.setTextColor(Color.parseColor("#cdcdcd"));
        tvmy.setTextColor(Color.parseColor("#cdcdcd"));
        tvset.setTextColor(Color.parseColor("#cdcdcd"));

        ivBg1.setImageResource(R.mipmap.icon_zy_unselect);
        ivBg2.setImageResource(R.mipmap.icon_main_unselect);
        ivBg3.setImageResource(R.mipmap.icon_tongji_unselect);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.person_add:
                startActivity(new Intent(MainActivity.this, SelectPeopleActivity.class));
                break;
            case R.id.iv_head:
                mPhotoPopupWindow = new PhotoPopupWindow(MainActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 权限申请
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //权限还没有授予，需要在这里写申请权限的代码
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
                        } else {
                            // 如果权限已经申请过，直接进行图片选择
                            mPhotoPopupWindow.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            // 判断系统中是否有处理该 Intent 的 Activity
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                startActivityForResult(intent, REQUEST_IMAGE_GET);
                            } else {
                                Toast.makeText(MainActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 权限申请
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                                || ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // 权限还没有授予，需要在这里写申请权限的代码
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 300);
                        } else {
                            // 权限已经申请，直接拍照
                            mPhotoPopupWindow.dismiss();
                            imageCapture();
                        }
                    }
                });
                View rootView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
            case R.id.btn_upload:
                //上传
                if (deviceFile == null) {
                    return;
                }
            case R.id.rl_generate:
                startActivity(new Intent(getApplicationContext(), PicDeatilsActivity.class));
                break;
            case R.id.floatbutton:
                startActivity(new Intent(getApplicationContext(), SendPostActivity.class));
                break;
            case R.id.ll_ziyaun:
                llContent.setVisibility(View.INVISIBLE);
                android.support.v4.app.FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.ll_container, new ResourceFragment());
                ft2.setCustomAnimations(
                        R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit);
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();


                break;
            case R.id.ll_ziyaun2:
                llC.setVisibility(View.INVISIBLE);
                android.support.v4.app.FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.ll_container2, new Resource2Fragment());
                ft3.setCustomAnimations(
                        R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit);
                ft3.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft3.addToBackStack(null);
                ft3.commit();


                break;

        }

    }

    public void showToast(String message) {
        llContent.setVisibility(View.VISIBLE);
        llC.setVisibility(View.VISIBLE);
    }

    // 请求权限后会在这里回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // 判断系统中是否有处理该 Intent 的 Activity
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_GET);
                    } else {
                        Toast.makeText(MainActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
            case 300:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPhotoPopupWindow.dismiss();
                    imageCapture();
                } else {
                    mPhotoPopupWindow.dismiss();
                }
                break;
        }

    }

    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(File inputFile) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(MainActivity.this, inputFile), "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File(storage + "/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
            mImageUri = imageUri; // 将 uri 传出，方便设置到视图中
        }

        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }

    public Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /**
     * 小图模式中，保存图片后，设置到视图中
     * 将图片保存设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File(storage + "/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
                File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
                // 保存图片
                FileOutputStream outputStream;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String path = Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME;
            deviceFile = saveBitmapFile(photo, path);
            // 在视图中显示图片
            main_icon.setImageBitmap(photo);
            upload(deviceFile);
        }
    }

    /*相机或者相册返回来的数据*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
                // 大图切割
                case REQUEST_BIG_IMAGE_CUTTING:
                    Bitmap bitmap = BitmapFactory.decodeFile(mImageUri.getEncodedPath());
                    String path = Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME;
                    deviceFile = saveBitmapFile(bitmap, path);
                    main_icon.setImageBitmap(bitmap);
                    upload(deviceFile);
                    break;
                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        // startSmallPhotoZoom(data.getData());
                        startBigPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    // startSmallPhotoZoom(Uri.fromFile(temp));
                    startBigPhotoZoom(temp);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 把batmap 转file
     *
     * @param bitmap
     * @param filepath
     */
    public static File saveBitmapFile(Bitmap bitmap, String filepath) {
        File file = new File(filepath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private void upload(File deviceFile) {

//        创建 RequestBody，用于封装构建RequestBody #默认文件类型
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/octet-stream"), deviceFile);
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), "单文件上传");

//        MultipartBody.Part 和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("name", deviceFile.getName(), requestFile);

//        执行请求
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().upload(description, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {

                Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    /**
     * 判断系统及拍照
     */
    private void imageCapture() {
        Intent intent;
        Uri pictureUri;
        File pictureFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        // 判断当前系统
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pictureUri = FileProvider.getUriForFile(this,
                    "com.example.azheng.rxjavamvpdemo", pictureFile);
        } else {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            pictureUri = Uri.fromFile(pictureFile);
        }
        // 去拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {
        private Context context;

        public GridAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public GridAdapter.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_rule, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull GridAdapter.GridViewHolder holder, final int position) {
//            holder.tv.setText("Hello world");
            mList.add("汇聚总量");
            mList.add("资源大类");
            mList.add("资源小类");
            mList.add("数据项");
            mList.add("服务总量");
            holder.tvType.setText(mList.get(position));
            holder.tv.setCardBackgroundColor(getResources().getColor(COLOR_STR[position]));
            Glide.with(MainActivity.this).load(img[position]).into(holder.img);

            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "click..." + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        class GridViewHolder extends RecyclerView.ViewHolder {

            private CardView tv;
            private ImageView img;
            private TextView tvType;

            public GridViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.cv_content1);
                img = itemView.findViewById(R.id.iv_cover1);
                tvType = itemView.findViewById(R.id.tv_count);
            }
        }
    }

    public String postXml(String url, String xmlStr, int i) {
        RequestBody body = RequestBody.create(MediaType.parse("charset=UTF-8"), xmlStr);
        Request requestOk = new Request.Builder()
                .url(url)

                .post(body)
                .build();

        okhttp3.Response response;
        try {
            response = new OkHttpClient().newCall(requestOk).execute();
            String jsonString = response.body().string();
            mList.clear();
            if (response.isSuccessful()) {
                Gson gson = new Gson();

                resultBean = gson.fromJson(jsonString, numBean.class);


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

}





