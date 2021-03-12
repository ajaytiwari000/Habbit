package com.salesmanager.core.business.services.catalog.attribute.image;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.cms.attributes.Boost.BoostFileManager;
import com.salesmanager.core.business.repositories.catalog.attribute.image.BoostIconRepository;
import com.salesmanager.core.business.services.catalog.attribute.BoostService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Boost;
import com.salesmanager.core.model.catalog.product.BoostIcon;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.content.OutputContentFile;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

@Service("boostIcon")
public class BoostIconServiceImpl extends SalesManagerEntityServiceImpl<Long, BoostIcon>
    implements BoostIconService {
  private static final Logger LOGGER = LoggerFactory.getLogger(BoostIconServiceImpl.class);
  @Inject private BoostFileManager boostFileManager;
  @Inject private BoostService boostService;
  @Inject private BoostIconService boostIconService;

  private BoostIconRepository boostIconRepository;

  @Inject
  public BoostIconServiceImpl(BoostIconRepository boostIconRepository) {
    super(boostIconRepository);
    this.boostIconRepository = boostIconRepository;
  }

  @Override
  public BoostIcon getById(Long id) {
    return boostIconRepository.findById(id).orElse(null);
  }

  @Override
  public void addBoostIcons(Boost boost, List<BoostIcon> boostIcons) throws ServiceException {}

  @Override
  public void addBoostIcon(Long id, MultipartFile[] files) throws ServiceException {
    try {
      Boost boost = boostService.getById(id);
      if (Objects.nonNull(boost.getBoostIcon())) {
        throw new ServiceException("Boost Icon Already exists.");
      }
      BoostIcon boostIcon = new BoostIcon();
      for (MultipartFile multipartFile : files) {
        if (!multipartFile.isEmpty()) {
          boostIcon.setImage(multipartFile.getInputStream());
          boostIcon.setIcon(multipartFile.getOriginalFilename());
        }
      }
      Assert.notNull(boostIcon.getImage(), "Image cannot be null");
      InputStream inputStream = boostIcon.getImage();
      ImageContentFile cmsContentImage = new ImageContentFile();
      cmsContentImage.setFileName(boostIcon.getIcon());
      cmsContentImage.setFile(inputStream);
      cmsContentImage.setFileContentType(FileContentType.BOOST);
      addBoostIcon(boost, boostIcon, cmsContentImage);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public void addBoostIcon(Boost boost, BoostIcon boostIcon, ImageContentFile inputImage)
      throws ServiceException {
    try {
      Assert.notNull(inputImage.getFile(), "ImageContentFile.file cannot be null");
      boostIcon.setBoostName(boost.getType());
      boostFileManager.addImage(boostIcon, inputImage);
      boost.setBoostIcon(this.create(boostIcon));
      boostService.update(boost);
    } catch (ServiceException e) {
      throw e;
    } catch (Exception e) {
      throw new ServiceException(e);
    } finally {
      try {
        if (inputImage.getFile() != null) {
          inputImage.getFile().close();
        }
      } catch (Exception ignore) {

      }
    }
  }

  @Override
  public void removeBoostIcon(BoostIcon boostIcon, Long boostId) throws ServiceException {
    try {
      Boost boost = boostService.getById(boostId);
      if (boost != null) {
        boostIcon.setBoostName(boost.getType());
        boostFileManager.removeImage(boostIcon);
        boostIconService.delete(boostIcon);
        boost.setBoostIcon(null);
        boostService.update(boost);
      }
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }

  @Override
  public BoostIcon saveOrUpdate(BoostIcon boostIcon) throws ServiceException {
    return null;
  }

  @Override
  public List<OutputContentFile> getBoostIcons(Boost boost) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getBoostIcon(Boost boost) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getBoostIcon(String boostName) throws ServiceException {
    return null;
  }

  @Override
  public OutputContentFile getBoostIcon(BoostIcon boostIcon) throws ServiceException {
    return null;
  }

  @Override
  public BoostIcon getBoostImageByBoostId(Long id) throws ServiceException {
    try {
      return boostService.getById(id).getBoostIcon();
    } catch (Exception e) {
      throw new ServiceException(e);
    }
  }
}
