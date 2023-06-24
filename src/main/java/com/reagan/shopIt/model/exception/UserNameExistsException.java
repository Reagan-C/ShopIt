package com.reagan.shopIt.model.exception;

public class UserNameExistsException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    @Override
    public String getMessage() {
        return String.format("%s already exists with this username in the record", ENTITY_NAME);
    }
}
