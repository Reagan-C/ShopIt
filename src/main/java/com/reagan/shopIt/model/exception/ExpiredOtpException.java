package com.reagan.shopIt.model.exception;

public class ExpiredOtpException extends ShopItException {

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "User";

    @Override
    public String getMessage() {
        return String.format("Your OTP  has expired");
    }

}
