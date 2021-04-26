package com.example.azheng.rxjavamvpdemo.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class ZYGLBean {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * temp_col_xh : 26
         * tableid : 72
         * dbname : JnsFrk
         * tablename : qydj_zxxx_zs
         * tablecname : 企业注销信息表
         * tablecode : 0
         * abstract :
         * src_dw : 58
         * theme : 6
         * name : 济宁市法人库
         * operationdatetime : 2019-08-08T14:38:36.44
         * org_name : null
         * theme_name : 价格监管
         * table_rows : 74400
         */

        private int temp_col_xh;
        private int tableid;
        private String dbname;
        private String tablename;
        private String tablecname;
        private int tablecode;
        @SerializedName("abstract")
        private String abstractX;
        private int src_dw;
        private int theme;
        private String name;
        private String operationdatetime;
        private Object org_name;
        private String theme_name;
        private int table_rows;

        public int getTemp_col_xh() {
            return temp_col_xh;
        }

        public void setTemp_col_xh(int temp_col_xh) {
            this.temp_col_xh = temp_col_xh;
        }

        public int getTableid() {
            return tableid;
        }

        public void setTableid(int tableid) {
            this.tableid = tableid;
        }

        public String getDbname() {
            return dbname;
        }

        public void setDbname(String dbname) {
            this.dbname = dbname;
        }

        public String getTablename() {
            return tablename;
        }

        public void setTablename(String tablename) {
            this.tablename = tablename;
        }

        public String getTablecname() {
            return tablecname;
        }

        public void setTablecname(String tablecname) {
            this.tablecname = tablecname;
        }

        public int getTablecode() {
            return tablecode;
        }

        public void setTablecode(int tablecode) {
            this.tablecode = tablecode;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public int getSrc_dw() {
            return src_dw;
        }

        public void setSrc_dw(int src_dw) {
            this.src_dw = src_dw;
        }

        public int getTheme() {
            return theme;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOperationdatetime() {
            return operationdatetime;
        }

        public void setOperationdatetime(String operationdatetime) {
            this.operationdatetime = operationdatetime;
        }

        public Object getOrg_name() {
            return org_name;
        }

        public void setOrg_name(Object org_name) {
            this.org_name = org_name;
        }

        public String getTheme_name() {
            return theme_name;
        }

        public void setTheme_name(String theme_name) {
            this.theme_name = theme_name;
        }

        public int getTable_rows() {
            return table_rows;
        }

        public void setTable_rows(int table_rows) {
            this.table_rows = table_rows;
        }
    }
}
