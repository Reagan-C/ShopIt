package com.reagan.shopIt.model.exception;

public class TokenNotFoundException extends ShopItException {

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Token";

    @Override
    public String getMessage() {
        return "The token could not be found or does not exist in our record";
    }

}
