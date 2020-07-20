package com.example.visualpage.fiter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MyFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest requ, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		requ.setCharacterEncoding("UTF-8");
		HttpServletRequest request = (HttpServletRequest) requ;
		HttpServletResponse response = (HttpServletResponse) resp;
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
	    response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "*");
	    response.setHeader("Access-Control-Allow-Credentials","true"); //是否支持cookie跨域'
	    response.setHeader("P3P", "CP=CAO PSA OUR");
		response.setHeader("XDomainRequestAllowed","1");
	    filterChain.doFilter(request, response);

	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}



}


