package com.moscue.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations(
				"/static/");
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	//@Bean
	public SimpleMappingExceptionResolver exceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		Properties exceptionMappings = new Properties();
		exceptionMappings.put("java.lang.Exception", "error/error");
		exceptionMappings.put("java.lang.RuntimeException", "error/error");
		exceptionResolver.setExceptionMappings(exceptionMappings);
		Properties statusCodes = new Properties();
		statusCodes.put("error/404", "404");
		statusCodes.put("error/error", "500");
		exceptionResolver.setStatusCodes(statusCodes);
		return exceptionResolver;
	}
	

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/login2").setViewName("login2");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/test").setViewName("login");
		registry.addViewController("/test/{id}").setViewName("login_rest");
		registry.addViewController("/test/foo").setViewName("loginFoo");
		registry.addViewController("/stock/SearchView").setViewName(
				"StockSearchView");
		registry.addViewController("/stock/Search").setViewName("StockSearch");
		registry.addViewController("/stock/Detail").setViewName("StockDetail");
		registry.addViewController("/holder").setViewName("Holder");
		registry.addViewController("/holder/Login").setViewName("HolderLogin");
		registry.addViewController("/holder/Logout")
				.setViewName("HolderLogout");
		registry.addViewController("/holder/LoginView").setViewName(
				"HolderLoginView");
		registry.addViewController("/holder/BalanceView").setViewName(
				"HolderBalanceView");
		registry.addViewController("/holder/BalanceAdd").setViewName(
				"HolderBalanceAdd");
		registry.addViewController("/trade/Buy").setViewName("TradeBuy");
		registry.addViewController("/trade/Sell").setViewName("TradeSell");
		registry.addViewController("/trade/BuyView")
				.setViewName("TradeBuyView");
		registry.addViewController("/trade/SellView").setViewName(
				"TradeSellView");
		registry.addViewController("/trade/Cancel").setViewName("TradeCancel");
		registry.addViewController("/trade/TransactionView").setViewName(
				"TradeTransactionView");
		registry.addViewController("/trade/HoldView").setViewName(
				"TradeHoldView");
		registry.addViewController("/trade/HoldView").setViewName(
				"TradeHoldView");
		registry.addViewController("/info").setViewName("Info");
		registry.addViewController("/greeting").setViewName("Greeting");

	}
	
	
	/*
	@Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }*/
}
