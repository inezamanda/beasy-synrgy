package com.synrgybootcamp.project.enums;

public enum TransactionType {
    TRANSFER("Transfer"),
    PAYMENT_MOBILE("Mobile"),
    PAYMENT_MERCHANT("Merchant"),
    PAYMENT_CREDIT_CARD("Credit Card");

    private String textDisplay;

    TransactionType(String textDisplay) {
        this.textDisplay = textDisplay;
    }

    public String getTextDisplay() {
        return textDisplay;
    }
}