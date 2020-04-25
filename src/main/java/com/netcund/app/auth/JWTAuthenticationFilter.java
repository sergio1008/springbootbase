package com.netcund.app.auth;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.netcund.app.auth.SecurityConstants.EXPIRATION_TIME;
import static com.netcund.app.auth.SecurityConstants.HEADER_STRING;
import static com.netcund.app.auth.SecurityConstants.SECRET;
import static com.netcund.app.auth.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcund.app.dto.ApplicationUserDTO;
import com.netcund.app.entities.ApplicationRoleEntity;
import com.netcund.app.entities.ApplicationUserEntity;
import com.netcund.app.repositories.ApplicationUserRepository;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
   
	private ApplicationUserRepository repoUser;	

	private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationUserRepository repoUser) {
        this.authenticationManager = authenticationManager;
        this.repoUser = repoUser;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUserDTO creds = new ObjectMapper()
                    .readValue(req.getInputStream(), ApplicationUserDTO.class);
        	ApplicationUserEntity userEntity = this.repoUser.findByLogin(creds.getLogin());
        	List<ApplicationRoleEntity> roles = userEntity.getRoles();
    		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getLogin(),
                            creds.getPassword(),
                            authorities)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
    	ApplicationUserEntity userEntity = this.repoUser.findByLogin(((User) auth.getPrincipal()).getUsername());
    	List<ApplicationRoleEntity> roles = userEntity.getRoles();
		List<String> authorities = new ArrayList<>();
		roles.forEach(role -> authorities.add("ROLE_" + role.getName()));
		String[] str = new String[authorities.size()];
        for (int j = 0; j < authorities.size(); j++) { 
            str[j] = authorities.get(j); 
        } 
        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withArrayClaim("CLAIM_TOKEN",str)
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
    
}