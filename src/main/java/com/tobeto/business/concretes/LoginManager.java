package com.tobeto.business.concretes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.LoginService;
import com.tobeto.business.rules.user.UserBusinessRules;
import com.tobeto.core.utilities.config.jwt.JwtConfig;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.entities.concretes.User;

@Service
public class LoginManager implements LoginService {

	@Autowired
	private JwtConfig jwtConfig;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserBusinessRules userBusinessRules;

	@Override
	public String login(String email, String password) {
		Optional<User> user = userBusinessRules.isUserExist(email);
		if (passwordEncoder.matches(password, user.get().getPassword())) {
			String token = jwtConfig.createToken(user.get());
			return token;
		} else {
			throw new BusinessException(Messages.WRONG_PASSWORD);
		}
	}

}
