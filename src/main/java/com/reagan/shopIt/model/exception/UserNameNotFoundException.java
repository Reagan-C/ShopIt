package com.reagan.shopIt.model.exception;

public class UserNameNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    private Object username = null;

    public UserNameNotFoundException(Object username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this username %s cannot be found or does not exist in our record",
                ENTITY_NAME, username.toString());
    }
}
