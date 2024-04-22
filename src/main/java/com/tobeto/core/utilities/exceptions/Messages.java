package com.tobeto.core.utilities.exceptions;

public record Messages() {

	// Product Messages
	public static final String PRODUCT_ID_NOT_FOUND = "Product ID not found!";

	// Category Messages
	public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists!";
	public static final String CATEGORY_ID_NOT_FOUND = "Category ID not found!";

	// User Messages
	public static final String USER_EMAIL_ALREADY_EXISTS = "User email already exists!";
	public static final String USER_ID_NOT_FOUND = "User ID not found!";
	public static final String INCORRECT_LAST_PASSWORD = "Incorrect last password";

	// Shelf Messages
	public static final String SHELF_ID_NOT_FOUND = "Shelf ID not found!";
	public static final String WAREHOUSE_FULL = "Warehouse full. You can't create new shelf!";
	public static final String NO_EMPTY_SHELF = "There are no empty shelf!";
	public static final String NO_SHELVES_AVAILABLE_FOR_SELLING_PRODUCT = "No shelves available for selling the product!";
	public static final String SHELF_IS_NOT_EMPTY = "Shelf is not empty!";
	public static final String CAPACITY_CANNOT_BE_BIG = "Shelf capacity cannot be greate than 5";

	// Customer Messages
	public static final String COMPANY_NAME_ALREADY_EXISTS = "Company name already exists!";
	public static final String TAX_NUMBER_ALREADY_EXISTS = "Tax number already exists!";
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists!";
	public static final String CUSTOMER_ID_NOT_FOUND = "Customer ID not found!";
}
