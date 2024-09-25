package com.example.franchiseservice.commons;

public class Constants {

    public static final String BRANCH = "Branch";
    public static final String FRANCHISE = "Franchise";
    public static final String PRODUCT = "Product";

    public static final String NAME = " name";
    public static final String STOCK = " stock";

    public static final String IS_REQUIRED = " is required and cannot be empty or blank.";

    public static final String NAME_IS_REQUIRED = NAME + IS_REQUIRED;

    public static final String BRANCH_IS_REQUIRED = BRANCH + IS_REQUIRED;
    public static final String FRANCHISE_IS_REQUIRED = FRANCHISE + IS_REQUIRED;
    public static final String PRODUCT_IS_REQUIRED = PRODUCT + IS_REQUIRED;

    public static final String BRANCH_NAME_IS_REQUIRED = BRANCH + NAME_IS_REQUIRED;
    public static final String FRANCHISE_NAME_IS_REQUIRED = FRANCHISE + NAME_IS_REQUIRED;
    public static final String PRODUCT_NAME_IS_REQUIRED = PRODUCT + NAME_IS_REQUIRED;

    public static final String PRODUCT_STOCK_IS_REQUIRED = PRODUCT + STOCK + IS_REQUIRED;
    public static final String PRODUCT_STOCK_NEGATIVE = PRODUCT + STOCK + " cannot be less than zero.";

    public static final String NOT_FOUND_WITH_ID = " not found with Id: ";

    public static final String BRANCH_NOT_FOUND_WITH_ID = BRANCH + NOT_FOUND_WITH_ID;
    public static final String FRANCHISE_NOT_FOUND_WITH_ID = FRANCHISE + NOT_FOUND_WITH_ID;
    public static final String PRODUCT_NOT_FOUND_WITH_ID = PRODUCT + NOT_FOUND_WITH_ID;
}
