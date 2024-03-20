package com.tobeto.business.rules.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.UserRepository;
import com.tobeto.entities.concretes.User;

@Service
public class UserBusinessRules {

	@Autowired
	private UserRepository userRepository;

	public void checkIfEmailExist(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			throw new BusinessException(Messages.USER_EMAIL_ALREADY_EXISTS);
		}
	}

}
