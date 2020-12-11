package com.lrm.interceptor;

import com.lrm.po.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by limi on 2017/10/15.
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        System.out.println(request.getRequestURL());
        System.out.println(request.getContextPath());
       /* if(request.getSession().getAttribute("user") == null && request.getRequestURL().equals("http://localhost:8080/admin/signup")){
            response.sendRedirect("/admin");
            return false;
        }*/
        if(request.getSession().getAttribute("user") == null) {
//            if ((request.getRequestURL().equals("http://localhost:8080/admin/signup"))) {
            response.sendRedirect("/begin");
            return false;
        }else if(((User) request.getSession().getAttribute("user")).getId()!=1){
            response.sendRedirect("/begin");
            return false;
        }
        //       }
        //       if(request.getSession().getAttribute("user") == null) {

        //           response.sendRedirect("/admin");
        //           return false;

        //      }
        return true;
    }
}
