package com.moscue.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class SecureHolder extends StockHolder implements UserDetails {

	private static final long serialVersionUID = 1L;
	public static final String SCOPE_READ = "read";
	public static final String SCOPE_WRITE = "write";
	public static final String ROLE_USER = "ROLE_USER";

	private Collection<GrantedAuthority> grantedAuthorities = new ArrayList
              <GrantedAuthority>();
	
	public SecureHolder(StockHolder holder) {
		Assert.notNull(holder, "the provided user reference can't benull");
		this.setCode(holder.getCode());
		this.setEmail(this.getEmail());
		this.setId(holder.getId());
		this.setName(holder.getName());
		this.setPassword(holder.getPassword());
		this.setPhone(holder.getPhone());
		for(String ga : Arrays.asList(ROLE_USER, SCOPE_READ, SCOPE_WRITE)) {
			this.grantedAuthorities.add(new SimpleGrantedAuthority(ga));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getCode();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
