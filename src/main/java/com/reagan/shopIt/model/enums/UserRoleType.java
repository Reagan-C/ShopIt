package com.reagan.shopIt.model.enums;

import lombok.Getter;

@Getter
public enum UserRoleType {
    
    ADMINISTRATOR("Administrator"),
    
    REGULAR("Regular");

    private final String value;

    UserRoleType(String value) {
        this.value = value;
    }
}
