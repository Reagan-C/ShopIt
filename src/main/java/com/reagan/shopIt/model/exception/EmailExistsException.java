package com.reagan.shopIt.model.exception;

public class EmailExistsException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    @Override
    public String getMessage() {
        return String.format("%s already exists with this email in the record", ENTITY_NAME);
    }
}
