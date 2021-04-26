package com.example.azheng.rxjavamvpdemo.bean;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class ZYLBBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * dest_db : -1
         * name : 全部
         */

        private String dest_db;
        private String name;

        public String getDest_db() {
            return dest_db;
        }

        public void setDest_db(String dest_db) {
            this.dest_db = dest_db;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
