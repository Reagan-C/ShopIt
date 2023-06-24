package com.reagan.shopIt.model.exception;

public class CategoryNotFoundException extends ShopItException{

    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Category";

    private Object name = null;

    public CategoryNotFoundException(Object name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("A %s with a name %s cannot be found or does not exist in our record",
                ENTITY_NAME, name.toString());
    }
}
