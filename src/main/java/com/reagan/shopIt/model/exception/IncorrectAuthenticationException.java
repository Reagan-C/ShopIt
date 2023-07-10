package com.reagan.shopIt.model.exception;

public class IncorrectAuthenticationException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = null;

    @Override
    public String getMessage() {
        return "Username or password incorrect";
    }
}
