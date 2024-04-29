package org.udemy.foodservice.orderservice.domain.ports.input.messagelistener.payment;

import org.udemy.foodservice.orderservice.domain.dto.message.PaymentResponse;

public interface ForPaymentResponseMessages {
    void paymentCompleted(PaymentResponse paymentResponse);
    void paymentCancelled(PaymentResponse paymentResponse);
}
