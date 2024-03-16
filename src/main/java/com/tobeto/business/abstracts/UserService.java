package com.tobeto.business.abstracts;

import java.util.Optional;

import com.tobeto.entities.concretes.User;

public interface UserService extends BaseService<User> {
	Optional<User> getUser(String email);

}
