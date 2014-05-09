package com.moscue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.moscue.entity.SecureHolder;
import com.moscue.entity.StockHolder;

@Component("userService")
public class HolderDetailsService implements UserDetailsService {

	@Autowired
	private StockHolderService stockHolderService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		StockHolder holder = stockHolderService.findByCode(username);
		if (holder != null){
			return new SecureHolder(holder);
		}
		throw new UsernameNotFoundException(username);
	}

}
