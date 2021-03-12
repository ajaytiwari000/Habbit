package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.model.catalog.product.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
  @Query("select p from Product p where p.sku = ?1")
  Optional<Product> getBySkuId(String sku);

  @Query("select p from Product p where p.id = ?1")
  Optional<Product> getByProductId(Long id);
}
