package com.reagan.shopIt.model.exception;

public class InvalidOtpException extends ShopItException {

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "ShopIT";

    @Override
    public String getMessage() {
        return String.format("%s OTP code is invalid or has expired", ENTITY_NAME);
    }

}
