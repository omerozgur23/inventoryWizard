package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.business.rules.user.UserBusinessRules;
import com.tobeto.dataAccess.RolesRepository;
import com.tobeto.dataAccess.UserRepository;
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
	/**********************************************************************/
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
	/**********************************************************************/
	@Transactional
	@Override
	public User create(User user) {

		userBusinessRules.checkIfEmailExist(user.getEmail());

//		List<Roles> roles = rolesRepository.findAll();
//		roles = roles.stream().filter(r -> !r.getRole().equals("admin")).toList();
		// ManytoMany ilişki olduğu için java sınıflarında da iki yönlü ilişki
		// oluşturmak gerekiyor.
//		user.setRoles(roles);
//		roles.forEach(r -> r.getUsers().add(user));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public List<User> getAll() {
//		List<User> users = userRepository.findAll();
//		if (users != null) {
//			users.stream().map(user -> user.getRoles());
//		}
//		return users;
		TypedQuery<User> query = entityManager.createNamedQuery("User.findAll", User.class);
		return query.getResultList();
	}

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
	/**********************************************************************/
	public boolean changePassword(String lastPassword, String newPassword, String email) {
		Optional<User> users = userRepository.findByEmail(email);
		if (users.isPresent()) {
			// kullanıcı, adına göre veritabanında bulundu
			// şifresini kontrol edelim
			User user = users.get();
			if (passwordEncoder.matches(lastPassword, user.getPassword())) {
				// şifre doğru ise şifresi yeni şifre ile güncellendi
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
				return true;
			}
		}
		return false;
	}

	/**********************************************************************/
	/**********************************************************************/
	/**********************************************************************/
	@Override
	public void delete(UUID id) {
		User user = userRepository.findById(id).orElseThrow();
		userRepository.delete(user);
	}

	@Override
	public void update(User entity) {
	}
}