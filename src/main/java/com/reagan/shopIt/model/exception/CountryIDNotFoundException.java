package com.reagan.shopIt.model.exception;

import java.io.Serial;

public class CountryIDNotFoundException extends ShopItException {

    @Serial
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Country";

    private final Object id;
    public CountryIDNotFoundException(Object countryId) {
        this.id = countryId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this ID  %s cannot be found or does not exist in our record",
                ENTITY_NAME, id.toString());
    }
}
