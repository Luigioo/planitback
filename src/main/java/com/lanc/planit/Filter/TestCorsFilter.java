package com.lanc.planit.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/**")
@Order(-99999)
public class TestCorsFilter extends HttpFilter {

    private static final long serialVersionUID = 7699L;
    private static final String Options_METHOD = "OPTIONS";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String origin = request.getHeader(HttpHeaders.ORIGIN);
        System.out.println(request.getMethod());
        if(origin!=null&&origin!=""){
            if(request.getMethod().equals("OPTIONS")){
                response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                String requestHeaders = request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS);
                if (requestHeaders!=null&&requestHeaders!="") {
                    response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders);
                }
                response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
                System.out.println("options request from: "+origin);

            }
        }else{
            System.out.println("no origin");
        }



        super.doFilter(request, response, chain);
    }
}
