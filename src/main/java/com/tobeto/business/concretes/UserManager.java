package com.tobeto.business.concretes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.business.rules.user.UserBusinessRules;
import com.tobeto.core.utilities.exceptions.BusinessException;
import com.tobeto.core.utilities.exceptions.Messages;
import com.tobeto.dataAccess.RolesRepository;
import com.tobeto.dataAccess.UserRepository;
import com.tobeto.dto.PageResponse;
import com.tobeto.entities.concretes.Roles;
import com.tobeto.entities.concretes.User;
import com.tobeto.entities.enums.Status;

import jakarta.transaction.Transactional;

@Service
public class UserManager implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserBusinessRules userBusinessRules;

	@Transactional
	@Override
	public User create(User user) {
		userBusinessRules.checkIfEmailExist(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedDate(LocalDateTime.now());
		user.setStatus(Status.ACTIVE);
		return userRepository.save(user);
	}

	@Override
	public User update(User clientUser) {
		User user = getUser(clientUser.getId());
		if (!"".equals(clientUser.getPassword()))
			user.setPassword(passwordEncoder.encode(clientUser.getPassword()));

		BeanUtils.copyProperties(clientUser, user, "id", "password", "firstName", "lastName", "createdDate", "status");
		return userRepository.save(user);
	}

	@Override
	public void delete(UUID id) {
		User userToDelete = userBusinessRules.isAdminOwnAccount(id);
		userToDelete.setStatus(Status.INACTIVE);
		userToDelete.setInactiveDate(LocalDateTime.now());
		userRepository.save(userToDelete);
	}

	@Override
	public PageResponse<User> getAll() {
		List<User> users = userRepository.findAllActive();
		int totalUserCount = userRepository.findAllActive().size();
		return new PageResponse<>(totalUserCount, users);
	}

	@Override
	public PageResponse<User> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<User> users = userRepository.findAllByPagination(pageable).getContent();
		int totalUserCount = userRepository.findAllActive().size();
		return new PageResponse<>(totalUserCount, users);
	}

	@Override
	public PageResponse<User> searchItem(String keyword) {
		List<User> users = userRepository.searchUser(keyword);
		int totalUserCount = userRepository.searchUser(keyword).size();
		return new PageResponse<User>(totalUserCount, users);
	}

	@Override
	public boolean changePassword(String lastPassword, String newPassword, String email) {
		Optional<User> user = userBusinessRules.checkIfEmailExist(email);

		if (passwordEncoder.matches(lastPassword, user.get().getPassword())) {
			user.get().setPassword(passwordEncoder.encode(newPassword));
			userRepository.save(user.get());
			return true;
		} else {
			throw new BusinessException(Messages.INCORRECT_LAST_PASSWORD);
		}
	}

	@Override
	public User getUser(UUID userId) {
		Optional<User> oUser = userRepository.findById(userId);
		User user = null;
		if (oUser.isPresent()) {
			user = oUser.get();
		} else {
			throw new BusinessException(Messages.USER_ID_NOT_FOUND);
		}
		return user;
	}

	@Override
	public String getUserRoles(User user) {
		if (user.getRoles().size() > 0) {
			String s = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.joining(","));
			return s;
		} else {
			throw new BusinessException(Messages.ROLE_NOT_FOUND);
		}
	}

	@Override
	public List<Roles> getAllRoles() {
		return rolesRepository.findAll();
	}
}
