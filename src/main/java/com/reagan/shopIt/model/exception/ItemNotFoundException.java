package com.reagan.shopIt.model.exception;

public class ItemNotFoundException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Item";

    private Object name = null;

    public ItemNotFoundException(Object name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("we currently do not have enough quantity of %s to satisfy " +
                        "your request",
                name.toString());
    }
}
