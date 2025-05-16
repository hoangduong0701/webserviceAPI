package com.dripg.drip_shop.controllers;

import com.dripg.drip_shop.entities.OrderPay;
import com.dripg.drip_shop.repositories.OrderPayRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/orderpay")
@CrossOrigin(origins = "*")
public class OrderPayController {

    private final OrderPayRepository orderRepository;

    public OrderPayController(OrderPayRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, String> payload) {
        try {
            OrderPay newOrder = new OrderPay();
            newOrder.setTotal(new BigDecimal(payload.get("total")));
            newOrder.setName("Kem Merino");

            OrderPay savedOrder = orderRepository.save(newOrder);
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(order))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<?> checkPaymentStatus(@PathVariable Long id) {
        return orderRepository.findById(id)
                .map(order -> ResponseEntity.ok(
                        Map.of("paymentStatus", order.getPaymentStatus())))
                .orElse(ResponseEntity.notFound().build());
    }
}
