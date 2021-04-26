package com.example.azheng.rxjavamvpdemo.contract;

import com.example.azheng.rxjavamvpdemo.base.BaseView;
import com.example.azheng.rxjavamvpdemo.bean.BaseObjectBean;
import com.example.azheng.rxjavamvpdemo.bean.BaseLoginBean;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;
import com.example.azheng.rxjavamvpdemo.bean.UrlBean;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.http.Url;


/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface MainContract {
    interface Model {
        Flowable<BaseObjectBean<CarBean>> paoCar( );
        Flowable<BaseObjectBean<CarBean>> yueyeCar( );
        Flowable<BaseLoginBean<LoginBean>> loginUser(String user );


    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        void onSuccess(CarBean bean);
    }

    interface Presenter {
        void selectPersonInfo( );
    }
}
