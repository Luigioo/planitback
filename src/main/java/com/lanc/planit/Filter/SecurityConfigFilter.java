package com.lanc.planit.Filter;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityConfigFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;


        System.out.println("header: "+resp.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
        System.out.println("hs:"+resp.getHeaderNames());
        //resp.setHeader("Set-Cookie", "locale=de; HttpOnly; SameSite=strict");
        String cookie = resp.getHeader("Set-Cookie");

        System.out.println("olala?");
        if (cookie != null) {
            resp.setHeader("Set-Cookie", cookie + "; Secure; SameSite=none");
            System.out.println("OLALA: "+ resp.getHeader("Set-Cookie"));
        }
        chain.doFilter(request, response);
    }
}
