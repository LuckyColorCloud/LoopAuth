package com.sobercoding.loopauth.servlet.filter;

import com.sobercoding.loopauth.model.LoopAuthHttpMode;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
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

    ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> getExcludeList() {
        return excludeList;
    }
    /**====================================================*/

    /**
     * 过滤路由
     */
    public LoopAuthFilter loopAuthFilter = (boolean isIntercept, String route) -> {
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
     * @param loopAuthFilter 拦截器
     * @return com.sobercoding.loopauth.servlet.filter.LoopAuthServletFilter
     */
    public LoopAuthServletFilter setLoopAuthFilter(LoopAuthFilter loopAuthFilter) {
        this.loopAuthFilter = loopAuthFilter;
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
            // 执行请求进入前操作
            loopAuthFilter.run(matchPaths(excludeList, request, includeList), httpServletRequest.getRequestURI());
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

    /**
     * 路由模糊匹配
     * @author Sober
     * @param excludeList 放行的路由
     * @param request 请求体
     * @param includeList 拦截的路由
     * @return boolean
     */
    private static boolean matchPaths(ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> excludeList, ServletRequest request, ConcurrentHashMap<String, HashSet<LoopAuthHttpMode>> includeList) {
        // 是否存在匹配到的路由
        boolean isInclude;
        if (request == null || includeList.isEmpty()) {
            // 没有需要拦截的路由直接放行
            return false;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 当前路由
        String path = httpServletRequest.getServletPath();
        String method = httpServletRequest.getMethod();
        // 当前请求方式
        LoopAuthHttpMode loopAuthHttpMode = LoopAuthHttpMode.toEnum(method);

        // 列表化需要放行的路由
        ConcurrentHashMap.KeySetView<String, HashSet<LoopAuthHttpMode>> excludes = excludeList.keySet();
        // 匹配路由 和 请求方法 存在一致则直接放行
        isInclude = excludes.stream().anyMatch(item ->
                LoopAuthUtil.fuzzyMatching(item, path) &&
                        (excludeList.get(item).contains(LoopAuthHttpMode.ALL) ||
                                excludeList.get(item).contains(loopAuthHttpMode)));
        if (isInclude) {
            return false;
        }

        // 列表化需要拦截的路由
        ConcurrentHashMap.KeySetView<String, HashSet<LoopAuthHttpMode>> includes = includeList.keySet();
        // 匹配路由 和 请求方法 存在一致则直接拦截
        isInclude = includes.stream().anyMatch(item ->
                LoopAuthUtil.fuzzyMatching(item, path) &&
                        (includeList.get(item).contains(LoopAuthHttpMode.ALL) ||
                                includeList.get(item).contains(loopAuthHttpMode)));
        return isInclude;
    }
}
