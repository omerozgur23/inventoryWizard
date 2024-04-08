package com.tobeto.business.abstracts;

import java.util.Optional;
import java.util.UUID;

import com.tobeto.entities.concretes.User;

public interface UserService extends BaseService<User> {
	Optional<User> getUser(String email);

	String getUserRoles(User user);

	User getUser(UUID userId);

}
