package com.tobeto.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tobeto.business.abstracts.UserService;
import com.tobeto.dataAccess.UserRepository;
import com.tobeto.entities.concretes.User;

import jakarta.transaction.Transactional;

@Service
public class UserManager implements UserService {

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional // satır 27'de
	public Optional<User> getUser(String email) {
		Optional<User> users = userRepository.findByEmail(email);
		if (users.isPresent()) {
			users.get().getRoles();
		}
		return users;
	}

	// kontrol et
	@Override
	public void create(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);

	}

	@Override
	public List<User> getAll() {
		List<User> users = userRepository.findAll();
		if (users != null) {
			users.stream().map(user -> user.getRoles());
		}
		return users;
	}

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

	@Override
	public void delete(UUID id) {
		User user = userRepository.findById(id).orElseThrow();
		userRepository.delete(user);
	}

	@Override
	public void update(User entity) {
	}
}
