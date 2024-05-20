package com.tobeto.core.utilities.exceptions;

public record Messages() {

	// Empty Method Message
	public static final String THIS_METHOD_DOES_NOT_WORK = "This method does not work!";

	public static final String EMAIL_MUST_BE_VALID = "Email address must be valid";

	// Login Messages
	public static final String WRONG_PASSWORD = "Wrong password";

	// Success Messages
	public static final String SUCCESSFULLY = "Successfully";

	// Product Messages
	public static final String PRODUCT_ID_NOT_FOUND = "Product ID not found!";
	public static final String PRODUCT_ID_CANNOT_BE_NULL = "Product id cannot be null";
	public static final String PRODUCT_NAME_CANNOT_BE_NULL = "Category name cannot be null";
	public static final String PRODUCT_NAME_CANNOT_BE_BLANK = "Category name cannot be blank";
	public static final String CRITICAL_COUNT_MUST_BE_POSITIVE = "Critical count must be positive";
	public static final String PURCHASE_PRICE_MUST_BE_POSITIVE = "Purchase price must be positive";
	public static final String UNIT_PRICE_MUST_BE_POSITIVE = "Unit price must be positive";

	// Category Messages
	public static final String CATEGORY_NAME_ALREADY_EXISTS = "Category name already exists!";
	public static final String CATEGORY_ID_NOT_FOUND = "Category ID not found!";
	public static final String CATEGORY_ID_CANNOT_BE_NULL = "Category id cannot be null";
	public static final String CATEGORY_NAME_CANNOT_BE_NULL = "Category name cannot be null";
	public static final String CATEGORY_NAME_CANNOT_BE_BLANK = "Category name cannot be blank";
	public static final String CATEGORY_NAME_TOO_SHORT = "Category name can be minimum two characters";

	// User Messages
	public static final String USER_EMAIL_ALREADY_EXISTS = "User email already exists!";
	public static final String USER_ID_NOT_FOUND = "User ID not found!";
	public static final String INCORRECT_LAST_PASSWORD = "Incorrect last password";
	public static final String ADMIN_CANNOT_DELETE_ITSELF = "Admin cannot delete itself";
	public static final String USER_ID_CANNOT_BE_NULL = "User ID cannot be null";
	public static final String FIRST_NAME_CANNOT_BE_NULL = "First name cannot be null";
	public static final String FIRST_NAME_CANNOT_BE_BLANK = "First name cannot be blank";
	public static final String LAST_NAME_CANNOT_BE_NULL = "Last name cannot be null";
	public static final String LAST_NAME_CANNOT_BE_BLANK = "Last name cannot be blank";
	public static final String PASSWORD_MUST_BE_6_CHARACTERS = "Password must be at least 6 characters";
	public static final String ROLES_LIST_CANNOT_BE_NULL = "Roles list cannot be null";
	public static final String ROLES_LIST_CANNOT_BE_BLANK = "Roles list cannot be blank";

	// Shelf Messages
	public static final String SHELF_ID_NOT_FOUND = "Shelf ID not found!";
	public static final String WAREHOUSE_FULL = "Warehouse full. You can't create new shelf!";
	public static final String NO_EMPTY_SHELF = "There are no empty shelf!";
	public static final String NO_SHELVES_AVAILABLE_FOR_SELLING_PRODUCT = "No shelves available for selling the product!";
	public static final String SHELF_IS_NOT_EMPTY = "Shelf is not empty!";
	public static final String CAPACITY_CANNOT_BE_BIG = "Shelf capacity cannot be greate than 5";
	public static final String COUNT_MUST_BE_POSITIVE = "Count must be positive";
	public static final String CAPACITY_MUST_BE_POSITIVE = "Capacity must be positive";
	public static final String SHELF_ID_CANNOT_BE_NULL = "Shelf ID cannot be null";
	public static final String CAPACITY_MUST_BE_LESS_THAN_OR_EQUAL_TO_10 = "Capacity must be less than or equal to 10";
	public static final String MAX_COUNT_AT_ONE_TIME = "A maximum of 10 shelves can be created at one time";

