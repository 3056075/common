package com.zm.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zm.user.entity.Role;
import com.zm.user.entity.User;

public class ZmUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = -7925151268075157934L;

	private List<GrantedAuthority> grantedAuthoritys;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (null == grantedAuthoritys) {
			grantedAuthoritys = new ArrayList<GrantedAuthority>();
			for (Role role : this.roles) {
				if (Role.STATUS_NORMAL.equals(role.getStatus())) {
					grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_"
							+ role.getRoleId().toString()));
				}
			}
		}
		return grantedAuthoritys;
	}

	@Override
	public boolean isAccountNonExpired() {
		return STATUS_NORMAL.equals(getStatus());
	}

	@Override
	public boolean isAccountNonLocked() {
		return STATUS_NORMAL.equals(getStatus());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return STATUS_NORMAL.equals(getStatus());
	}

	@Override
	public boolean isEnabled() {
		return STATUS_NORMAL.equals(getStatus());
	}

}
