package com.moscue.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

public class MvcUtils {
	public static ModelAndView createMessageModelAndView(String message, String nextUrl, HttpServletRequest request) {
		String path = request.getContextPath();  
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;   
		ModelAndView mav = new ModelAndView();
		String url = basePath + nextUrl;
		mav.setViewName("message");
		mav.addObject("message", message);
		mav.addObject("nextUrl", url);
		return mav;
	}
}
