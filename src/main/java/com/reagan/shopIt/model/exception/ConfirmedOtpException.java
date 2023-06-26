package com.reagan.shopIt.model.exception;

public class ConfirmedOtpException extends ShopItException {

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "ShopIT";

    @Override
    public String getMessage() {
        return ("The account has already been confirmed by user");
    }

}
