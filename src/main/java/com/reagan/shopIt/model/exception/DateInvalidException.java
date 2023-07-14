package com.reagan.shopIt.model.exception;

public class DateInvalidException extends ShopItException{

    private static final long serialVersionUID = 1L;
    private String invalidDate;

    public DateInvalidException(String invalidDate){
        this.invalidDate = invalidDate;
    }

    @Override
    public String getMessage() {
        return String.format("Supplied date, %s, is invalid. Ensure date is correct" +
                " in this format: YYYY-MM-DD.", invalidDate);
    }
}
