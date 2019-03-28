package com.grudzinski.docugen.base.models;

public enum PaymentMethod {
    CASH("CASH"), TRANSFER("TRANSFER");

    private final String method;

    PaymentMethod(String paymentMethod) {
        method = paymentMethod;
    }
    
//    @Override
//    public String toString() {
//        return method;
//    }
}
