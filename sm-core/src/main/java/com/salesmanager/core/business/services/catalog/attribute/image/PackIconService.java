package com.salesmanager.core.business.services.catalog.attribute.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Pack;
import com.salesmanager.core.model.catalog.product.PackIcon;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PackIconService extends SalesManagerEntityService<Long, PackIcon> {

  /**
   * @param pack
   * @param packIcons
   * @throws ServiceException
   */
  void addPackIcons(Pack pack, List<PackIcon> packIcons) throws ServiceException;

  /**
   * Add a PackIcon to the persistence and an entry to the CMS
   *
   * @param id
   * @param files
   * @throws ServiceException
   */
  void addPackIcon(Long id, MultipartFile[] files) throws ServiceException;

  /**
   * @param pack
   * @param packIcon
   * @param cmsContentImage
   */
  void addPackIcon(Pack pack, PackIcon packIcon, ImageContentFile cmsContentImage)
      throws ServiceException;
  /**
   * @param packIcon
   * @param packId
   * @throws ServiceException
   */
  void removePackIcon(PackIcon packIcon, Long packId) throws ServiceException;

  /**
   * @param packIcon
   * @throws ServiceException
   */
  PackIcon saveOrUpdate(PackIcon packIcon) throws ServiceException;

  /**
   * Returns all Images for a given pack
   *
   * @param pack
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getPackIcons(Pack pack) throws ServiceException;

  /**
   * Returns all Images for a given pack
   *
   * @param pack
   * @return
   * @throws ServiceException
   */
  OutputContentFile getPackIcon(Pack pack) throws ServiceException;

  /**
   * Returns an image file from required identifier. This method is used by the image servlet
   *
   * @param packSize
   * @return
   * @throws ServiceException
   */
  OutputContentFile getPackIcon(String packSize) throws ServiceException;

  /**
   * Get the image ByteArrayOutputStream and content description from CMS
   *
   * @param packIcon
   * @return
   * @throws ServiceException
   */
  OutputContentFile getPackIcon(PackIcon packIcon) throws ServiceException;

  /**
   * @param id
   * @return
   * @throws ServiceException
   */
  PackIcon getPackImageByPackId(Long id) throws ServiceException;
}
