package com.hero.angel.result;

import java.io.Serializable;

/**
 * 返回结果Bean
 */
public class ResultBean implements Serializable {

    /**
     * 定义常量
     */
    private static final int SUCCESS = 200;
    private static final int FAIL = 0;

    private String msg = "success";
    private int status = SUCCESS;

    private Object data;


    /*成功构造器*/
    public ResultBean ok() {
        return new ResultBean();
    }

    public ResultBean ok(Object data) {
        return new ResultBean(data);
    }


    /*失败构造器*/
    public ResultBean build() {
        return new ResultBean(FAIL,null, null);
    }

    public ResultBean build(String msg) {
        return new ResultBean(FAIL, msg, null);
    }

    /**构造器**/
    public ResultBean() {
    }

    public ResultBean(Object data) {
        this.data = data;
    }

    public ResultBean(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public ResultBean(int status, String msg, Object data) {
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    /**Setter/Getter**/

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
