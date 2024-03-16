package com.tobeto.core.utilities.config.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tobeto.entities.concretes.Roles;
import com.tobeto.entities.concretes.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtConfig {

	@Value("${SECRET_KEY}")
	private String KEY;

	public String createToken(User user) {
		JwtBuilder builder = Jwts.builder();

		List<Roles> userRoles = user.getRoles();

		String[] roles = new String[userRoles.size()];
		for (int i = 0; i < userRoles.size(); i++) {
			roles[i] = userRoles.get(i).getRole();
		}

		// add custom keys
		Map<String, Object> customKeys = new HashMap<>();
		customKeys.put("roles", roles);
		builder = builder.claims(customKeys);

		Instant date = Instant.now().plus(15, ChronoUnit.MINUTES);

		builder = builder.subject("login").id(user.getEmail()).issuedAt(new Date()).expiration(Date.from(date));

		return builder.signWith(getKey()).compact();
	}

	public Claims tokenControl(String token) {
		JwtParser builder = Jwts.parser().verifyWith(getKey()).build();
		return builder.parseSignedClaims(token).getPayload();
	}

	private SecretKey getKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(KEY));
		return key;
	}

	public static void main(String[] args) {
		keyUret();
	}

	public static void keyUret() {
		SecretKey key = Jwts.SIG.HS512.key().build();
		String str = Encoders.BASE64.encode(key.getEncoded());
		System.out.println(str);
	}
}
