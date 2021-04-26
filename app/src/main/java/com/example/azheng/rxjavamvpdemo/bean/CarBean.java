package com.example.azheng.rxjavamvpdemo.bean;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class CarBean {

    /**
     * data : [{"id":1,"age":"23","name":"weishuyong"},{"id":2,"age":"24","name":"weishuxiang "},{"id":3,"age":"25","name":"22"},{"id":4,"age":"26","name":"22"},{"id":5,"age":"12","name":"weishuyong"},{"id":6,"age":"22","name":"liyuxiang"},{"id":7,"age":"22","name":"liyuxiang"},{"id":8,"age":"22","name":"liyuxiang"},{"id":9,"age":"22","name":"liyuxiang"},{"id":10,"age":"22","name":"liyuxiang"},{"id":11,"age":"22","name":"liyuxiang"}]
     * success : true
     * code : 200
     * message : 返回成功
     */

    private boolean success;
    private int code;
    private String message;
    private List<DataBean> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * age : 23
         * name : weishuyong
         */

        private int id;
        private String age;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
