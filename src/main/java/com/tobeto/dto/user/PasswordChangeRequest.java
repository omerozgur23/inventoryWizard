package com.tobeto.dto.user;

import lombok.Data;

@Data
public class PasswordChangeRequest {

	private String lastPassword;

	private String newPassword;
}
