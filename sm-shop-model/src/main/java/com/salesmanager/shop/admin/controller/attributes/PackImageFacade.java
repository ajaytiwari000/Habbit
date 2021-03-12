package com.salesmanager.shop.admin.controller.attributes;

import com.salesmanager.core.model.catalog.product.PackIcon;
import org.springframework.web.multipart.MultipartFile;

public interface PackImageFacade {
  void addPackIcon(Long id, MultipartFile[] files);

  void removePackIcon(Long packIcon, Long packId);

  PackIcon getPackImageByPackId(Long id);
}
