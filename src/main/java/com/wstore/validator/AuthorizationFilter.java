package com.wstore.validator;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wstore.beans.LoginBean;


/**
 * Servlet Filter implementation class AuthorizationFilter
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthorizationFilter() {

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		//nothing to do here
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//get the loginBean from session attribute
		LoginBean loginBean = (LoginBean) ((HttpServletRequest)request).getSession().getAttribute("loginBean");
		// For the first application request there is no loginBean in the session so user needs to log in
		// For other requests loginBean is present but we need to check if user has logged in successfully
		if(loginBean == null || !loginBean.isLoggedIn()) {
			String contextPath = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse)response).sendRedirect(contextPath + "/index.jsf");
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		//nothing to do here
	}

}
