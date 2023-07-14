package com.reagan.shopIt.model.exception;

public class CategoryIDNotFoundException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Category";

    private Object id = null;

    public CategoryIDNotFoundException(Object id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("%s with  ID %s cannot be found or does not exist in our record",
                ENTITY_NAME, id.toString());
    }
}
