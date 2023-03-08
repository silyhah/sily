package com.sily.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.sily.reggie.common.BaseContext;
import com.sily.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String requestURI = httpServletRequest.getRequestURI();

        log.info("拦截到请求：{}", requestURI);

        String urls[] = new String[]{
                "/backend/**",
                "/front/**",
                "/employee/login",
                "/employee/logout",
                "/user/sendMsg",
                "/user/login"
        };


        boolean flag = check(urls, requestURI);
        if (flag) {
            log.info("本次请求{}不需要处理", requestURI);
            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if (httpServletRequest.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id为：{}", httpServletRequest.getSession().getAttribute("employee"));

            Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            chain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if(httpServletRequest.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",httpServletRequest.getSession().getAttribute("user"));

            Long userId = (Long) httpServletRequest.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            chain.doFilter(request,response);
            return;
        }


        log.info("用户未登录");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
