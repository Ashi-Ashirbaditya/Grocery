package net.ads.grocery.service;

import net.ads.grocery.model.Products;
import net.ads.grocery.repository.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public void saveProducts(Products product) {
        productsRepository.save(product);
    }
    public Products getProductsById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }
    public void deleteProductsById(Long id) {
        productsRepository.deleteById(id);
    }

    public Page<Products> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.productsRepository.findAll(pageable);
    }
}
