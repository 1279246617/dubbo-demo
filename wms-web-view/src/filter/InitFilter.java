package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @ClassName
 * @author fqq
 * @data2017年3月22日下午3:21:19
 * @Description:TODO
 */
public class InitFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String ctx = httpServletRequest.getRequestURI().replace(httpServletRequest.getServletPath(), "");
		((HttpServletRequest) request).getSession().setAttribute("ctx", ctx);
		filterChain.doFilter(request, response);
	}
	
	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

}
