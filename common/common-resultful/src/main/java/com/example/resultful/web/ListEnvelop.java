package com.example.resultful.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

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
public class ListEnvelop<T> extends Envelop {

    private List<T> detailModelList = new ArrayList<>(0);

    public List<T> getDetailModelList() {
        return detailModelList;
    }

    public void setDetailModelList(List<T> detailModelList) {
        this.detailModelList = detailModelList;
    }

    public static ListEnvelop getSuccess(String message, List detailModelList) {
        ListEnvelop envelop = new ListEnvelop();
        envelop.setMessage(message);
        envelop.setDetailModelList(detailModelList);
        envelop.setStatus(200);
        return envelop;
    }
    public static ListEnvelop getError(String message) {
        ListEnvelop envelop = new ListEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(-1);
        return envelop;
    }
    public static ListEnvelop getCodeError(String message,Integer code) {
        ListEnvelop envelop = new ListEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(code);
        return envelop;
    }
}