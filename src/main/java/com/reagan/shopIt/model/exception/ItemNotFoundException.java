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
        return String.format("%s with this name %s is out of stock",
                ENTITY_NAME, name.toString());
    }
}
