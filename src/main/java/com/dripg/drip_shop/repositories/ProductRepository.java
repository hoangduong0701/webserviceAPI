package com.dripg.drip_shop.repositories;

import com.dripg.drip_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

//ok
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product > {

    Product findBySlug(String slug);
    List<Product> findByIsNewArrivalTrue();


}
