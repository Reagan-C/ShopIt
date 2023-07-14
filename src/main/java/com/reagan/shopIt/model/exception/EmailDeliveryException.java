package com.reagan.shopIt.model.exception;

public class EmailDeliveryException extends ShopItException {

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Email";

    @Override
    public String getMessage() {
        return String.format(" An error  occurred and %s message delivery has failed", ENTITY_NAME);
    }
}
