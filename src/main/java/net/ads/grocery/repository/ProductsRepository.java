package net.ads.grocery.repository;

import net.ads.grocery.model.Products;
import net.ads.grocery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
