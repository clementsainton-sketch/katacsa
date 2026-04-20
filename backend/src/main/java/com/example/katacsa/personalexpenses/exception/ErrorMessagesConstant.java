package com.example.katacsa.personalexpenses.exception;

class ErrorMessagesConstant {
    private ErrorMessagesConstant() throws InstantiationException {
        throw new InstantiationException("Cannot be instantiated");
    }

    static final String AMOUNT_VALUE_ERROR = "Amount value is wrong";
    static final String INCOHERENT_DATES = "Incoherent dates";
    static final String FUTURE_DATE_EXCEPTION = "This date cannot be in the future";
}
