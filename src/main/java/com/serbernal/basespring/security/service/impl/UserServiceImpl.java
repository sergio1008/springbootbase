package com.serbernal.basespring.security.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.serbernal.basespring.dto.UserDTO;
import com.serbernal.basespring.security.persistence.entities.User;
import com.serbernal.basespring.security.persistence.repositories.UserRepository;
import com.serbernal.basespring.security.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${api.secretkey}")
	private String secretKey;
	@Override
	public UserDTO saveUser(UserDTO user) {
		User userSaved = this.repo.save(this.modelMapper.map(user, User.class));
		return this.modelMapper.map(userSaved, UserDTO.class);
	}

	@Override
	public String getToken(String username, String password) {
		User user = this.repo.findByUsernameAndPassword(username, password);
		if (user != null) {
			return getJWTToken(user.getUsername());
		}
		return null;
	}

	private String getJWTToken(String username) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, this.secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
