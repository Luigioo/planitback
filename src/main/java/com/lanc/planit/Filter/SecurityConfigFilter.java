package com.lanc.planit.Filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityConfigFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        //resp.setHeader("Set-Cookie", "locale=de; HttpOnly; SameSite=strict");
        String cookie = resp.getHeader("Set-Cookie");
        if (cookie != null) { resp.setHeader("Set-Cookie", cookie + "; SameSite=none");}
        chain.doFilter(request, response);
    }
}
