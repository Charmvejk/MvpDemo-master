package com.example.azheng.rxjavamvpdemo.bean;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class XNYBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * clientname : binzhou-hetian-jiuyijilu
         * systemname : 滨州和天就医记录
         * Name : 滨州市公安局
         * dbcname : 滨州医疗信息
         * tabcname : 滨州就医记录信息
         * rows_sum : 2603707
         */

        private String clientname;
        private String systemname;
        private String Name;
        private String dbcname;
        private String tabcname;
        private float rows_sum;

        public String getClientname() {
            return clientname;
        }

        public void setClientname(String clientname) {
            this.clientname = clientname;
        }

        public String getSystemname() {
            return systemname;
        }

        public void setSystemname(String systemname) {
            this.systemname = systemname;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getDbcname() {
            return dbcname;
        }

        public void setDbcname(String dbcname) {
            this.dbcname = dbcname;
        }

        public String getTabcname() {
            return tabcname;
        }

        public void setTabcname(String tabcname) {
            this.tabcname = tabcname;
        }

        public float getRows_sum() {
            return rows_sum;
        }

        public void setRows_sum(int rows_sum) {
            this.rows_sum = rows_sum;
        }
    }
}
