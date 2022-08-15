package com.sobercoding.loopauth.filter;

import com.sobercoding.loopauth.CheckPermissionAnnotation;

import javax.servlet.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yun
 */
public class LoopAuthServletFilter implements Filter {

    /**
     * 拦截路由
     */
    private Set<String> includeList = new HashSet<>();

    /**
     * 放行路由
     */
    private Set<String> excludeList = new HashSet<>();


    /**
     * ===================添加拦截路由=====================
     */
    public LoopAuthServletFilter addInclude(String... paths) {
        includeList.addAll(Arrays.asList(paths));
        return this;
    }

    public LoopAuthServletFilter addInclude(Collection<String> paths) {
        includeList.addAll(paths);
        return this;
    }
    /**====================================================*/

    /**
     * ===================添加放行路由=====================
     */
    public LoopAuthServletFilter addExclude(String... paths) {
        excludeList.addAll(Arrays.asList(paths));
        return this;
    }

    public LoopAuthServletFilter addExclude(Collection<String> paths) {
        excludeList.addAll(paths);
        return this;
    }
    /**====================================================*/

    /**
     * ===================获取放行路由=====================
     */
    public Set<String> getIncludeList() {
        return includeList;
    }

    Set<String> getExcludeList() {
        return excludeList;
    }
    /**====================================================*/

    /**
     * 过滤路由
     */
    public LoopAuthFilter loopAuthFilter = r -> {};
    /**
     * 过滤异常 处理
     */
    public LoopAuthErrorFilter loopAuthErrorFilter = r -> r;

    /**
     * 设置 过滤规则
     * @param loopAuthFilter
     * @return
     */
    public LoopAuthServletFilter setLoopAuthFilter(LoopAuthFilter loopAuthFilter) {
        this.loopAuthFilter = loopAuthFilter;
        return this;
    }

    /**
     * 设置异常处理规则
     * @param loopAuthErrorFilter
     * @return
     */
    public LoopAuthServletFilter setLoopAuthErrorFilter(LoopAuthErrorFilter loopAuthErrorFilter) {
        this.loopAuthErrorFilter = loopAuthErrorFilter;
        return this;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            loopAuthFilter.run(CheckPermissionAnnotation.matchPaths(excludeList, request));
        } catch (Throwable e) {
            // 1. 获取异常处理策略结果
            String result =  String.valueOf(loopAuthErrorFilter.run(e));
            // 2. 写入输出流
            if(response.getContentType() == null) {
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
