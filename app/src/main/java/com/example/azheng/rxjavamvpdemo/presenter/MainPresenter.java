package com.example.azheng.rxjavamvpdemo.presenter;

import com.example.azheng.rxjavamvpdemo.base.BasePresenter;
import com.example.azheng.rxjavamvpdemo.bean.BaseObjectBean;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.contract.MainContract;
import com.example.azheng.rxjavamvpdemo.model.MainModel;
import com.example.azheng.rxjavamvpdemo.net.RxScheduler;

import io.reactivex.functions.Consumer;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private MainContract.Model model;

    public MainPresenter() {
        model = new MainModel();
    }

    @Override
    public void selectPersonInfo( ) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.paoCar()
                .compose(RxScheduler.<BaseObjectBean<CarBean>>Flo_io_main())
                .as(mView.<BaseObjectBean<CarBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseObjectBean<CarBean>>() {
                    @Override
                    public void accept(BaseObjectBean<CarBean> bean) throws Exception {
                        mView.onSuccess(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.showLoading();
                    }
                });
    }
 }
