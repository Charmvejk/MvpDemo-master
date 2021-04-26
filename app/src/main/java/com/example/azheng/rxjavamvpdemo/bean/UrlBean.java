package com.example.azheng.rxjavamvpdemo.bean;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class UrlBean {

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * data : [{"id":1,"phone":"123"},{"id":2,"phone":"15194113739"},{"id":3,"phone":"15194113739"},{"id":4,"phone":"15194113739"},{"id":5,"phone":"151941137391"},{"id":6,"phone":"151941137391"}]
     * success : true
     * code : 200
     * message : 返回成功
     */

    private String url;

}
