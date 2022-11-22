package com.briup.crmgateway.filter;

import com.briup.crmuitl.util.JwtUtil;
import com.briup.crmuitl.util.Result;
import com.briup.crmuitl.util.ResultCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @Author lining
 * @Date 2022/11/15
 */
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper ObjectMapper;
    @Value("${tokenfilter.whiteList}")
    private String whiteList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取通过拦截的返回对象
        Mono<Void> mono = chain.filter(exchange);
        //获取请求
        ServerHttpRequest request = exchange.getRequest();
        //获取响应
        ServerHttpResponse response = exchange.getResponse();
        //当用户进行options请求时，放行
        if (request.getMethod().matches("OPTIONS")) {
            return mono;
        }
        String url = request.getURI().getPath();
        System.out.println("请求路径："+url);
        //当访问路径为白名单路径，放行
        if (url.matches(whiteList)) {
            return mono;
        }
        //获取请求头信息
        String token = request.getHeaders().getFirst("token");
        //当token为空，提示用户未登录
        if (token == null) {
            return writeResultToResp(response, ResultCode.USER_NOT_LOGIN);
        }
        //如果token验证无效或者redis中存储超时，提示用户token无效,请重新登录
        if (!(JwtUtil.checkSign(token) && redisTemplate.hasKey(token))) {
            return writeResultToResp(response, ResultCode.TOKEN_IS_INVALID);
        }
        //通过拦截，进行放行
        return mono;
    }

    @Override
    public int getOrder() {
        //设置执行顺序，数值越小，执行越前
        return -100;
    }

    /**
     * 设置响应保存信息
     * @param response  响应对象
     * @param msg 响应体数据
     * @return
     */
    public Mono<Void> writeResultToResp(ServerHttpResponse response, ResultCode msg) {
        try {
            //响应状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应头
            response.getHeaders().add("Content-Type", "application/json;charset=utf-8");
            //响应体
            DataBuffer body = response.bufferFactory().wrap(ObjectMapper.writeValueAsBytes(Result.failure(msg)));
            return response.writeWith(Mono.just(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.setComplete();
    }
}
