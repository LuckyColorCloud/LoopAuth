package com.sobercoding.loopauth.springbootstarter.filter;

import com.sobercoding.loopauth.router.LoopAuthHttpMode;
import com.sobercoding.loopauth.springbootstarter.CheckPermissionAnnotation;
import com.sobercoding.loopauth.util.LoopAuthUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
        includeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        return this;
    }

    public LoopAuthServletFilter addIncludes(String path, LoopAuthHttpMode... loopAuthHttpModes) {
        includeList.put(path, new HashSet<>(Arrays.asList(loopAuthHttpModes)));
        return this;
    }
    /**====================================================*/

    /**
     * ===================添加放行路由=====================
     */
    public LoopAuthServletFilter addExclude(String path, LoopAuthHttpMode... mode) {
        excludeList.put(path, new HashSet<LoopAuthHttpMode>(Arrays.asList(mode)));
        return this;
    }

    public LoopAuthServletFilter addExcludes(String path, LoopAuthHttpMode... loopAuthHttpModes) {
        excludeList.put(path, new HashSet<>(Arrays.asList(loopAuthHttpModes)));
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
    public LoopAuthFilter loopAuthFilter = r -> {
    };
    /**
     * 过滤异常 处理
     */
    public LoopAuthErrorFilter loopAuthErrorFilter = r -> r;

    /**
     * 设置 过滤规则
     *
     * @param loopAuthFilter 拦截器
     * @return com.sobercoding.loopauth.springbootstarter.filter.LoopAuthServletFilter
     */
    public LoopAuthServletFilter setLoopAuthFilter(LoopAuthFilter loopAuthFilter) {
        this.loopAuthFilter = loopAuthFilter;
        return this;
    }

    /**
     * 设置异常处理规则
     *
     * @param loopAuthErrorFilter 拦截器异常执行
     * @return com.sobercoding.loopauth.springbootstarter.filter.LoopAuthServletFilter
     */
    public LoopAuthServletFilter setLoopAuthErrorFilter(LoopAuthErrorFilter loopAuthErrorFilter) {
        this.loopAuthErrorFilter = loopAuthErrorFilter;
        return this;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            // 存在拦截路由时启用
            if (LoopAuthUtil.isNotEmpty(includeList)) {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                // 放行路由为空 拦截
                if (LoopAuthUtil.isEmpty(excludeList)) {
                    loopAuthFilter.run(CheckPermissionAnnotation.matchPaths(excludeList, request, includeList));
                }
                // 放行路由不为空 且 放行路由不包含当前路由
                if (LoopAuthUtil.isNotEmpty(excludeList) && !excludeList.contains(httpServletRequest.getRequestURI())) {
                    loopAuthFilter.run(CheckPermissionAnnotation.matchPaths(excludeList, request));
                }
            }
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
