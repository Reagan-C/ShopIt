package com.reagan.shopIt.model.exception;

public class EmailNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    private Object userId = null;

    public EmailNotFoundException(Object userId) {
        this.userId = userId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this email  %s cannot be found or does not exist in our record",
                ENTITY_NAME, userId.toString());
    }
}
