package com.example.azheng.rxjavamvpdemo.fragment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDailog;
import com.example.azheng.rxjavamvpdemo.R;
import com.example.azheng.rxjavamvpdemo.activity.DividerItemDecoration;
import com.example.azheng.rxjavamvpdemo.base.BaseMvpFragment;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.contract.MainContract;
import com.example.azheng.rxjavamvpdemo.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class RunCarFragment extends BaseMvpFragment<MainPresenter> implements MainContract.View {
    private RecyclerView recyclerView;
    private MyRecyclerAdapter recycleAdapter;
    private List<String> mDatas = new ArrayList<>();
    private List<String> mDatasPlace = new ArrayList<>();
    LoadingDailog.Builder loadBuilder;
    LoadingDailog dialog;

    @Override
    protected void initView(View view) {


        recyclerView = view.findViewById(R.id.recyclerView);
        mPresenter = new MainPresenter();
        mPresenter.attachView(this);
        mPresenter.selectPersonInfo();
    }

    private void initRecycleView() {
        recycleAdapter = new MyRecyclerAdapter(getActivity(), mDatas, mDatasPlace);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //TODO 下划线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.run_car_fragment;
    }


    @Override
    public void onSuccess(CarBean bean) {
        for (int i = 0; i < bean.getData().size(); i++) {
            mDatas.add(bean.getData().get(i).getName());
            mDatasPlace.add(bean.getData().get(i).getAge());
        }
        initRecycleView();
    }

    @Override
    public void showLoading() {
        loadBuilder = new LoadingDailog.Builder(getActivity())
                .setMessage("加载中...")
                .setCancelable(true)
                .setCancelOutside(true);
        dialog = loadBuilder.create();
        dialog.show();
        //
//        ProgressDialog.getInstance().showText(getActivity(), "获取人员数据中...");
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }


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

            return mDatas.size();
        }

        //填充onCreateViewHolder方法返回的holder中的控件
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tvDate.setText(mDatas1.get(position));
            holder.tvName.setText(mDatas.get(position));
        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = inflater.inflate(R.layout.item_person, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;
            TextView tvDate;

            public MyViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tv_name);
                tvDate = (TextView) view.findViewById(R.id.tv_time);
            }

        }
    }

}
