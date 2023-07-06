package com.irving.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irving.commonly.R;
import com.irving.utlis.ThreadLocalUtils;
import com.irving.domain.Employee;
import com.irving.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        String[] paths = {
                "/employee/logout",
                "/employee/login",
                "/backend/**",
                "/front/**",
                "/common/upload",
                "/common/download",
                "/user/login"
        };


        log.info("拦截到的请求{}", request.getRequestURI());
        if (check(paths,path)){
            filterChain.doFilter(request,response);
            return;
        }
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        if (employee!=null){
//            long id = Thread.currentThread().getId();
            ThreadLocalUtils.SetObject(employee);
            filterChain.doFilter(request,response);
            return;
        }
        User user=(User)request.getSession().getAttribute("user");
        if (user!=null){
            ThreadLocalUtils.SetObject(user);
            filterChain.doFilter(request,response);
            return;
        }
        ObjectMapper objectMapper=new ObjectMapper();
        String s = objectMapper.writeValueAsString(R.error("NOTLOGIN"));
        response.getWriter().write(s);
    }

    private boolean check(String[] paths, String path) {
        for (int i = 0; i < paths.length; i++) {
            if(PATH_MATCHER.match(paths[i],path)){
                return true;
            }
        }
        return false;
    }


}
