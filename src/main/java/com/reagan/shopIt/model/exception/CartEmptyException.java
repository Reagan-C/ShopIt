package com.reagan.shopIt.model.exception;

public class CartEmptyException extends ShopItException{

    private static final long serialVersionUID = 1L;

    private Object name = null;

    public CartEmptyException(Object name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return String.format("Dear %s, there are no items in your cart.",
                name);
    }
}
