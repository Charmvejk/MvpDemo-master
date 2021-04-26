package com.example.azheng.rxjavamvpdemo.bean;


/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：对象
 */
public class BaseObjectBean<T> extends CarBean {

    /**
     * status : 1
     * msg : 获取成功
     * result : {} 对象
     */


    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }


}
