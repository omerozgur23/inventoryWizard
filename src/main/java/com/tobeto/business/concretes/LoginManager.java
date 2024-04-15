package com.tobeto.business.concretes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.LoginService;
import com.tobeto.business.abstracts.UserService;
import com.tobeto.core.utilities.config.jwt.JwtConfig;
import com.tobeto.entities.concretes.User;

@Service
public class LoginManager implements LoginService {

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private UserService userService;

	@Override
	public String login(String email, String password) {
		Optional<User> oUser = userService.getUserByEmail(email);
		if (oUser.isPresent() && oUser.get().getPassword().equals(password)) {
			String token = jwtConfig.createToken(oUser.get());
			return token;
		} else {
			throw new RuntimeException("Login Error");
		}
	}

}
