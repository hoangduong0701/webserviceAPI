package com.dripg.drip_shop.services;

import com.dripg.drip_shop.Exceptions.ResourceNotFoundEx;
import com.dripg.drip_shop.dto.ProductDto;
import com.dripg.drip_shop.entities.*;
import com.dripg.drip_shop.mapper.ProductMapper;
import com.dripg.drip_shop.repositories.ProductRepository;
import com.dripg.drip_shop.specification.ProductSpecification;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductServiceIml implements ProductService{
//ok
@Autowired
private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = productMapper.mapToProductEntity(productDto);
        return productRepository.save(product);
    }

    @Override
    public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId) {

        Specification<Product> productSpecification= Specification.where(null);

        if(null != categoryId){
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if(null != typeId){
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(typeId));
        }

        List<Product> products = productRepository.findAll(productSpecification);
        return productMapper.getProductDtos(products);
    }

    @Override
    public List<ProductDto> getNewArrivals() {
        List<Product> products = productRepository.findByIsNewArrivalTrue();
        return products.stream()
                .map(productMapper::mapProductToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductBySlug(String slug) {
        Product product= productRepository.findBySlug(slug);
        if(null == product){
            throw new ResourceNotFoundEx("Product Not Found!");
        }
        ProductDto productDto = productMapper.mapProductToDto(product);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setVariants(productMapper.mapProductVariantListToDto(product.getProductVariants()));
        productDto.setProductResources(productMapper.mapProductResourcesListDto(product.getResources()));
        return productDto;
    }

    @Override
    public ProductDto getProductById(UUID id) {
        Product product= productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEx("Product Not Found!"));
        ProductDto productDto = productMapper.mapProductToDto(product);
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setCategoryTypeId(product.getCategoryType().getId());
        productDto.setVariants(productMapper.mapProductVariantListToDto(product.getProductVariants()));
        productDto.setProductResources(productMapper.mapProductResourcesListDto(product.getResources()));
        return productDto;
    }

    @Override
    public Product updateProduct(ProductDto productDto, UUID id) {
        Product product= productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundEx("Product Not Found!"));
        productDto.setId(product.getId());
        return productRepository.save(productMapper.mapToProductEntity(productDto));
    }

    @Override
    public Product fetchProductById(UUID id) throws Exception {
        return productRepository.findById(id).orElseThrow(BadRequestException::new);
    }

    @Override
    public void deleteProduct(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundEx("Product Not Found!"));
        productRepository.delete(product);
    }


}
