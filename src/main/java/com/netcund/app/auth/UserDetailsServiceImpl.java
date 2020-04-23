package com.netcund.app.auth;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.netcund.app.entities.ApplicationRoleEntity;
import com.netcund.app.entities.ApplicationUserEntity;
import com.netcund.app.repositories.ApplicationUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
    private ApplicationUserRepository applicationUserRepository;

    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUserEntity applicationUser = applicationUserRepository.findByName(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }
        
        List<ApplicationRoleEntity> roles = applicationUser.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role)));
        return new User(applicationUser.getName(), applicationUser.getPassword(), authorities);
    }
}
