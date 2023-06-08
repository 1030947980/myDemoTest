package com.example.resultful.web;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.HashMap;
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
public class MixEnvelop<T, J> extends Envelop {

    private static final long serialVersionUID = 2076324875575488461L;

//   当前页"
    private int currPage = 1;

//  每页大小 默认10"
    private int pageSize = 15;

//  总共多少页", example = "2"
    private int totalPage;

//    总共多少数据"
    private int totalCount;

//    列表内容"
    private List<T> detailModelList = new ArrayList<>(0);


//    实体内容"
    private J obj = (J)new HashMap<>(0);


    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        if (totalCount % pageSize == 0) {
            totalPage = totalCount / pageSize;
        } else {
            totalPage = totalCount / pageSize + 1;
        }
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getDetailModelList() {
        return detailModelList;
    }

    public void setDetailModelList(List<T> detailModelList) {
        this.detailModelList = detailModelList;
    }

    public J getObj() {
        return obj;
    }

    public void setObj(J obj) {
        this.obj = obj;
    }

    public static MixEnvelop getSuccess(String message) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccess(String message, Object obj) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setObj(obj);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccess(String message, Object obj, Integer total) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setObj(obj);
        envelop.setTotalCount(total);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccessListWithPage(String message, List detailModelList, int page, int size, Long count) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setPageSize(size);
        envelop.setDetailModelList(detailModelList);
        envelop.setCurrPage(page);
        envelop.setStatus(200);
        envelop.setTotalCount(count.intValue());
        return envelop;
    }

    public static MixEnvelop getPushList(String message, List list) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setDetailModelList(list);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccessListWithPage2(String message, List mapList) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setDetailModelList(mapList);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getError(String message, int errorCode) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(errorCode);
        return envelop;
    }

    public static MixEnvelop getError(String message) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setStatus(-1);
        return envelop;
    }

    public static MixEnvelop getSuccessList(String message, List objList) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setDetailModelList(objList);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccessList(String message, List objList, Integer total) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setDetailModelList(objList);
        envelop.setTotalCount(total);
        envelop.setStatus(200);
        return envelop;
    }

    public static MixEnvelop getSuccess(String message, Object obj, List objList) {
        MixEnvelop envelop = new MixEnvelop();
        envelop.setMessage(message);
        envelop.setObj(obj);
        envelop.setDetailModelList(objList);
        envelop.setStatus(200);
        return envelop;
    }
}