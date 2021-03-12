package com.salesmanager.core.business.repositories.catalog.product;

import com.salesmanager.core.model.catalog.product.image.ProductStickerImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductStickerImageRepository
    extends JpaRepository<ProductStickerImage, Long>, ProductStickerImageRepositoryCustom {
  @Query("select distinct ps from ProductStickerImage ps where ps.skuId= ?1")
  Optional<ProductStickerImage> getBySkuId(String skuId);

  @Query("select ps from ProductStickerImage ps where ps.id = ?1")
  Optional<ProductStickerImage> getById(Long id);
}
