package com.smartxphones.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class UsersDetails implements UserDetails {
	
	 private String userName;
	    private String password;
	    private List<GrantedAuthority> authorities;

	    public UsersDetails(User user) {
	        this.userName = user.getUserName();
	        this.password = user.getPassword();
	        this.authorities = user.getRoles()
	        		.stream().map(u -> u.getRole())
	        		.map(SimpleGrantedAuthority::new)
	        		.collect(Collectors.toList());
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    @Override
	    public String getPassword() {
	        return password;
	    }

	    @Override
	    public String getUsername() {
	        return userName;
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

