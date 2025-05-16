package com.dripg.drip_shop.services;

import com.dripg.drip_shop.dto.ProductDto;
import com.dripg.drip_shop.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
//ok
    public Product addProduct(ProductDto product);
    public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId);

    List<ProductDto> getNewArrivals();

    ProductDto getProductBySlug(String slug);

    ProductDto getProductById(UUID id);

    Product updateProduct(ProductDto productDto, UUID id);

    Product fetchProductById(UUID uuid) throws Exception;
    void deleteProduct(UUID id);
}
