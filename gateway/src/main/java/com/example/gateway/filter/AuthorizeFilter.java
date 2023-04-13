package com.example.gateway.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by Bing on 2023/4/7.
 */
@Order(-1)
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        //获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> params = request.getQueryParams();

        //防止SQL注入过滤器
        if(doSqlFilter(params)){
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().setComplete();
        }

//        //文件类型过滤器
//        if(doFileFilter(request)){
//            return this.forbidden(ctx, ResultStatus.ERROR_PARA, "Illegal file");
//        }

        //获取参数中的某个值
        String authorization = params.getFirst("authorization");
        //判断是否等于admin
        //放行or拦截
        if ("admin".equals(authorization)){
            return chain.filter(exchange);
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    public boolean doSqlFilter(MultiValueMap<String, String> params){
        String sql = "";
        for (String key : params.keySet()) {
            List<String> values = params.get(key);

            for (String value : Objects.requireNonNull(values)) {
                sql = sql + value;
            }
            if (sqlValidate(sql)) {
                return true;
            }
        }
        return false;
    }

    private static boolean sqlValidate(String str) {
        if (StringUtils.isNoneBlank(str)){
            str = str.toLowerCase();//统一转为小写,比较简单的单词加入右边空格，避免单词中包含字段
            String badStr = "and |exec |execute |insert |select |delete |update |drop |chr |mid |master |truncate |" +
                    "declare | sitename |net user|xp_cmdshell|or |exec |execute |create |" +
                    "table |from |grant |use |group_concat|column_name|" +
                    "information_schema.columns|table_schema|union |where |select |update |order |by |like |" ;//过滤掉的sql关键字，可以手动添加
            String[] badStrs = badStr.split("\\|");
            for (int i = 0; i < badStrs.length; i++) {
                if (str.indexOf(badStrs[i]) >= 0) {
                    return true;
                }
            }
            return false;
        }else {
            return true;
        }
    }

//    public boolean doFileFilter(ServerHttpRequest req){
//        MultipartResolver resolver = new CommonsMultipartResolver(req.get().getServletContext());
//        if(resolver.isMultipart(req)){
//            MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(req);
////            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
//            Iterator<String> it = multipartRequest.getFileNames();
//
//            while (it.hasNext()){
//                String fileName = (String)it.next();
//                logger.info("doFileFilter :"+fileName);
//                MultipartFile file = multipartRequest.getFile(fileName);
//                if(checkFile(file.getOriginalFilename())){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
