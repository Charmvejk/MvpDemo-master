package com.example.azheng.rxjavamvpdemo.contract;

import com.example.azheng.rxjavamvpdemo.base.BaseView;
import com.example.azheng.rxjavamvpdemo.bean.BaseObjectBean;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;

import io.reactivex.Flowable;


/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface LoginContract {
    interface Model {
        Flowable<BaseObjectBean<LoginBean>> loginUser(String user);

    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(LoginBean bean);
    }

    interface Presenter {

        void loginPersonInfo(String user);

    }
}
