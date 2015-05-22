package com.p.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.p.model.Role;
import com.p.model.modelAux.UsuarioSpring;

@Service("userDetailsService")
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersService usersService;

	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {

		com.p.model.User domainUser = usersService.getByEmail(login);

		if (domainUser != null) {
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;

			return new UsuarioSpring(domainUser.getEmail(),
					domainUser.getPassword(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked,
					getAuthorities(domainUser.getRole()),
					domainUser.getId());
		} else {
			return null;
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	public List<String> getRoles(Role role) {

		List<String> roles = new ArrayList<String>();

		roles.add(role.toString());
		return roles;
	}

	public static List<GrantedAuthority> getGrantedAuthorities(
			List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

}
