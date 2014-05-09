package com.moscue.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.moscue.schdule.ScheduledTasks;

@Configuration
@EnableScheduling
public class SchduleConfig {
	
	@Bean
	public ScheduledTasks tasks() {
		return new ScheduledTasks();
	}
	
}
