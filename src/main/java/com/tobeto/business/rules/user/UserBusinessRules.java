package com.tobeto.business.rules.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tobeto.core.utilities.SecurityUtils;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.UserRepository;
import com.tobeto.entities.concretes.User;

@Service
public class UserBusinessRules {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> checkIfEmailExist(String email) {
		Optional<User> userOptional = userRepository.findByEmail(email);
		if (userOptional.isPresent()) {
			throw new BusinessException(Messages.USER_EMAIL_ALREADY_EXISTS);
		}
		return userOptional;
	}

	public Optional<User> isUserExist(String email) {
		return userRepository.findByEmail(email);
	}

	public User isAdminOwnAccount(UUID id) {
		String authenticatedEmail = SecurityUtils.getCurrentUsername();
		User userToDelete = userRepository.findById(id)
				.orElseThrow(() -> new BusinessException(Messages.USER_ID_NOT_FOUND));
		if (userToDelete.getEmail().equals(authenticatedEmail)) {
			throw new BusinessException(Messages.ADMIN_CANNOT_DELETE_ITSELF);
		}
		return userToDelete;
	}

}
