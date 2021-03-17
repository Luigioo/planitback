package com.lanc.planit.filter;
/**
 * @Author Luigi Lin
 *
 */
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
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

        System.out.println("Security config filter: ");
        if(SecurityContextHolder.getContext().getAuthentication()!=null){
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        }

//        System.out.println("header: "+resp.getHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN));
//        System.out.println("hs:"+resp.getHeaderNames());

        String cookie = resp.getHeader("Set-Cookie");

//        System.out.println("olala?");
        if (cookie != null) {
            //important for the deployed version
            resp.setHeader("Set-Cookie", cookie + "; Secure; SameSite=none");
//            System.out.println("OLALA: "+ resp.getHeader("Set-Cookie"));
        }
        chain.doFilter(request, response);
    }
}
