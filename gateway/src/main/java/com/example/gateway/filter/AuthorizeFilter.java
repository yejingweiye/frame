package com.example.gateway.filter;

import com.example.common.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    private final static String AUTHORIZE_TOKEN="Authorize";

    /**
     * 全局拦截
     *
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //获取令牌信息header param cookies
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);

        // true：token 在header里面，方便token在下模块取
        boolean hasHeaderToken = true;

        if(StringUtils.isBlank(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            hasHeaderToken =  false;
        }

        if(StringUtils.isBlank(token)){
            HttpCookie httpCookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(httpCookie!=null){
                token = httpCookie.getValue();
                hasHeaderToken =  false;
            }
        }

        //无令牌，返回信息
        if(StringUtils.isBlank(token)){
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //有令牌则校验，能解析证明成功
        try {
            JwtUtil.parseJWT(token);
        } catch (Exception e) {
            //无效拦截
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        //令牌封装到header中
        request.mutate().header(AUTHORIZE_TOKEN,token);

         // 有效放行
        return chain.filter(exchange);
    }

    /**
     *
     * 排序
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
