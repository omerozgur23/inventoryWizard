package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
import com.tobeto.entities.concretes.PageResponse;
import com.tobeto.entities.concretes.Roles;
import com.tobeto.entities.concretes.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
	private EntityManager entityManager;

	@Autowired
	private UserBusinessRules userBusinessRules;

	/**********************************************************************/
	/**********************************************************************/
	@Transactional
	@Override
	public User create(User user) {
		userBusinessRules.checkIfEmailExist(user.getEmail());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public User update(User clientUser) {
		User user = userRepository.findById(clientUser.getId()).orElseThrow();
		clientUser.setPassword(passwordEncoder.encode(clientUser.getPassword()));
		user.setEmail(clientUser.getEmail());
		user.setPassword(clientUser.getPassword());
		return userRepository.save(user);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		User user = userRepository.findById(id).orElseThrow();
		userRepository.delete(user);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<User> getAll() {
//		List<User> users = userRepository.findAll();
//		if (users != null) {
//			users.stream().map(user -> user.getRoles());
//		}
//		return users;
		TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);
//		return query.getResultList();
		List<User> users = userRepository.findAll();
		int totalShelvesCount = userRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, users);
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public PageResponse<User> getAllByPage(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		List<User> users = userRepository.findAll(pageable).getContent();
		int totalShelvesCount = userRepository.findAll().size();
		return new PageResponse<>(totalShelvesCount, users);
	}

	/**********************************************************************/
	/**********************************************************************/
	public String getUserRoles(User user) {
		if (user.getRoles().size() > 0) {
			String s = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.joining(","));
			return s;
		} else {
			return "Rol yok";
		}
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public boolean changePassword(String lastPassword, String newPassword, String email) {
		Optional<User> users = userRepository.findByEmail(email);
		if (users.isPresent()) {
			User user = users.get();

			if (passwordEncoder.matches(lastPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
				return true;
			} else {
				throw new BusinessException(Messages.INCORRECT_LAST_PASSWORD);
			}
		} else {
			throw new BusinessException(Messages.USER_ID_NOT_FOUND);
		}

	}

	/**********************************************************************/
	/**********************************************************************/
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

	/**********************************************************************/
	/**********************************************************************/
	@Override
	@Transactional
	public Optional<User> getUser(String email) {
		Optional<User> users = userRepository.findByEmail(email);
		if (users.isPresent()) {
			users.get().getRoles();
		}
		return users;
	}

	/**********************************************************************/
	/**********************************************************************/
	@Override
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public List<User> searchItem(String keyword) {
		return userRepository.searchUser(keyword);
	}

	@Override
	public List<Roles> getAllRoles() {
		return rolesRepository.findAll();
	}
}
