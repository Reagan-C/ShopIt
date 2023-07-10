package com.reagan.shopIt.model.exception;

import org.joda.time.Days;

public class InsufficientDaysBeforeUpdateException extends ShopItException {
    private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return ("You must wait for 7 days before you can perform another update operation");
    }
}
