package com.reagan.shopIt.model.exception;

public class CategoryNameNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Category";

    private Object name = null;

    public CategoryNameNotFoundException(Object categoryName) {
        this.name = categoryName;
    }

    @Override
    public String getMessage() {
        return String.format("%s with name  %s, cannot be found or does not exist in our record",
                ENTITY_NAME, name.toString());
    }
}
