package com.hrms.model;

import java.util.Collection;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;



import com.hrms.util.UserSecurityUtil;

@Component
public class CustomUserDetail implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserSecurityUtil user;
	private final static String ROLE = "ROLE_";
	
	
	
	
	public CustomUserDetail(UserSecurityUtil user) {

		this.user = user;
	
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		System.out.println("get athorites");
		HashSet<SimpleGrantedAuthority> set = new HashSet<>();

		for (UserRole role : user.getUserRoles()) {

			set.add(new SimpleGrantedAuthority(ROLE+role.getRole().getRoleName()));

		}

		return set;
	}

	@Override
	public String getPassword() {

		return user.getUserEntity().getUserPass();
	}

	@Override
	public String getUsername() {
		return user.getUserEntity().getUserCode();
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
