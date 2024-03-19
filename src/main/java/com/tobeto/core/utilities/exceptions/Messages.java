package com.tobeto.core.utilities.exceptions;

public record Messages() {

	// Category Messages
	// değişken isimleri tamamen büyük yazılır
	public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists!";
	public static final String categoryIdNotFound = "Category ID not found!";

	// User Messages
	public static final String userEmailAlreadyExists = "User email already exists!";
}
