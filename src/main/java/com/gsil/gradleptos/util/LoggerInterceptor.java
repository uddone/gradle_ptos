package com.gsil.gradleptos.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

@Slf4j(topic  = "CONSOLE")
public class LoggerInterceptor implements HandlerInterceptor
{

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		if(request.getRequestURL().toString().contains("/DashBoard")) return true;

		return true;
	}

	private String getParameters(HttpServletRequest request)
	{
		StringBuffer posted = new StringBuffer();
		Enumeration<?> e = request.getParameterNames();
		if (e != null)
		{
			posted.append("{");
		}
		while (e.hasMoreElements())
		{
			if (posted.length() > 1)
			{
				posted.append(", {");
			}
			String curr = (String) e.nextElement();
			posted.append(curr + "=");
			posted.append(request.getParameter(curr).concat("}"));
		}
		String ip = request.getHeader("X-FORWARDED-FOR");
		String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		if (ipAddr != null && !ipAddr.equals("")) {
			posted.append(" , psip = " + ipAddr);
		}
		
		return posted.toString();
	}

	private String getRemoteAddr(HttpServletRequest request)
	{
		String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			return ipFromHeader;
		}
		return request.getRemoteAddr();
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception
	{}


	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception
	{
		String uri = request.getRequestURI().toString();
		String result = "성공";
		
		if (ex != null) {
			ex.printStackTrace();
			result = "실패";
		}else {
			if(uri.contains("/DashBoard")) {}
			else{}
		}
		
	}

}
