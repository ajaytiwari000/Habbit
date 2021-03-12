package com.salesmanager.core.business.services.catalog.attribute.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.BoostIcon;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoostIconService extends SalesManagerEntityService<Long, BoostIcon> {

  /**
   * @param boost
   * @param boostIcons
   * @throws ServiceException
   */
  void addBoostIcons(Boost boost, List<BoostIcon> boostIcons) throws ServiceException;

  /**
   * Add a BoostIcon to the persistence and an entry to the CMS
   *
   * @param id
   * @param files
   * @throws ServiceException
   */
  void addBoostIcon(Long id, MultipartFile[] files) throws ServiceException;

  /**
   * @param boost
   * @param boostIcon
   * @param cmsContentImage
   * @throws ServiceException
   */
  void addBoostIcon(Boost boost, BoostIcon boostIcon, ImageContentFile cmsContentImage)
      throws ServiceException;

  /**
   * @param boostIcon
   * @param boostId
   * @throws ServiceException
   */
  void removeBoostIcon(BoostIcon boostIcon, Long boostId) throws ServiceException;

  /**
   * @param boostIcon
   * @throws ServiceException
   */
  BoostIcon saveOrUpdate(BoostIcon boostIcon) throws ServiceException;

  /**
   * Returns all Images for a given boost
   *
   * @param boost
   * @return
   * @throws ServiceException
   */
  List<OutputContentFile> getBoostIcons(Boost boost) throws ServiceException;

  /**
   * Returns all Images for a given boost
   *
   * @param boost
   * @return
   * @throws ServiceException
   */
  OutputContentFile getBoostIcon(Boost boost) throws ServiceException;

  /**
   * Returns an image file from required identifier. This method is used by the image servlet
   *
   * @param boostName
   * @return
   * @throws ServiceException
   */
  OutputContentFile getBoostIcon(String boostName) throws ServiceException;

  /**
   * Get the image ByteArrayOutputStream and content description from CMS
   *
   * @param boostIcon
   * @return
   * @throws ServiceException
   */
  OutputContentFile getBoostIcon(BoostIcon boostIcon) throws ServiceException;

  /**
   * @param id
   * @return
   * @throws ServiceException
   */
  BoostIcon getBoostImageByBoostId(Long id) throws ServiceException;
}
