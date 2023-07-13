package com.reagan.shopIt.model.exception;

public class PendingOrderNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Pending order";

    private Object userId = null;

    private Object id = null;

    public PendingOrderNotFoundException(Object id, Object userId ) {
        this.userId = userId;
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Pending order with ID %s and user_id %s does not exist in our record",
                id.toString(), userId.toString());
    }
}
