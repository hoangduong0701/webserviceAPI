package com.dripg.drip_shop.services;

import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.entities.Order;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
//ok
@Component
public class PaymentIntentService {
    public Map<String, String> createPaymentIntent(Order order) throws StripeException {
        User user = order.getUser();
        Map<String, String> metaData = new HashMap<>();
        metaData.put("orderId",order.getId().toString());
        PaymentIntentCreateParams paymentIntentCreateParams= PaymentIntentCreateParams.builder()
                .setAmount((long) (order.getTotalAmount() * 100))
                .setCurrency("usd")
                .putAllMetadata(metaData)
                .setDescription("Test Payment Project -1")
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCreateParams);
        Map<String, String> map = new HashMap<>();
        map.put("client_secret", paymentIntent.getClientSecret());
        return map;
    }
}
