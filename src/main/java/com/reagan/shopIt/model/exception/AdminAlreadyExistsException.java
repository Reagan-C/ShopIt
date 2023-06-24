package com.reagan.shopIt.model.exception;

public class AdminAlreadyExistsException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    private Object entityEmail = null;

    public AdminAlreadyExistsException(Object entityEmail) {
        this.entityEmail = entityEmail;
    }

    @Override
    public String getMessage() {
        return String.format("%s with an email %s is already an admin!", ENTITY_NAME, entityEmail.toString());
    }
}
