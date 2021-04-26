package com.example.azheng.rxjavamvpdemo.model;


import com.example.azheng.rxjavamvpdemo.bean.BaseObjectBean;
import com.example.azheng.rxjavamvpdemo.bean.BaseLoginBean;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;
import com.example.azheng.rxjavamvpdemo.bean.UrlBean;
import com.example.azheng.rxjavamvpdemo.contract.MainContract;
import com.example.azheng.rxjavamvpdemo.net.RetrofitClient;

import io.reactivex.Flowable;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public class MainModel implements MainContract.Model {
    @Override
    public Flowable<BaseObjectBean<CarBean>> paoCar() {
        return RetrofitClient.getInstance().getApi().paoCar();
    }

    public Flowable<BaseObjectBean<CarBean>> yueyeCar() {
        return RetrofitClient.getInstance().getApi().yueyeCar();
    }

    public Flowable<BaseLoginBean<LoginBean>> loginUser(String user) {
        return RetrofitClient.getInstance().getApi().login(user);
    }
}
