package com.aws.micro.config;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    //@Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
    		throws IOException, ServletException {
    	
    	HttpServletRequest hreq = (HttpServletRequest) req;
    	Enumeration<String> enums = hreq.getHeaderNames();
    	while(enums.hasMoreElements()) {
    		String headerKey = enums.nextElement();
    		System.out.print("The Header Key==>"+headerKey);
    		System.out.print("---");
    		System.out.print("The Header Value==>"+hreq.getHeader(headerKey));
    		System.out.println();
    	}
    	
    	Enumeration<String> penums = hreq.getParameterNames();
    	while(penums.hasMoreElements()) {
    		String paramKey = penums.nextElement();
    		System.out.print("The Param Key==>"+paramKey);
    		System.out.print("---");
    		System.out.print("The Param Value==>"+hreq.getParameter(paramKey));
    		System.out.println();
    	}
    	
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Max-Age", "3600");
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    //@Override
    public void destroy() {
    }

    //@Override
    public void init(FilterConfig config) throws ServletException {
    }
}