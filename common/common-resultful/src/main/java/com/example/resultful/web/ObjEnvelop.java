package com.example.resultful.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;

/**
 * 信封对象，封装REST接口的返回值内容。包括：
 * - 页码
 * - 页大小
 * - 错误消息
 * - 错误代码
 * - 对象模型
 * <p>
 * 信封对象的返回场景：
 * - API使用者确实无法访问返回头，即一些语言库无法处理HTTP的响应消息，这时候需要以这种形式提供返回值。
 * - API需要支持交叉域请求（通过JSONP）。
 * @author llh
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ObjEnvelop<J> extends Envelop {

//    value = "内容"
    private J obj = (J)new HashMap<>(0);

    public J getObj() {
        return obj;
    }

    public void setObj(J obj) {
        this.obj = obj;
    }

    public static ObjEnvelop getSuccess(String message, Object obj) {
        ObjEnvelop envelop = new ObjEnvelop();
        envelop.setMessage(message);
        envelop.setObj(obj);
        envelop.setStatus(200);
        return envelop;
    }

    public static ObjEnvelop getSuccess(String message, Object obj,int status) {
        ObjEnvelop envelop = new ObjEnvelop();
        envelop.setMessage(message);
        envelop.setObj(obj);
        envelop.setStatus(status);
        return envelop;
    }

    public static ObjEnvelop getError(String message) {
        ObjEnvelop envelop = new ObjEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(-1);
        return envelop;
    }

    public static ObjEnvelop getError(String message,int errorCode) {
        ObjEnvelop envelop = new ObjEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(errorCode);
        return envelop;
    }
}