package com.moscue;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.moscue.dao.InMemoryMessageRespository;
import com.moscue.dao.MessageRepository;
import com.moscue.dao.StockHolderRepository;
import com.moscue.entity.Message;
import com.moscue.entity.StockHolder;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableWebMvc
public class Application extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
    public static void main(String[] args) {
    	ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        
    	StockHolderRepository repository = context.getBean(StockHolderRepository.class);
    	StockHolder holder = new StockHolder();
    	holder.setName("lxx");
    	holder.setCode("10001");
    	holder.setEmail("test@163.com");
    	holder.setPassword("123");
    	holder.setPhone("12388888");
    	repository.save(holder);
    	StockHolder holder2 = repository.findOne(1L);
    	System.out.println(holder2.getEmail());
    }
    
    @Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRespository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}
	
    /*
    @Bean
    InternalResourceViewResolver internalResourceViewResolver () {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    */
}
