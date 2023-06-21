package com.reagan.shopIt.model.exception;

public class ShopItException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private Integer code = 900;
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
