package com.moscue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.moscue.entity.StockHolder;
import com.moscue.service.HolderDetailsService;
import com.moscue.service.StockHolderService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ApplicationTests {

	@Autowired
	public HolderDetailsService holderDetailsService;
	
	@Autowired
	public StockHolderService stockHolderService;
	
	@Test
	public void contextLoads() {
		//UserDetails UserDetails = holderDetailsService.loadUserByUsername("10001");
		
		//System.out.println(UserDetails.getUsername());
		
		StockHolder holder = stockHolderService.findByCode("10001");
		System.out.println(holder.getCode());
	}

}
