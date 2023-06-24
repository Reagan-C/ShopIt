package com.reagan.shopIt.model.exception;

public class SetAuthenticationFailureException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String message = "Cannot set user authentication";

    public SetAuthenticationFailureException() {
    }
}
