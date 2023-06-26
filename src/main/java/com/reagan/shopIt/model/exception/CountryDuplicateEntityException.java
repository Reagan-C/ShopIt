package com.reagan.shopIt.model.exception;

public class CountryDuplicateEntityException extends ShopItException {
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Country";

    @Override
    public String getMessage() {
        return String.format("%s entry already exists in record.", ENTITY_NAME);
    }
}
