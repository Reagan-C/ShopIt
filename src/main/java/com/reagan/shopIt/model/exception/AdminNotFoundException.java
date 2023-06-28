package com.reagan.shopIt.model.exception;

public class AdminNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    private Object userId = null;

    public AdminNotFoundException(Object userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this email address %s is not an admin",
                ENTITY_NAME, userId.toString());
    }
}
