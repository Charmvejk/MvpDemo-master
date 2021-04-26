package com.example.azheng.rxjavamvpdemo.bean;

import java.util.List;

/**
 * @author azheng
 * @date 2018/6/4.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：登陆
 */
public class ZYGLSCBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 5
         * name : 电子证照
         * memo : 各类证照、证明、批文、鉴定报告、办事结果等文件
         * src_type : sqlserver
         * src_ip : 59.206.96.138
         * src_port : 11433
         * src_db : DZZZ
         * src_user_name : sa
         * src_user_pwd : JNSdzzz!@2019
         * dest_type : sqlserver
         * dest_ip : 59.206.96.138
         * dest_port : 11433
         * dest_db : DZZZ
         * dest_user_name : sa
         * dest_user_pwd : JNSdzzz!@2019
         * addtime : 2019-08-16T11:07:46.973
         * updatetime : 2019-08-16T14:01:35.99
         * adduser : null
         * updateuser : null
         * src_dw : 55
         * theme : 1
         */

        private int id;
        private String name;
        private String memo;
        private String src_type;
        private String src_ip;
        private String src_port;
        private String src_db;
        private String src_user_name;
        private String src_user_pwd;
        private String dest_type;
        private String dest_ip;
        private String dest_port;
        private String dest_db;
        private String dest_user_name;
        private String dest_user_pwd;
        private String addtime;
        private String updatetime;
        private Object adduser;
        private Object updateuser;
        private String src_dw;
        private int theme;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getSrc_type() {
            return src_type;
        }

        public void setSrc_type(String src_type) {
            this.src_type = src_type;
        }

        public String getSrc_ip() {
            return src_ip;
        }

        public void setSrc_ip(String src_ip) {
            this.src_ip = src_ip;
        }

        public String getSrc_port() {
            return src_port;
        }

        public void setSrc_port(String src_port) {
            this.src_port = src_port;
        }

        public String getSrc_db() {
            return src_db;
        }

        public void setSrc_db(String src_db) {
            this.src_db = src_db;
        }

        public String getSrc_user_name() {
            return src_user_name;
        }

        public void setSrc_user_name(String src_user_name) {
            this.src_user_name = src_user_name;
        }

        public String getSrc_user_pwd() {
            return src_user_pwd;
        }

        public void setSrc_user_pwd(String src_user_pwd) {
            this.src_user_pwd = src_user_pwd;
        }

        public String getDest_type() {
            return dest_type;
        }

        public void setDest_type(String dest_type) {
            this.dest_type = dest_type;
        }

        public String getDest_ip() {
            return dest_ip;
        }

        public void setDest_ip(String dest_ip) {
            this.dest_ip = dest_ip;
        }

        public String getDest_port() {
            return dest_port;
        }

        public void setDest_port(String dest_port) {
            this.dest_port = dest_port;
        }

        public String getDest_db() {
            return dest_db;
        }

        public void setDest_db(String dest_db) {
            this.dest_db = dest_db;
        }

        public String getDest_user_name() {
            return dest_user_name;
        }

        public void setDest_user_name(String dest_user_name) {
            this.dest_user_name = dest_user_name;
        }

        public String getDest_user_pwd() {
            return dest_user_pwd;
        }

        public void setDest_user_pwd(String dest_user_pwd) {
            this.dest_user_pwd = dest_user_pwd;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public Object getAdduser() {
            return adduser;
        }

        public void setAdduser(Object adduser) {
            this.adduser = adduser;
        }

        public Object getUpdateuser() {
            return updateuser;
        }

        public void setUpdateuser(Object updateuser) {
            this.updateuser = updateuser;
        }

        public String getSrc_dw() {
            return src_dw;
        }

        public void setSrc_dw(String src_dw) {
            this.src_dw = src_dw;
        }

        public int getTheme() {
            return theme;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }
    }
}
