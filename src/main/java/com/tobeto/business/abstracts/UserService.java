package com.tobeto.business.abstracts;

import java.util.List;
import java.util.UUID;

import com.tobeto.entities.concretes.Roles;
import com.tobeto.entities.concretes.User;

public interface UserService extends BaseService<User> {

	String getUserRoles(User user);

	User getUser(UUID userId);

	boolean changePassword(String lastPassword, String newPassword, String email);

	List<Roles> getAllRoles();

}
