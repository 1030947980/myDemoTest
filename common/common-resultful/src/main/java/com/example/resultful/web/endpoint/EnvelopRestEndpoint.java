package com.example.resultful.web.endpoint;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.resultful.web.*;
import com.example.resultful.web.exceptions.ServiceException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * REST风格控控制器基类。
 * 基于Envelop的返回结果封装
 * HTTP响应体格式为JSON。
 * + 成功：根据各业务逻辑自行决定要返回的数据，各业务模块的返回结构不尽相同。
 * + 失败：{"status":错误码, "message":"错误原因"}
 *
 * @author Progr1mmer
 */
public abstract class EnvelopRestEndpoint extends Exception {

    @Autowired
    protected ObjectMapper objectMapper;

    protected Envelop success() {
        return success("success");
    }

    protected Envelop success(String message) {
        return success(message, 200);
    }

    protected Envelop success(String message, int status) {
        Envelop envelop = new Envelop();
        envelop.setMessage(message);
        envelop.setStatus(status);
        return envelop;
    }

    protected <J> ObjEnvelop<J> success(J obj){
        return success("success", obj);
    }

    protected <J, _J> ObjEnvelop<_J> success(J obj, Class<_J> target){
        _J _obj = convertToModel(obj, target);
        return success("success", _obj);
    }

    protected <J> ObjEnvelop<J> success(String message, J obj){
        return success(message, 200, obj);
    }

    protected <J, _J> ObjEnvelop<_J> success(String message, J obj, Class<_J> target){
        _J _obj = convertToModel(obj, target);
        return success(message, 200, _obj);
    }

    protected <J> ObjEnvelop<J> success(String message, int status, J obj){
        ObjEnvelop<J> objEnvelop = new ObjEnvelop<>();
        objEnvelop.setMessage(message);
        objEnvelop.setStatus(status);
        objEnvelop.setObj(obj);
        return objEnvelop;
    }

    protected <J, _J> ObjEnvelop<_J> success(String message, int status, J obj, Class<_J> target){
        _J _obj = convertToModel(obj, target);
        return success(message, status, _obj);
    }

    protected <T> ListEnvelop<T> success(List<T> contents){
        return success("success", contents);
    }

    protected <T, _T> ListEnvelop<_T> success(List<T> contents, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success("success", _contents);
    }

    protected <T> ListEnvelop<T> success(String message, List<T> contents){
        return success(message, 200, contents);
    }

    protected <T, _T> ListEnvelop<_T> success(String message, List<T> contents, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success(message, 200, _contents);
    }

    protected <T> ListEnvelop<T> success(String message, int status, List<T> contents){
        ListEnvelop<T> listEnvelop = new ListEnvelop<>();
        listEnvelop.setMessage(message);
        listEnvelop.setStatus(status);
        listEnvelop.setDetailModelList(contents);
        return listEnvelop;
    }

    protected <T, _T> ListEnvelop<_T> success(String message, int status, List<T> contents, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success(message, status, _contents);
    }

    protected <T> PageEnvelop<T> success(List<T> contents, int totalCount, int currPage, int pageSize) {
        return success("success", contents, totalCount, currPage, pageSize);
    }

    protected <T, _T> PageEnvelop<_T> success(List<T> contents, int totalCount, int currPage, int pageSize, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success("success", _contents, totalCount, currPage, pageSize);
    }

    protected <T> PageEnvelop<T> success(String message, List<T> contents, int totalCount, int currPage, int pageSize) {
        return success(message, 200, contents, totalCount, currPage, pageSize);
    }

    protected <T, _T> PageEnvelop<_T> success(String message, List<T> contents, int totalCount, int currPage, int pageSize, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success(message, 200, _contents, totalCount, currPage, pageSize);
    }

    protected <T> PageEnvelop<T> success(String message, int status, List<T> contents, int totalCount, int currPage, int pageSize) {
        PageEnvelop<T> pageEnvelop = new PageEnvelop();
        pageEnvelop.setMessage(message);
        pageEnvelop.setStatus(status);
        pageEnvelop.setCurrPage(currPage);
        pageEnvelop.setPageSize(pageSize);
        pageEnvelop.setTotalCount(totalCount);
        pageEnvelop.setDetailModelList(contents);
        return pageEnvelop;
    }

