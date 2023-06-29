package com.reagan.shopIt.model.exception;

public class CountryNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Country";

    private Object countryTitle = null;

    public CountryNotFoundException(Object countryTitle) {
        this.countryTitle = countryTitle;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this title  %s cannot be found",
                ENTITY_NAME, countryTitle.toString());
    }
}
