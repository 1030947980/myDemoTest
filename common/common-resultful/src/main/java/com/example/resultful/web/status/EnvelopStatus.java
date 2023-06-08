package com.example.resultful.web.status;

/**
 * Created by chenweida on 2018/5/8 0008.
 */
public enum EnvelopStatus {

    success("请求成功", 200),
    system_error("系统错误", -10000),
    //------------------登陆 权限相关 start ------------------
    status_10100("账号不存在", 10100),
    status_10101("密码错误", 10101),
    status_10102("用户未登录", 10102),
    status_10103("登陆超时", 10103),
    status_10104("账号被挤", 10104),
    status_10105("账号没权限", 10105),
    status_10106("账号已存在", 10106),;
    //------------------登陆 权限相关 end ------------------

    public String name;
    public Integer code;

    EnvelopStatus(String name, Integer value) {
        this.name = name;
        this.code = value;
    }

}