    protected <T, _T> PageEnvelop<_T> success(String message, int status, List<T> contents, int totalCount, int currPage, int pageSize, Class<_T> target){
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), target);
        return success(message, status, _contents, totalCount, currPage, pageSize);
    }

    protected <T, J> MixEnvelop<T, J> success(List<T> contents, J obj) {
        return success("success", contents, obj);
    }

    protected <T, J, _T, _J> MixEnvelop<_T, _J> success(List<T> contents, J obj, Class<_T> targetContents, Class<_J> targetObj) {
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), targetContents);
        _J _obj = convertToModel(obj, targetObj);
        return success("success", _contents, _obj);
    }

    protected <T, J> MixEnvelop<T, J> success(String message, List<T> contents, J obj) {
        return success(message, 200, contents, obj);
    }

    protected <T, J, _T, _J> MixEnvelop<_T, _J> success(String message, List<T> contents, J obj, Class<_T> targetContents, Class<_J> targetObj) {
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), targetContents);
        _J _obj = convertToModel(obj, targetObj);
        return success(message, 200, _contents, _obj);
    }

    protected <T, J> MixEnvelop<T, J> success(String message, int status, List<T> contents, J obj) {
        return success(message, status, contents, obj, contents.size(), 1, contents.size() != 0 ? contents.size() : 15);
    }

    protected <T, J, _T, _J> MixEnvelop<_T, _J> success(String message, int status, List<T> contents, J obj, Class<_T> targetContents, Class<_J> targetObj) {
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), targetContents);
        _J _obj = convertToModel(obj, targetObj);
        return success(message, status, _contents, _obj, _contents.size(), 1, _contents.size() != 0 ? _contents.size() : 15);
    }

    protected <T, J> MixEnvelop<T, J> success(String message, int status, List<T> contents, J obj, int totalCount, int currPage, int pageSize) {
        MixEnvelop<T, J> mixEnvelop = new MixEnvelop<>();
        mixEnvelop.setMessage(message);
        mixEnvelop.setStatus(status);
        mixEnvelop.setCurrPage(currPage);
        mixEnvelop.setPageSize(pageSize);
        mixEnvelop.setTotalCount(totalCount);
        mixEnvelop.setDetailModelList(contents);
        mixEnvelop.setObj(obj);
        return mixEnvelop;
    }

    protected <T, J, _T, _J> MixEnvelop<_T, _J> success(String message, int status, List<T> contents, J obj, int totalCount, int currPage, int pageSize, Class<_T> targetContents, Class<_J> targetObj) {
        List<_T> _contents = convertToModels(contents, new ArrayList<>(contents.size()), targetContents);
        _J _obj = convertToModel(obj, targetObj);
        return success(message, status, _contents, _obj, totalCount, currPage, pageSize);
    }

    protected Envelop failed (String message) {
        return failed(message, -10000);
    }

    public PageEnvelop failedPageEnvelopException(Exception e){
        System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())&& e instanceof ServiceException){
            return PageEnvelop.getError(e.getMessage(),-10000);
        }else {
            return PageEnvelop.getError("系统繁忙，请稍后尝试",-10000);
        }
    }
    protected Envelop failedException(Exception e){
        System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())&& e instanceof ServiceException){
            return failed(e.getMessage(),-10000);
        }else {
            return failed("系统繁忙，请稍后尝试",-10000);
        }
    }
    protected MixEnvelop failedMixEnvelopException(Exception e){
        System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())&& e instanceof ServiceException){
            return MixEnvelop.getError(e.getMessage(),-10000);
        }else {
            return MixEnvelop.getError("系统繁忙，请稍后尝试",-10000);
        }
    }
    protected ListEnvelop failedListEnvelopException(Exception e){
        System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())&& e instanceof ServiceException){
            return ListEnvelop.getCodeError(e.getMessage(),-10000);
        }else {
            return ListEnvelop.getCodeError("系统繁忙，请稍后尝试",-10000);
        }
    }
    protected ObjEnvelop failedObjEnvelopException(Exception e){
        System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())&& e instanceof ServiceException)
            System.out.println(e.getClass());
        if (StringUtils.isNotBlank(e.getMessage())){
            return ObjEnvelop.getError(e.getMessage(),-10000);
        }else {
            return ObjEnvelop.getError("系统繁忙，请稍后尝试",-10000);
        }
    }
    protected Envelop failed (String message, int status) {
        Envelop envelop = new Envelop();
        envelop.setMessage(message);
        envelop.setStatus(status);
        return envelop;
    }

    protected <E extends Envelop> E failed(String message, Class<E> clazz) {
        return failed(message, -10000, clazz);
    }

    protected <E extends Envelop> E failed(String message, int status, Class<E> clazz) {
        try {
            E envelop = clazz.newInstance();
            envelop.setMessage(message);
            envelop.setStatus(status);
            return envelop;
        } catch (Exception e) {
            return null;
        }
    }

    protected <T> T toEntity(String json, Class<T> target) throws IOException {
        T entity = objectMapper.readValue(json, target);
        return entity;
    }

    protected <T> T toDateEntity(String json, Class<T> target) throws IOException {
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        T entity = objectMapper.readValue(json, target);
        return entity;
    }

    protected <T> T convertToModel(Object source, Class<T> target, String... properties) {
        if (source == null) {
            return null;
        }
        T _target = BeanUtils.instantiate(target);
        BeanUtils.copyProperties(source, _target, propertyDiffer(properties, target));
        return _target;
    }

    protected <T> List<T> convertToModels(Collection sources, List<T> targets, Class<T> target){
        if (null == sources) {
            return null;
        }
        sources.forEach(item -> {
            T _target = BeanUtils.instantiate(target);
            BeanUtils.copyProperties(item, _target);
            targets.add(_target);
        });
        return targets;
    }

    protected <T> List<T> convertToModels(Collection sources, List<T> targets, Class<T> target, String properties) {
        if (null == sources) {
            return null;
        }
        sources.forEach(item -> {
            T _target = BeanUtils.instantiate(target);
            BeanUtils.copyProperties(item, _target, propertyDiffer(StringUtils.isEmpty(properties) ? null : properties.split(","), target));
            targets.add(_target);
        });
        return targets;
    }

    protected String[] propertyDiffer(String[] properties, Class target) {
        if (properties == null || properties.length == 0) {
            return null;
        }
        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(target);
        List<String> propertiesList = Arrays.asList(properties);
        List<String> arrayList = new ArrayList<>();
        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && !propertiesList.contains(targetPd.getName())) {
                arrayList.add(targetPd.getName());
            }
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    protected String randomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int pos = random.nextInt(str.length());
            builder.append(str.charAt(pos));
        }
        return builder.toString();
    }

    /**
     * 客户端调用REST接口时，若返回的是分页结果，则需要在响应头中添加资源的总数量及其他资源的分页导航。
     * EHR平台使用响应头中的 X-Total-Count 字段记录资源的总数量，link header作为其他资源的分页导航。
     *
     * @return
     */
    @Deprecated
    public void pagedResponse(
            HttpServletRequest request,
            HttpServletResponse response,
            Long resourceCount, Integer currentPage, Integer pageSize) {
        if (request == null || response == null) return;

        response.setHeader("X-Total-Count", Long.toString(resourceCount));
        if (resourceCount == 0) return;

        if (currentPage == null) currentPage = new Integer(1);
        if (pageSize == null) pageSize = new Integer(15);


        String baseUri = "<" + request.getRequestURL().append("?").toString() + request.getQueryString() + ">";
        long firstPage = currentPage == 1 ? -1 : 1;
        long prevPage = currentPage == 1 ? -1 : currentPage - 1;

        long lastPage = resourceCount % pageSize == 0 ? resourceCount / pageSize : resourceCount / pageSize + 1;
        long nextPage = currentPage == lastPage ? -1 : currentPage + 1;

        lastPage = currentPage == lastPage ? -1 : lastPage;

        Map<String, String> map = new HashMap<>();
        if (firstPage != -1)
            map.put("rel='first',", baseUri.replaceAll("page=\\d+", "page=" + Long.toString(firstPage)));
        if (prevPage != -1) map.put("rel='prev',", baseUri.replaceAll("page=\\d+", "page=" + Long.toString(prevPage)));
        if (nextPage != -1) map.put("rel='next',", baseUri.replaceAll("page=\\d+", "page=" + Long.toString(nextPage)));
        if (lastPage != -1) map.put("rel='last',", baseUri.replaceAll("page=\\d+", "page=" + Long.toString(lastPage)));

        response.setHeader("Link", linkMap(map));
    }

    private String linkMap(Map<String, String> map) {
        StringBuffer links = new StringBuffer("");
        for (String key : map.keySet()) {
            links.append(map.get(key)).append("; ").append(key);
        }

        return links.toString();
    }
    /**
     * 获取被代理人code，若没有则返回当前登录者
     * @return
     */
    public String getRepUID(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSONObject.parseObject(userAgent);
            if(!json.containsKey("represented")){
                return json.getString("uid");
            }
            return json.getString("represented");
        } catch (Exception e) {
            return null;
        }
    }
    public String getUID() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String userAgent = request.getHeader("userAgent");
            if (StringUtils.isEmpty(userAgent)) {
                userAgent = request.getHeader("User-Agent");
            }
            JSONObject json = JSON.parseObject(userAgent);
            return json.getString("uid");

        } catch (Exception e) {
            return null;
        }
    }
    public String getUID(String patient) {
        try {
            if(StringUtils.isEmpty(patient)){
                patient=this.getUID();
            }
            return patient;

        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getUserAgent(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userAgent = request.getHeader("userAgent");
            JSONObject user = JSON.parseObject(userAgent);
            return user;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址。
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
                //根据网卡取本机配置的IP
                InetAddress inet=null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip= inet.getHostAddress();
            }
        }
        return ip;
    }
}
