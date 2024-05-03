package org.udemy.foodservice.orderservice.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.udemy.foodservice.orderservice.domain.dto.message.PaymentResponse;
import org.udemy.foodservice.orderservice.domain.ports.input.messagelistener.payment.ForPaymentResponseMessages;

@Slf4j
@Validated
@Service
public class PaymentResponseMessageListener implements ForPaymentResponseMessages {
    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {

    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {

    }
}
