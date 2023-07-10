package com.reagan.shopIt.model.exception;

public class ConfirmedPasswordChangeOtpException extends ShopItException{


        private static final long serialVersionUID = 1L;
        public static final String ENTITY_NAME = "ShopIT";

        @Override
        public String getMessage() {
            return ("This OTP has already been used for password change operation");
        }

    }
