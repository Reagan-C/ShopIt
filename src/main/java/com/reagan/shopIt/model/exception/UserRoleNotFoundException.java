package com.reagan.shopIt.model.exception;

public class UserRoleNotFoundException extends ShopItException{
    private static final long serialVersionUID = 1L;
    public static final String ENTITY_NAME = "Role";

    private Object roleId = null;

    public UserRoleNotFoundException(Object roleId) {
        this.roleId = roleId;
    }

    @Override
    public String getMessage() {
        return String.format("%s with this title %s cannot be found or does not exist in our record",
                ENTITY_NAME, roleId.toString());
    }
}
