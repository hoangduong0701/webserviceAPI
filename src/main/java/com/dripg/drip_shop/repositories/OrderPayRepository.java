package com.dripg.drip_shop.repositories;

import com.dripg.drip_shop.entities.OrderPay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPayRepository extends JpaRepository<OrderPay, Long> {

}
