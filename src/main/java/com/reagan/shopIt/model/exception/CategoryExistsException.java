package com.reagan.shopIt.model.exception;

public class CategoryExistsException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "A category";

    @Override
    public String getMessage() {
        return String.format("%s already exists with this name or abbreviation in the record", ENTITY_NAME);
    }
}
