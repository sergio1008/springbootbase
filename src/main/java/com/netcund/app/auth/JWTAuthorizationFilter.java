package com.netcund.app.auth;

import static com.netcund.app.auth.SecurityConstants.HEADER_STRING;
import static com.netcund.app.auth.SecurityConstants.SECRET;
import static com.netcund.app.auth.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.netcund.app.entities.ApplicationRoleEntity;
import com.netcund.app.entities.ApplicationUserEntity;
import com.netcund.app.repositories.ApplicationUserRepository;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	@Autowired
	private ApplicationUserRepository repoUser;
	
    public JWTAuthorizationFilter(AuthenticationManager authManager, ApplicationUserRepository repoUser) {
        super(authManager);
        this.repoUser = repoUser;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);
        
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();

            if (user != null) {
            	ApplicationUserEntity userEntity = this.repoUser.findByName(user);
            	List<ApplicationRoleEntity> roles = userEntity.getRoles();
        		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        		roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }
            return null;
        }
        return null;
    }
}
