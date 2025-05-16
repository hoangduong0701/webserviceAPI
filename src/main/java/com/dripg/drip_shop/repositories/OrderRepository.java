package com.dripg.drip_shop.repositories;

import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
//ok
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUser(User user);
}
