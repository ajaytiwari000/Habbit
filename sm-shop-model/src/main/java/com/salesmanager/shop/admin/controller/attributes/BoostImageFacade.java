package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.model.catalog.product.BoostIcon;
import org.springframework.web.multipart.MultipartFile;

public interface BoostImageFacade {
  void removeBoostIcon(Long boostIconId, Long boostId);

  void addBoostIcon(Long id, MultipartFile[] files);

  BoostIcon getBoostImageByBoostId(Long id);
}
