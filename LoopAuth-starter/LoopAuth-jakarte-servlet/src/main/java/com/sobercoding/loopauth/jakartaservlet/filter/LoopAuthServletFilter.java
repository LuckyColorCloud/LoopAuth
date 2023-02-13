package com.sobercoding.loopauth.jakartaservlet.filter;

import com.sobercoding.loopauth.function.LoopAuthErrorFilter;
import com.sobercoding.loopauth.function.LoopAuthFilterFun;
import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.util.LoopAuthUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yun
 */
public class LoopAuthServletFilter implements Filter {

    /**
     * 拦截路由
     */
    private ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> includeList = new ConcurrentHashMap<>();

    /**
     * 放行路由
     */
    private ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> excludeList = new ConcurrentHashMap<>();


    /**
     * ===================添加拦截路由=====================
     */
    public LoopAuthServletFilter addInclude(String path, LoopAuthHttpMode... mode) {
        if (mode.length <= 0){
            includeList.put(path, new HashSet<LoopAuthHttpMode>(Collections.singleton(LoopAuthHttpMode.ALL)));
        }else {
            includeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        }
        return this;
    }

    /**====================================================*/

    /**
     * ===================添加放行路由=====================
     */
    public LoopAuthServletFilter addExclude(String path, LoopAuthHttpMode... mode) {
        if (mode.length <= 0){
            excludeList.put(path, new HashSet<LoopAuthHttpMode>(Collections.singleton(LoopAuthHttpMode.ALL)));
        }else {
            excludeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        }
        return this;
    }

    /**====================================================*/

    /**
     * ===================获取放行路由=====================
     */
    public ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> getIncludeList() {
        return includeList;
    }

    public ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> getExcludeList() {
        return excludeList;
    }
    /**====================================================*/

    /**
     * 过滤路由
     */
    public LoopAuthFilterFun loopAuthFilterFun = (boolean isIntercept, String route) -> {
        if (isIntercept){
            // 拦截
            LoopAuthSession.isLogin();
        }
    };
    /**
     * 过滤异常 处理
     */
    public LoopAuthErrorFilter loopAuthErrorFilter = e -> {
        e.printStackTrace();
        return e.getMessage();
    };

    /**
     * 设置 过滤规则
     *
     * @param loopAuthFilterFun 拦截器
     * @return com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter
     */
    public LoopAuthServletFilter setLoopAuthFilter(LoopAuthFilterFun loopAuthFilterFun) {
        this.loopAuthFilterFun = loopAuthFilterFun;
        return this;
    }

    /**
     * 设置异常处理规则
     *
     * @param loopAuthErrorFilter 拦截器异常执行
     * @return com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter
     */
    public LoopAuthServletFilter setLoopAuthErrorFilter(LoopAuthErrorFilter loopAuthErrorFilter) {
        this.loopAuthErrorFilter = loopAuthErrorFilter;
        return this;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            // 路由
            String route = httpServletRequest.getRequestURI();
            // 当前请求方式
            LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(httpServletRequest.getMethod());
            // 执行请求进入前操作
            loopAuthFilterFun.run(LoopAuthUtil.matchPaths(excludeList, includeList, route, loopAuthHttpMode), route);
        } catch (Throwable e) {
            // 1. 获取异常处理策略结果
            String result = String.valueOf(loopAuthErrorFilter.run(e));
            // 2. 写入输出流
            if (response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(result);
            return;
        }
        // 执行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

}
