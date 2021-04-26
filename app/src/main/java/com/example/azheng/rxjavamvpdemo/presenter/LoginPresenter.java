package com.example.azheng.rxjavamvpdemo.presenter;

import com.example.azheng.rxjavamvpdemo.base.BasePresenter;
import com.example.azheng.rxjavamvpdemo.bean.BaseLoginBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;
import com.example.azheng.rxjavamvpdemo.contract.LoginContract;
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
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private MainContract.Model model;

    public LoginPresenter() {
        model = new MainModel();
    }

    @Override
    public void loginPersonInfo(String user) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.loginUser(user)
                .compose(RxScheduler.<BaseLoginBean<LoginBean>>Flo_io_main())
                .as(mView.<BaseLoginBean<LoginBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseLoginBean<LoginBean>>() {
                    @Override
                    public void accept(BaseLoginBean<LoginBean> bean) throws Exception {
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
