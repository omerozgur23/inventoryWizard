package com.tobeto.core.utilities.exceptions;

public record Messages() {

	// Category Messages
	// değişken isimleri tamamen büyük yazılır
	public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists!";
	public static final String CATEGORY_ID_NOT_FOUND = "Category ID not found!";

	// User Messages
	public static final String USER_EMAIL_ALREADY_EXISTS = "User email already exists!";
}