	// Customer Messages
	public static final String COMPANY_NAME_ALREADY_EXISTS = "Company name already exists!";
	public static final String TAX_NUMBER_ALREADY_EXISTS = "Tax number already exists!";
	public static final String EMAIL_ALREADY_EXISTS = "Email already exists!";
	public static final String CUSTOMER_ID_NOT_FOUND = "Customer ID not found!";
	public static final String CUSTOMER_COMPANY_NAME_CANNOT_BE_NULL = "Company name cannot be null";
	public static final String CUSTOMER_COMPANY_NAME_CANNOT_BE_BLANK = "Company name cannot be blank";
	public static final String CUSTOMER_CONTACT_NAME_CANNOT_BE_NULL = "Contact name cannot be null";
	public static final String CUSTOMER_CONTACT_NAME_CANNOT_BE_BLANK = "Contact name cannot be blank";
	public static final String CUSTOMER_CONTACT_EMAIL_CANNOT_BE_NULL = "Contact email cannot be null";
	public static final String CUSTOMER_CONTACT_EMAIL_CANNOT_BE_BLANK = "Contact email cannot be blank";
	public static final String CUSTOMER_CONTACT_PHONE_CANNOT_BE_NULL = "Contact phone cannot be null";
	public static final String CUSTOMER_CONTACT_PHONE_CANNOT_BE_BLANK = "Contact phone cannot be blank";
	public static final String CUSTOMER_TAX_NUMBER_CANNOT_BE_NULL = "Tax number cannot be null";
	public static final String CUSTOMER_TAX_NUMBER_CANNOT_BE_BLANK = "Tax number cannot be blank";
	public static final String CUSTOMER_ADDRESS_CANNOT_BE_NULL = "Address cannot be null";
	public static final String CUSTOMER_ADDRESS_CANNOT_BE_BLANK = "Address email cannot be blank";
	public static final String CUSTOMER_ID_CANNOT_BE_NULL = "Customer id cannot be null";

	// Order Messages
	public static final String ORDER_ID_NOT_FOUND = "Order ID not found!";
	public static final String ORDER_STATUS_ALREADY_FALSE = "Order status already false";
	public static final String AN_INVOICE_CANNOT_BE_CRATED_FOR_CANCELED_ORDER = "An invoice cannot be created for a canceled order";

	// Invoice Messages
	public static final String INVOICE_ID_NOT_FOUND = "Invoice ID not found!";
	public static final String INVOICE_ALREADY_EXIST = "This order already has an invoice.";
	public static final String INVOICE_STATUS_ALREADY_FALSE = "Invoice status already false";
	public static final String ORDER_ID_CANNOR_BE_NULL = "Order ID cannot be null";
	public static final String INVOICE_ID_CANNOR_BE_NULL = "Invoice ID cannot be null";

	// Role Messages
	public static final String ROLE_NOT_FOUND = "Role not found";

	// Supplier Messages
	public static final String SUPPLIER_ID_NOT_FOUND = "Supplier ID not found!";
	public static final String SUPPLIER_ID_CANNOT_BE_NULL = "Supplier id cannot be null";
	public static final String SUPPLIER_COMPANY_NAME_CANNOT_BE_BLANK = "Company name cannot be blank";
	public static final String SUPPLIER_COMPANY_NAME_CANNOT_BE_NULL = "Company name cannot be null";
	public static final String SUPPLIER_CONTACT_NAME_CANNOT_BE_NULL = "Contact name cannot be null";
	public static final String SUPPLIER_CONTACT_NAME_CANNOT_BE_BLANK = "Contact name cannot be blank";
	public static final String SUPPLIER_CONTACT_EMAIL_CANNOT_BE_NULL = "Contact email cannot be null";
	public static final String SUPPLIER_CONTACT_EMAIL_CANNOT_BE_BLANK = "Contact email cannot be blank";
	public static final String SUPPLIER_CONTACT_PHONE_CANNOT_BE_NULL = "Contact phone cannot be null";
	public static final String SUPPLIER_CONTACT_PHONE_CANNOT_BE_BLANK = "Contact phone cannot be blank";
	public static final String SUPPLIER_TAX_NUMBER_CANNOT_BE_NULL = "Tax number cannot be null";
	public static final String SUPPLIER_TAX_NUMBER_CANNOT_BE_BLANK = "Tax number cannot be blank";
	public static final String SUPPLIER_ADDRESS_CANNOT_BE_NULL = "Address cannot be null";
	public static final String SUPPLIER_ADDRESS_CANNOT_BE_BLANK = "Address email cannot be blank";

}
