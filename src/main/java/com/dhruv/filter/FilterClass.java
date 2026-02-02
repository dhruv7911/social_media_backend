package com.dhruv.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.Filter.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletRequest;

public class FilterClass implements Filter{
	@Override
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException, ServletResponse {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String origin = httpRequest.getHeader("Origin"); 
		// Set CORS headers
        if(origin != null && origin.equals("http://localhost:5173")) {
        	httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With");
            httpResponse.setHeader("Access-Control-Max-Age", "3600");
        }
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
	}
	@Override
	public void destroy() {
		//cleanup logic if needed
	}
}