package com.example.azheng.rxjavamvpdemo.net;


import android.util.ArrayMap;

import com.example.azheng.rxjavamvpdemo.bean.BaseObjectBean;
import com.example.azheng.rxjavamvpdemo.bean.BaseLoginBean;
import com.example.azheng.rxjavamvpdemo.bean.CarBean;
import com.example.azheng.rxjavamvpdemo.bean.LoginBean;
import com.example.azheng.rxjavamvpdemo.bean.UrlBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIService {

    /**
     * 登陆
     *
     * @param username 账号
     * @param password 密码
     * @return
     */
//    @FormUrlEncoded
//    @POST("zdyw/queryZdywList")
//    Flowable<BaseObjectBean<CarBean>> selectPersonInfo(@Field("id") String username,
//                                              @Field("currentPage") String password,
//                                              @Field("pageSize") String password1);

    /**
     * //     * POST请求
     * //
     *
     * @return
     */
//    @FormUrlEncoded
//    @POST("UserServlet")
//    Call<User> postUser(@Field("name") String name, @Field("email") String email);

//      * GET请求
//  */
    @GET("paocar")
    Flowable<BaseObjectBean<CarBean>> paoCar();

    @GET("yueyecar")
    Flowable<BaseObjectBean<CarBean>> yueyeCar();

    //    @Multipart
//    @POST("upload")
//    Call<ResponseBody> upload(@Part MultipartBody.Part file);
    //todo 单文件
    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("login")
    Flowable<BaseLoginBean<LoginBean>> login(@Field("phone") String user);

    //todo 多文件上传
    @Multipart
    @POST("uploadMore")
    Call<ResponseBody> uploadFiles(@Part() List<MultipartBody.Part> parts,@Part("content") RequestBody mContent,@Part("time") RequestBody time);


    //下载图片
//    @Streaming
    @GET
    Call<ResponseBody> downloadPicWithUrl(@Url String url);

}
