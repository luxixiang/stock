package com.moscue;

import java.util.Date;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.moscue.config.SchduleConfig;
import com.moscue.dao.InMemoryMessageRespository;
import com.moscue.dao.MessageRepository;
import com.moscue.dao.OrderRepository;
import com.moscue.dao.StockHolderRepository;
import com.moscue.entity.Balance;
import com.moscue.entity.Message;
import com.moscue.entity.StockHolder;
import com.moscue.entity.StockOrder;
import com.moscue.schdule.ScheduledTasks;
import com.moscue.service.StockHolderService;

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
    	ScheduledTasks task= context.getBean(com.moscue.schdule.ScheduledTasks.class);
		task.fetchStock();
		
		StockHolderRepository repository = context.getBean(StockHolderRepository.class);
    	StockHolder holder2 = repository.findOne(1L);
    	System.out.println(holder2.getEmail());
		StockHolderService stockHolderService = context.getBean(StockHolderService.class);
		Balance balance = stockHolderService.getBalanceByHolderId(holder2.getId());
		if (balance == null) {
			balance = new Balance();
			balance.setAddTime(new Date());
			balance.setHolderId(holder2.getId());
			balance.setRemain(200000.0);
			balance.setUpdateTime(new Date());
			stockHolderService.saveBalance(balance);
		}
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
}
